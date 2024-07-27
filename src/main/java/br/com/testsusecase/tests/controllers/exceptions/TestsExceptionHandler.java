package br.com.testsusecase.tests.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.testsusecase.tests.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TestsExceptionHandler {
  
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardError> handleEntityNotFound(ResourceNotFoundException exception,
                                                            HttpServletRequest request){
    StandardError standardError = StandardError.builder().timestamp(LocalDateTime.now()).error("Entity not found")
            .message(exception.getMessage()).status(HttpStatus.NOT_FOUND.value()).path(request.getRequestURI()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);

  }

}
