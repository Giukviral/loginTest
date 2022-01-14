package giuk.dto;

import giuk.entity.EnumAppUserRole;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppUserResponseDTO {
  private String username;
  private String email;
  private List<EnumAppUserRole> appUserRole;
}