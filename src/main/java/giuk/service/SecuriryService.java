package giuk.service;

import giuk.domain.AppUserDomain;
import giuk.entity.AppUser;
import giuk.entity.AppUserRole;
import giuk.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecuriryService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser userVO = userRepository.findByName(username);
    if(userVO == null){
      System.out.println("!!!!"); //throw는 하는데 안되네.
      throw new UsernameNotFoundException(username);
    }
    AppUserDomain user = AppUserDomain.builder().user_id(userVO.getUserId())
        .username(userVO.getUsername()).email(userVO.getEmail()).password(userVO.getPassword()).build();
    user.setAuthorities(this.getAuthorities(userVO));
    return user;
  }

  public Collection<GrantedAuthority> getAuthorities(AppUser user){
    List<AppUserRole> userRole = user.getAppUserRoles();
    Collection<GrantedAuthority> returnRole = new ArrayList<>();
    for (AppUserRole role : userRole) {
      returnRole.add(new SimpleGrantedAuthority(role.getRole().name()));
    }
    return  returnRole;
  }

}
