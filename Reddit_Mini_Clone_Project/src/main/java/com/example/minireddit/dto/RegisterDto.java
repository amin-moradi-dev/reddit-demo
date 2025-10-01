package com.example.minireddit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank @Email
        String email,
        @NotBlank @Size(min=6)
        String password,
        @NotBlank
        String displayName
) {}