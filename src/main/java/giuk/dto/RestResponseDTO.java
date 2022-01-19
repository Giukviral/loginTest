package giuk.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestResponseDTO {

  private String message;

  public RestResponseDTO(String msg) {
    this.message = msg;
  }
}
