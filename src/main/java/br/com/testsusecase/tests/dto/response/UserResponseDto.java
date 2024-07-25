package br.com.testsusecase.tests.dto.response;

import java.time.LocalDateTime;

public record UserResponseDto(Long id, String name, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {

  public Long id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String email() {
    return email;
  }

  public String password() {
    return password;
  }

  public LocalDateTime createdAt() {
    return createdAt;
  }

  public LocalDateTime updatedAt() {
    return updatedAt;
  }

}
