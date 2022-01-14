package giuk.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestResponseDTO {

  private String message;

  public RestResponseDTO(String msg) {
    this.message = msg;
  }
}
