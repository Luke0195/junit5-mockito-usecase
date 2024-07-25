package br.com.testsusecase.tests.services.impl;

import org.springframework.stereotype.Service;

import br.com.testsusecase.tests.domain.User;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.repository.UserRepository;
import br.com.testsusecase.tests.services.UserService;
import br.com.testsusecase.tests.services.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  @org.springframework.transaction.annotation.Transactional(readOnly = true)
  public UserResponseDto findUserById(Long id) {
    User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user id not found!"));
    UserResponseDto response = new UserResponseDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword(), entity.getCreatedAt(), entity.getUpdatedAt());
    return response;
  }
  
  
}
