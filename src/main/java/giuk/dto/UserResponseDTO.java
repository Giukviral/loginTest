package giuk.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserResponseDTO{
  private String username;
  private String email;
  private List<Integer> appUserRole;
}