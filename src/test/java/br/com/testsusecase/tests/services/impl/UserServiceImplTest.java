package br.com.testsusecase.tests.services.impl;

import br.com.testsusecase.tests.domain.User;
import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.factories.UserFactory;
import br.com.testsusecase.tests.repository.UserRepository;
import br.com.testsusecase.tests.services.exceptions.ResourceAlreadyExistsException;
import br.com.testsusecase.tests.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.ResourceClosedException;
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

import java.util.List;
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
        Mockito.when(userRepository.findById(1L)).thenReturn(this.optionalUser);
        UserResponseDto user = userService.findUserById(1L);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.id());
        Assertions.assertNotNull(user.name());
        Assertions.assertNotNull(user.email());
        Assertions.assertNotNull(user.password());
        Assertions.assertEquals(UserResponseDto.class, user.getClass());
    }

    @Test
    @DisplayName("should return all users when findall is called")
    void shouldReturnAllUsersWhenFindAllUsersIsCalled(){
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserResponseDto> response = userService.findAllUsers();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,  response.size());
    }

    @Test
    @DisplayName("Should throws ResourceAlreadyExists when e-mail is already taken!")
    void createShouldThrowsResourceAlreadyExistsWhenEmailIsAlreadyTaken(){
        UserRequestDto dto = UserFactory.makeUserRequestDto();
        dto.setEmail("invalid_mail@mail.com");
        User user = UserFactory.makeUser(dto);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenThrow(ResourceAlreadyExistsException.class);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, ()->{
            userService.create(dto);
        });
    }

    @Test
    @DisplayName("Should return a User when valid data is provided")
    void shouldReturnAUserWhenValidEmailIsProvided(){
        UserRequestDto dto = UserFactory.makeUserRequestDto();
        Mockito.when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        UserResponseDto responseDto = userService.create(dto);
        Assertions.assertNotNull(responseDto);
    }


    private  void setupValues(){
        this.existingId = 3L;
        this.nonExistingId = 9000L;
        this.userRequestDto = UserFactory.makeUserRequestDto();
        this.user = UserFactory.makeUser(userRequestDto);
        this.optionalUser = Optional.of(UserFactory.makeUser(userRequestDto));
    }


}