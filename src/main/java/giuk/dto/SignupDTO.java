package giuk.dto;

import giuk.entity.EnumAppUserRole;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignupDTO {

  @Size(min = 4, max = 255)
  private String username;
  private String email;
  @Size(min = 4, max = 255)
  private String password;
  private List<EnumAppUserRole> appUserRole;
}