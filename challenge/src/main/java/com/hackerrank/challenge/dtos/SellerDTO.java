package com.hackerrank.challenge.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    @NotBlank(message = "El nickname del vendedor es obligatorio")
    private String name;

    private String page;
}
