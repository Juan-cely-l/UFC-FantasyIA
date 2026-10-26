package com.ufc.UFC_FantasyIA.service;

import com.ufc.UFC_FantasyIA.dto.EventRequestDto;
import com.ufc.UFC_FantasyIA.dto.EventResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {
    EventResponseDto create(EventRequestDto eventRequestDto);
    EventResponseDto update(UUID id, EventRequestDto eventRequestDto);
    EventResponseDto findById(UUID id);
    Page<EventResponseDto> findAll(Pageable pageable);
    void deleteById(UUID id);
}
