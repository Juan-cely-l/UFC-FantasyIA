package com.ufc.UFC_FantasyIA.dto;

import java.time.LocalDate;
import java.util.UUID;

public record EventResponseDto(
        UUID id,
        String name,
        String location,
        LocalDate date
) {
}
