package com.ufc.UFC_FantasyIA.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventRequestDto(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,
        @NotNull(message = "Date cannot be null")
        LocalDate date,

        @NotBlank(message = "Location cannot be empty")
        @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
        String location
) {
}
