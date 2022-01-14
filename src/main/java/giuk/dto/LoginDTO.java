package giuk.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDTO {

  @NotNull
  @Size(min = 4, max = 255)
  private String username;
  @Size(min = 4, max = 255)
  private String password;
}
