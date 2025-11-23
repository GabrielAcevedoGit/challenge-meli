package com.hackerrank.challenge.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotNull(message = "El ID de la categoria es obligatorio")
    Long id;

    String name;
}
