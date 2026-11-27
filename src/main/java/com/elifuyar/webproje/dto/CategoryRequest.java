package com.elifuyar.webproje.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

        @NotBlank(message = "Kategori adı boş olamaz.")
        @Size(max = 100)
        String name,

        @Size(max = 255)
        String description
) {
}