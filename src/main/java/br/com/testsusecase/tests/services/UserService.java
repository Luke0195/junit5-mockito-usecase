package br.com.testsusecase.tests.services;


import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.dto.response.UserResponseDto;


import java.util.List;

public interface UserService {

  UserResponseDto findUserById(Long id);
  List<UserResponseDto> findAllUsers();
  UserResponseDto create(UserRequestDto requestDto);
  UserResponseDto update(Long id,  UserRequestDto requestDto);
  void delete(Long id);
}
