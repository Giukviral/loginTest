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
public class AppUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // authenticationManager.authenticate(token)에서 불림.
    // 지금은 jwt가 filter 에서 auth 동작을 수행하므로 위 코드가 사라졌고, filter에서 호출됨.
    AppUser user = userRepository.findByName(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return AppUserDomain.builder().userid(user.getUserId()).username(user.getUsername())
        .email(user.getEmail()).password(user.getPassword()).authorities(this.getAuthorities(user))
        .build();
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
