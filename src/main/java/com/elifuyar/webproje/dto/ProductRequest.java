package com.elifuyar.webproje.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(

        @NotNull
        Long categoryId,

        @NotBlank
        @Size(max = 150)
        String name,

        String description,

        @NotNull
        @DecimalMin("0.00")
        @Digits(integer = 8, fraction = 2)
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer stockQuantity,

        @Size(max = 500)
        String imageUrl,

        @NotNull
        Boolean active
) {
}