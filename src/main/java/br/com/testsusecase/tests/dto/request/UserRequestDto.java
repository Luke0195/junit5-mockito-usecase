package br.com.testsusecase.tests.dto.request;

public record UserRequestDto(String name, String email, String password) {

  public String name() {
    return name;
  }

  public String email() {
    return email;
  }

  public String password() {
    return password;
  }

}
