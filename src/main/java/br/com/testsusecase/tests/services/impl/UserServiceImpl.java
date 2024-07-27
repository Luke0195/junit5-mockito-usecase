package br.com.testsusecase.tests.services.impl;

import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.services.exceptions.ResourceAlreadyExistsException;
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
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  @Override
  @Transactional(readOnly = true)
  public UserResponseDto findUserById(Long id) {
    User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user id not found!"));
    return  mapUserToUserResponseDto(entity);
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
  Optional<User> findUserByEmail = userRepository.findByEmail(requestDto.getEmail());
  if(findUserByEmail.isPresent()) throw new ResourceAlreadyExistsException("This e-mail is already taken!");
  User user = User.builder().name(requestDto.getName()).email(requestDto.getEmail()).password(requestDto.getPassword()).build();
  user = userRepository.save(user);
  return mapUserToUserResponseDto(user);
  }

  private static UserResponseDto mapUserToUserResponseDto(User entity){
    return new UserResponseDto(entity.getId(), entity.getName(), entity.getEmail(),
            entity.getPassword(), entity.getCreatedAt(), entity.getUpdatedAt());
  }

}
