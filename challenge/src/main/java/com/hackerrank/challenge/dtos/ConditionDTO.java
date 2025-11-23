package com.hackerrank.challenge.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class ConditionDTO {
    @NotBlank(message = "El valor de la condición es obligatorio (ej: NEW)")
    private String value;

    private String displayValue;
}
