package br.com.testsusecase.tests.controllers;

import br.com.testsusecase.tests.domain.User;
import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.factories.UserFactory;
import br.com.testsusecase.tests.services.impl.UserServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private UserServiceImpl userService;

   @Autowired
   private ObjectMapper objectMapper;

   private UserRequestDto userRequestDto;
   private User user;
   private UserResponseDto userResponseDto;


   @BeforeEach
   void setup(){
       setupValue();
   }


   @DisplayName("POST - Should create an user when valid data is provided")
   @Test
   void shouldCreateAnUserWhenValidDataIsProvided() throws Exception {
    Mockito.when(userService.create(Mockito.any(UserRequestDto.class))).thenReturn(userResponseDto);
     String jsonBody = objectMapper.writeValueAsString(userRequestDto);
     ResultActions resultActions = this.mockMvc
             .perform(MockMvcRequestBuilders.post("/users")
                     .content(jsonBody)
             .contentType(MediaType.APPLICATION_JSON)
             .accept(MediaType.APPLICATION_JSON)
                     );
     resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
   }


   private void setupValue(){
       this.userRequestDto = UserFactory.makeUserRequestDto();
       this.user = UserFactory.makeUser(userRequestDto);
       this.userResponseDto = UserFactory.makeUserResponseDto(user);
   }



}

