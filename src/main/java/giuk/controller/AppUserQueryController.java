package giuk.controller;


import giuk.dto.AppUserResponseDTO;
import giuk.dto.LoginDTO;
import giuk.dto.RestResponseDTO;
import giuk.dto.SignupDTO;
import giuk.service.AppUserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppUserQueryController {

  private final AppUserService userService;

  @PostMapping("/signup")
  public RestResponseDTO signup(@RequestBody @Valid SignupDTO user){
    return userService.signup(user);
  }

  @PostMapping("/login")
  public RestResponseDTO login(@RequestBody @Valid LoginDTO loginData) {
    return userService.login(loginData);
  }

  @GetMapping("/users/{userid}")
  @PreAuthorize("hasRole('ADMIN')")
  public AppUserResponseDTO search(@PathVariable String userid){
    return userService.findByUserId(Integer.parseInt(userid));
  }

  @GetMapping("/users/my-info")
  @Secured({"ROLE_ADMIN","ROLE_CLIENT"})
  public AppUserResponseDTO myinfo(HttpServletRequest req){
    return userService.myinfo(req);
  }
  // 이 요청은 인증이 끝난 이후이므로, httpservletrequest 말고 다른데서 본다.
}
