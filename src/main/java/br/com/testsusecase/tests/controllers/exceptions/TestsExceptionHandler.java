package br.com.testsusecase.tests.controllers.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import br.com.testsusecase.tests.services.exceptions.ResourceAlreadyExistsException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.testsusecase.tests.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TestsExceptionHandler {
  
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardError> handleEntityNotFound(ResourceNotFoundException exception,
                                                            HttpServletRequest request){
    StandardError standardError = makeStandardError("Entity not found!",
            HttpStatus.NOT_FOUND.value(), request.getRequestURI(), exception.getMessage() , new ArrayList<>());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<StandardError> handleEntityAlreadyException(ResourceAlreadyExistsException exception,
                                                                    HttpServletRequest request){
    StandardError standardError = makeStandardError("Resource already exists!",
            HttpStatus.BAD_REQUEST.value(),request.getRequestURI(), exception.getMessage()
            ,  new ArrayList<>());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                             HttpServletRequest request){
   List<FieldError> errors = new ArrayList<>();
   exception.getFieldErrors().forEach(x -> {
     String fieldName = x.getField();
     String fieldDescription = x.getDefaultMessage();
     errors.add(new FieldError(fieldName, fieldDescription));
   });

   StandardError standardError = makeStandardError("Hibernate Validation Exception",
           HttpStatus.BAD_REQUEST.value(),
           request.getRequestURI(), "Invalid field is provided check field_errors to validate the data", errors);
   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);

  }

  private static StandardError makeStandardError(String error, int status, String path,
                                                 String message,
                                                 List<FieldError> errors){
    return StandardError.builder()
            .timestamp(LocalDateTime.now())
            .error(error)
            .status(status)
            .path(path)
            .message(message)
            .fieldErrors(errors)
            .build();
  }

}
