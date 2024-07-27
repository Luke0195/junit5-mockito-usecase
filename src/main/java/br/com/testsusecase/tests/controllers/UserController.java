package br.com.testsusecase.tests.controllers;


import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.repository.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

  @PostMapping
  public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userRequestDto){
     UserResponseDto response = userServiceImpl.create(userRequestDto);
     URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
     return ResponseEntity.created(uri).body(response);
  }
}
