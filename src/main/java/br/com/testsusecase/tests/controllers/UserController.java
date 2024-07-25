package br.com.testsusecase.tests.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {
  
  private final UserServiceImpl userServiceImpl;

  @GetMapping(value="/{id}")
  public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id){
    UserResponseDto response =  userServiceImpl.findUserById(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
