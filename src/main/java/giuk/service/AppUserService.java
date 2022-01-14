package giuk.service;

import giuk.domain.AppUserDomain;
import giuk.dto.LoginDTO;
import giuk.dto.SignupDTO;
import giuk.dto.AppUserResponseDTO;
import giuk.entity.AppUser;
import giuk.entity.AppUserRole;
import giuk.entity.EnumAppUserRole;
import giuk.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;

  public String saveNewUser(SignupDTO userDTO) {
    if (userRepository.findByName(userDTO.getUsername()) != null) {
      return "already exist user name!";
    }
    if (userRepository.findByMail(userDTO.getEmail()) != null) {
      return "already used eamil address!"; //이건 잘 됨.
    }
    AppUser newUser = new AppUser();
    newUser.setEmail(userDTO.getEmail());
    newUser.setUsername(userDTO.getUsername());
    newUser.setPassword(userDTO.getPassword());
    List<EnumAppUserRole> userRole = userDTO.getAppUserRole();
    for (EnumAppUserRole role : userRole) {
      newUser.getAppUserRoles().add(new AppUserRole(role));
    }
    return userRepository.save(newUser).getUserId().toString();
  }

  public String login(LoginDTO loginData) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        loginData.getUsername(), loginData.getPassword());
    Authentication authentication = authenticationManager.authenticate(token);
    return "login success";
  }


  public AppUserResponseDTO findByUserId(Integer userid) {
    AppUser user = userRepository.findByUserId(userid);
    if (user == null) {
      return AppUserResponseDTO.builder().build();
    }
    List<EnumAppUserRole> userRole = new ArrayList<>();
    List<AppUserRole> hasRole = user.getAppUserRoles();
    for (AppUserRole role : hasRole) {
      userRole.add(role.getRole());
    }
    AppUserResponseDTO retUser = AppUserResponseDTO.builder().username(user.getUsername())
        .email(user.getEmail()).appUserRole(userRole).build();
    return retUser;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser userVO = userRepository.findByName(username);
    if (userVO == null) {
      System.out.println("!!!!"); //throw는 하는데 안되네.
      throw new UsernameNotFoundException(username);
    }

    AppUserDomain user = AppUserDomain.builder().userid(userVO.getUserId())
        .username(userVO.getUsername()).email(userVO.getEmail()).password(userVO.getPassword())
        .authorities(this.getAuthorities(userVO))
        .build();
    return user;
  }

  public Collection<GrantedAuthority> getAuthorities(AppUser user) {
    List<AppUserRole> userRole = user.getAppUserRoles();
    Collection<GrantedAuthority> returnRole = new ArrayList<>();
    for (AppUserRole role : userRole) {
      returnRole.add(new SimpleGrantedAuthority(role.getRole().name()));
    }
    return returnRole;
  }
}