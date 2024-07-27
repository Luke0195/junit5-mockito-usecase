package br.com.testsusecase.tests.controllers;


import br.com.testsusecase.tests.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {
  
  private final UserServiceImpl userServiceImpl;
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;

  @GetMapping(value="/{id}")
  public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id){
    UserResponseDto response =  userServiceImpl.findUserById(id);
    return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(response, UserResponseDto.class));
  }

  @GetMapping
  public ResponseEntity<List<UserResponseDto>> findAllUsers(){
    List<UserResponseDto> users = userServiceImpl.findAllUsers();
    return ResponseEntity.status(HttpStatus.OK).body(users);
  }
}
