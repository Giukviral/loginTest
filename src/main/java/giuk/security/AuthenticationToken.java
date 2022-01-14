package giuk.security;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AuthenticationToken {
  private String username;
  private Collection roles;
  private String token;
}
