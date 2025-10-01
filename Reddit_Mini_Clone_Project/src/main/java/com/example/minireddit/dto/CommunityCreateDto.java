package com.example.minireddit.dto;

import jakarta.validation.constraints.NotBlank;

public record CommunityCreateDto(
        @NotBlank
        String name,
        String description
) {}
