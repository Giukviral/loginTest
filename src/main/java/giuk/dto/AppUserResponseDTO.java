package giuk.dto;

import giuk.domain.AppUserDomain;
import giuk.entity.AppUser;
import giuk.entity.EnumAppUserRole;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@NoArgsConstructor
public class AppUserResponseDTO {

  private String username;
  private String email;
  private List<EnumAppUserRole> appUserRole;

  public AppUserResponseDTO(AppUser user){
    if(user == null) return;
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.appUserRole = user.getRoleWithEnumAppUserRoles();
  }
  public AppUserResponseDTO(AppUserDomain user){
    if(user==null) return;
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.appUserRole = new ArrayList<EnumAppUserRole>();
    for (GrantedAuthority g:user.getAuthorities()) {
      this.appUserRole.add(EnumAppUserRole.valueOf(g.getAuthority()));
    }
  }
}