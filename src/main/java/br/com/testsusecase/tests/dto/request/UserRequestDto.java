package br.com.testsusecase.tests.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDto(String name, String email, String password) {
  @NotEmpty(message = "The field name must be required")
  public String name() {
    return name;
  }

  @NotEmpty(message = "The field name must be required")
  @Email(message = "Please provided a valid e-mail")
  public String email() {
    return email;
  }

  @NotEmpty(message = "The field password must be required")
  public String password() {
    return password;
  }

}
