package giuk.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {

  private String username;
  private String email;
  private String password;
  private List<Integer> appUserRole;
}