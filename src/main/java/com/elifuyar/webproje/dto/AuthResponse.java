package com.elifuyar.webproje.dto;

import com.elifuyar.webproje.enums.Role;

public record AuthResponse(
        String token,
        Long userId,
        String fullName,
        String email,
        Role role
) {
}