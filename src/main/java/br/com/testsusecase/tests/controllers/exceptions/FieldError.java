package br.com.testsusecase.tests.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FieldError {

    @JsonProperty("field_name")
    private String fieldName;
    @JsonProperty("field_description")
    private String fieldDescription;
}
