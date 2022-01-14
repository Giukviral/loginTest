package giuk.service;

import giuk.dto.AppUserResponseDTO;
import giuk.dto.LoginDTO;
import giuk.dto.RestResponseDTO;
import giuk.dto.SignupDTO;
import giuk.entity.AppUser;
import giuk.entity.AppUserRole;
import giuk.entity.EnumAppUserRole;
import giuk.repository.UserRepository;
import giuk.security.JwtTokenProvider;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public RestResponseDTO signup(SignupDTO userDTO) {
    if (userRepository.findByName(userDTO.getUsername()) != null) {
      return new RestResponseDTO("username is duplicated.");
    }
    if (userRepository.findByMail(userDTO.getEmail()) != null) {
      return new RestResponseDTO("email is duplicated.");
    }
    AppUser newUser = new AppUser();
    newUser.setEmail(userDTO.getEmail());
    newUser.setUsername(userDTO.getUsername());
    newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    List<EnumAppUserRole> userRole = userDTO.getAppUserRole();
    for (EnumAppUserRole role : userRole) {
      newUser.getAppUserRoles().add(new AppUserRole(role));
    }
    return new RestResponseDTO(
        "signup success. userid : " + userRepository.save(newUser).getUserId().toString());
  }

  public RestResponseDTO login(LoginDTO loginData) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginData.getUsername(),
              loginData.getPassword()));
      return new RestResponseDTO(jwtTokenProvider.createToken(loginData.getUsername(),
          userRepository.findByName(loginData.getUsername()).getAppUserRoles()));
    } catch (Exception e) {
      return new RestResponseDTO("login fail");
    }
  }

  public AppUserResponseDTO findByUserId(Integer userid) {
    AppUser user = userRepository.findByUserId(userid);
    return new AppUserResponseDTO(user);
  }

  public AppUserResponseDTO myinfo(HttpServletRequest req) {
    AppUser user = userRepository.findByName(jwtTokenProvider.getUsername(
        jwtTokenProvider.resolveToken(req)));
    // res 는 로그인 된 이후이므로 null이 되지 않는다.
    return new AppUserResponseDTO(user);
  }

}
