package com.ufc.UFC_FantasyIA.dto;

import java.util.UUID;

public record FighterResponseDto(
        UUID id,
        String name,
        String nickname,
        Integer heightCm,
        Integer reachCm,
        Integer age,
        String country

) {
}
