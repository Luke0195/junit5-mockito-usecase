package br.com.testsusecase.tests.factories;

import br.com.testsusecase.tests.domain.User;
import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.dto.response.UserResponseDto;

import java.time.LocalDateTime;

public class UserFactory {

    private UserFactory(){};

    public static UserRequestDto makeUserRequestDto(){
     return UserRequestDto.builder().name("any_name").email("any_mail@mail.com").password("any_password").build();
    }

    public static User makeUser(UserRequestDto userRequestDto){
        return User.builder()
                .id(1L)
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static UserResponseDto makeUserResponseDto(User user){
        return  new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }


}
