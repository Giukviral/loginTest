package giuk.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {

  private String username;
  private String email;
  private String password;
  private int appUserRole;
}