package giuk.domain;

import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
// 생성시에만 값 설정이 가능하고 변경되지 않을 것임.
@Builder
public class AppUserDomain implements UserDetails {
  private int userid;
  private String username;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;


  // 이하 UserDetails 기본 함수들.
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}