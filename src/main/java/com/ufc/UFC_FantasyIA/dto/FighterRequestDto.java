package com.ufc.UFC_FantasyIA.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FighterRequestDto(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
         String name,
        @NotBlank(message = "Nickname cannot be empty")
        @Size(min = 2, max = 100, message = "Nickname must be between 2 and 100 characters")
        String nickname,
        @NotNull
        @Positive
        Integer heightCm,
        @NotNull(message = "Age cannot be null")
        @Positive
        Integer age,
        @NotNull
        @Positive
        Integer reachCm,
        @NotBlank(message = "Country cannot be empty")
        @Size(min = 2, max = 100, message = "Country must be between 2 and 100 characters")
        String country

) {
}
