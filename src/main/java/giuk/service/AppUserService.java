package giuk.service;

import giuk.domain.AppUserDomain;
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
      return new RestResponseDTO("duplicated username");
    }
    if (userRepository.findByMail(userDTO.getEmail()) != null) {
      return new RestResponseDTO("duplicated email");
    }
    AppUser newUser = new AppUser(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
    List<EnumAppUserRole> userRole = userDTO.getAppUserRole();
    for (EnumAppUserRole role : userRole) {
      newUser.getAppUserRoles().add(new AppUserRole(role));
    }
    return new RestResponseDTO(
        userRepository.save(newUser).getUserId().toString());
  }

  public RestResponseDTO login(LoginDTO loginData) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginData.getUsername(),
              loginData.getPassword()));
      return new RestResponseDTO(jwtTokenProvider.createToken(loginData.getUsername(),
          userRepository.findByName(loginData.getUsername()).getAppUserRoles()));
    } catch (Exception e) {
      return new RestResponseDTO("un correct id/pw");
    }
  }

  public AppUserResponseDTO findByUserId(Integer userid) {
    AppUser user = userRepository.findByUserId(userid);
    return new AppUserResponseDTO(user);
  }

  public AppUserResponseDTO myInfo(AppUserDomain user) {
    return new AppUserResponseDTO(user);
  }
}