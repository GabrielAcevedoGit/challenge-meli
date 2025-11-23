package com.hackerrank.challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@NoArgsConstructor
public class ItemDTO {
    @JsonProperty(access = READ_ONLY)
    private String id;

    @NotBlank(message = "El título es obligatorio")
    private String title;
    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Valid
    private PriceDTO price;

    @NotNull(message = "El vendedor es obligatorio")
    @Valid
    private SellerDTO seller;

    @NotNull(message = "La categoria no puede ser nula")
    @Valid
    private CategoryDTO category;

    @Valid
    private List<AttributeDTO> attributes;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stockQuantity;

    @Min(value = 0, message = "La cantidad vendida no puede ser negativa")
    private Integer soldQuantity;

    @NotEmpty(message = "Debes cargar al menos una imagen")
    private List<String> images;

    @NotNull(message = "La condicion es obligatoria")
    @Valid
    private ConditionDTO condition;

    @JsonProperty(access = READ_ONLY)
    private String createdAt;
}
