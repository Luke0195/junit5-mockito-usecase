package br.com.testsusecase.tests.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message){
    super(message);
  }
  
}
