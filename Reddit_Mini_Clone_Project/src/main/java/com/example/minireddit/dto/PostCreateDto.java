package com.example.minireddit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCreateDto(
        @NotNull Long communityId,
        String title, // required when parentId null (top-level)
        @NotBlank String body,
        String imageUrl,
        Long parentId // null => top-level; otherwise comment
) {}
