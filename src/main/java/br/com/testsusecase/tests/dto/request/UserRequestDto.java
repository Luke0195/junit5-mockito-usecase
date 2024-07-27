package br.com.testsusecase.tests.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequestDto {
  @NotEmpty(message = "The field name must be required")
  private String name;

  @NotEmpty(message = "The field name must be required")
  @Email(message = "Please provided a valid e-mail")
  private String email;

  @NotEmpty(message = "The field password must be required")
  private String password;

}
