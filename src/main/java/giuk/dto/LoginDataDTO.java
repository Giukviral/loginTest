package giuk.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDataDTO {
  @NotNull
  @Size(max = 10, min = 4)
  private String username;
  private String password;
}
