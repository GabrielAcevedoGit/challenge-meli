package com.hackerrank.challenge.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class AttributeDTO {
    @NotBlank(message = "El nombre del atributo es obligatorio (ej: Color)")
    @Size(max = 100, message = "El nombre del atributo no puede superar los 100 caracteres")
    private String name;

    @NotBlank(message = "El valor del atributo es obligatorio (ej: Rojo)")
    @Size(max = 255, message = "El valor del atributo no puede superar los 255 caracteres")
    private String value;
}
