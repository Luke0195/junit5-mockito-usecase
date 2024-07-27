package br.com.testsusecase.tests.controllers;


import br.com.testsusecase.tests.dto.request.UserRequestDto;

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

  private static final String PATHID= "/{id}";
  private final UserServiceImpl userServiceImpl;
  private final ModelMapper modelMapper;


  @GetMapping(value=PATHID)
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

  @PutMapping(value = "/{id}")
  public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto requestDto){
    UserResponseDto response = userServiceImpl.update(id, requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping(value = PATHID)
  public ResponseEntity<Void> deleteById(@PathVariable Long id){
    userServiceImpl.delete(id);
    return ResponseEntity.noContent().build();
  }



}
