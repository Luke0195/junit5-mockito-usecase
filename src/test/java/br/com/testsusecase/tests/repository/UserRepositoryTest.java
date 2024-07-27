package br.com.testsusecase.tests.repository;

import br.com.testsusecase.tests.domain.User;
import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.factories.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("dev")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveShouldReturnAnEntityWhenIsCalled(){
        UserRequestDto userRequestDto = UserFactory.makeUserRequestDto();
        User user = UserFactory.makeUser(userRequestDto);
        user = userRepository.save(user);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
    }
}
