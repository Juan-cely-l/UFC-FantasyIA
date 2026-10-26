package com.ufc.UFC_FantasyIA.service;

import com.ufc.UFC_FantasyIA.dto.FighterRequestDto;
import com.ufc.UFC_FantasyIA.dto.FighterResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FighterService {
    FighterResponseDto create(FighterRequestDto fighterRequestDto);
    FighterResponseDto update(UUID id, FighterRequestDto fighterRequestDto);
    FighterResponseDto findById(UUID id);
    Page<FighterResponseDto> findAll(Pageable pageable);
    void deleteById(UUID id);
}
