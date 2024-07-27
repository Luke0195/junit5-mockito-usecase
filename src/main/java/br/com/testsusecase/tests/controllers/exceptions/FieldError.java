package br.com.testsusecase.tests.controllers.exceptions;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FieldError {
    private String name;
    private String description;
}
