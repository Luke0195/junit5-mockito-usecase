package br.com.testsusecase.tests.controllers.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardError implements Serializable{

  private LocalDateTime timestamp;
  private Integer status;
  private String error;
  private String message;
  private String path;
  private List<FieldError> fieldErrors;
  
}
