package br.com.testsusecase.tests.services.impl;

import br.com.testsusecase.tests.domain.User;
import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.factories.UserFactory;
import br.com.testsusecase.tests.repository.UserRepository;
import br.com.testsusecase.tests.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = UserServiceImplTest.class)
@ActiveProfiles("dev")
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;
    private Optional<User> optionalUser;
    private Long existingId;
    private Long nonExistingId;


    @BeforeEach
    void setup(){
      //  MockitoAnnotations.openMocks(this);
        this.setupValues();
        Mockito.when(userRepository.findById(1L)).thenReturn(this.optionalUser);
    }

    @Test
    @DisplayName("should throw resource not found when invalid user id is provided")
    void shouldThrowsResourceNotFoundExceptionWhenInvalidIdIsProvided(){
        Mockito.when(userRepository.findUserById(this.nonExistingId)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
                this.userService.findUserById(this.nonExistingId);
        });
    }

    @Test
    @DisplayName("should return a user when valid id is provided")
    void shouldReturnUserWhenValidIdIsProvided(){
        UserResponseDto user = userService.findUserById(1L);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.id());
        Assertions.assertNotNull(user.name());
        Assertions.assertNotNull(user.email());
        Assertions.assertNotNull(user.password());
        Assertions.assertEquals(UserResponseDto.class, user.getClass());
    }


    private  void setupValues(){
        this.existingId = 3L;
        this.nonExistingId = 9000L;
        this.userRequestDto = UserFactory.makeUserRequestDto();
        this.user = UserFactory.makeUser(userRequestDto);
        this.optionalUser = Optional.of(UserFactory.makeUser(userRequestDto));
    }


}