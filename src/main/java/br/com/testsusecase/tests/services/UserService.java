package br.com.testsusecase.tests.services;


import br.com.testsusecase.tests.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

  UserResponseDto findUserById(Long id);
  List<UserResponseDto> findAllUsers();
}
