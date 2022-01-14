package giuk.dto;

import giuk.entity.AppUser;
import giuk.entity.EnumAppUserRole;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}