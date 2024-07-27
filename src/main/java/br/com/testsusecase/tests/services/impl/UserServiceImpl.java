package br.com.testsusecase.tests.services.impl;

import br.com.testsusecase.tests.dto.request.UserRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.testsusecase.tests.domain.User;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.repository.UserRepository;
import br.com.testsusecase.tests.services.UserService;
import br.com.testsusecase.tests.services.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  @Override
  @Transactional(readOnly = true)
  public UserResponseDto findUserById(Long id) {
    User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user id not found!"));
    UserResponseDto response = new UserResponseDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword(), entity.getCreatedAt(), entity.getUpdatedAt());
    return response;
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserResponseDto> findAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(x ->  new UserResponseDto(x.getId(), x.getName(), x.getEmail(), x.getPassword(), x.getCreatedAt(), x.getUpdatedAt())).toList();
  }

  @Override
  @Transactional
  public UserResponseDto create(UserRequestDto requestDto) {
    User createdUser = userRepository.save(modelMapper.map(requestDto, User.class));
    return modelMapper.map(createdUser, UserResponseDto.class);
  }
}
