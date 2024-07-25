package br.com.testsusecase.tests.services;


import br.com.testsusecase.tests.dto.response.UserResponseDto;

public interface UserService {

  UserResponseDto findUserById(Long id);
}
