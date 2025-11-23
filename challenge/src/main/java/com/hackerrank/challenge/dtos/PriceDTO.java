package com.hackerrank.challenge.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data @AllArgsConstructor
@NoArgsConstructor
public class PriceDTO {
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private BigDecimal amount;

    @NotBlank(message = "La moneda de pago es obligatoria")
    @Pattern(regexp = "[A-Z]{3}", message = "La moneda debe ser un código de 3 letras (ej: USD)")
    private String currency;

    private String displayValue;
}
