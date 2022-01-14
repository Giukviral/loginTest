package giuk.controller;

import giuk.dto.LoginDataDTO;
import giuk.security.AuthenticationToken;
import giuk.service.SecuriryService;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SecuriryController {

  private final AuthenticationManager authenticationManager;
  private final SecuriryService securiryService;

  @PostMapping("/login")
  public AuthenticationToken login(@RequestBody LoginDataDTO loginData, HttpSession session) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        loginData.getUsername(), loginData.getPassword());
    Authentication authentication = authenticationManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        SecurityContextHolder.getContext());
    UserDetails user;
    try {
      user = securiryService.loadUserByUsername(loginData.getUsername());
      //아이디가 없으면 여기서 죽음. 다음 줄로 가기 전에 죽음.. duplicated session key로.
    } catch (Exception e) {
      System.out.println("원통하도다"); // 이것도 안 뜸
      throw e;
    }
    return new AuthenticationToken(user.getUsername(), user.getAuthorities(), session.getId());
  }
}