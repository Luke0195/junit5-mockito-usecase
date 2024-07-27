package br.com.testsusecase.tests.controllers;

import br.com.testsusecase.tests.domain.User;
import br.com.testsusecase.tests.dto.request.UserRequestDto;
import br.com.testsusecase.tests.dto.response.UserResponseDto;
import br.com.testsusecase.tests.factories.UserFactory;
import br.com.testsusecase.tests.services.exceptions.ResourceAlreadyExistsException;
import br.com.testsusecase.tests.services.exceptions.ResourceNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

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


   @DisplayName("POST- Should throws ResourceAlreadyExistsException when email already exists")
   @Test
   void shouldThrowsResourceAlreadyExistsExceptionWhenEmailAlreadyExists()throws Exception{
       UserRequestDto existsRequestDto = new UserRequestDto("any_name", "invalid_email@mail.com", "123");
       Mockito.when(userService.create(Mockito.any())).thenThrow(ResourceAlreadyExistsException.class);
       String jsonBody = objectMapper.writeValueAsString(existsRequestDto);
       ResultActions resultActions = this.mockMvc
               .perform(MockMvcRequestBuilders.post("/users")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
               .content(jsonBody));
       resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
   }

   @DisplayName("GET - Should return a list of users")
   @Test
   void shouldListAllUserOnGet() throws Exception{
       ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/users")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON));
       resultActions.andExpect(MockMvcResultMatchers.status().isOk());
   }

   @DisplayName("GET - Should return a user when valid is provided")
   @Test
   void shouldReturnAnUserWhenValidIdIsProvided() throws Exception{
       Mockito.when(userService.findUserById(1L)).thenReturn(this.userResponseDto);
       ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON));
       resultActions.andExpect(MockMvcResultMatchers.status().isOk());
   }

   @DisplayName("GET - Should returns 404 when invalid id is provided")
   @Test
   void shouldReturnNotFoundWhenInvalidIdIsProvided() throws Exception {
     Mockito.when(userService.findUserById(900L)).thenThrow(ResourceNotFoundException.class);
     ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 900L)
             .contentType(MediaType.APPLICATION_JSON)
             .accept(MediaType.APPLICATION_JSON));
    resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
   }

   @DisplayName("DELETE- Should returns 200 when valid id is provided")
   @Test
   void shouldReturnsSuccessOnDeleteAValidUserId() throws Exception{
       Mockito.doNothing().when(userService).delete(1L);
       ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}",1L)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON));
      resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
   }

   @DisplayName("DELETE - Should returns 404 when a invalid id provided")
   @Test
   void shouldReturnNotFoundWhenInvalidIdIsProvidedOnDelete() throws Exception{
       Mockito.doThrow(ResourceNotFoundException.class).when(userService).delete(900L);
       ResultActions resultActions = this.mockMvc
               .perform(MockMvcRequestBuilders.delete("/users/{id}", 900L)
               .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
       resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
   }

   @DisplayName("UPDATE - Should return 200 when valid data is provided")
   @Test
   void shouldUpdateAnUserWhenValidDataIsProvided() throws Exception{
       String jsonBody = objectMapper.writeValueAsString(this.userResponseDto);
       ResultActions resultActions = this.mockMvc
               .perform(MockMvcRequestBuilders.put("/users/{id}", 1L)
                       .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonBody));
       resultActions.andExpect(MockMvcResultMatchers.status().isOk());
   }



   private void setupValue(){
       this.userRequestDto = UserFactory.makeUserRequestDto();
       this.user = UserFactory.makeUser(userRequestDto);
       this.userResponseDto = UserFactory.makeUserResponseDto(user);
   }



}

