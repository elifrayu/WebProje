package com.elifuyar.webproje.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Ad soyad boş olamaz.")
        @Size(max = 100)
        String fullName,

        @NotBlank(message = "E-posta boş olamaz.")
        @Email(message = "Geçerli bir e-posta giriniz.")
        @Size(max = 150)
        String email,

        @NotBlank(message = "Şifre boş olamaz.")
        @Size(min = 8, max = 64,
                message = "Şifre 8-64 karakter arasında olmalıdır.")
        String password

) {
}