package com.ufc.UFC_FantasyIA.service;

import com.ufc.UFC_FantasyIA.dto.EventRequestDto;
import com.ufc.UFC_FantasyIA.dto.EventResponseDto;
import com.ufc.UFC_FantasyIA.entity.Event;
import com.ufc.UFC_FantasyIA.exception.ResourceAlreadyExistsException;
import com.ufc.UFC_FantasyIA.exception.EventNotFoundException;
import com.ufc.UFC_FantasyIA.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventResponseDto create(EventRequestDto eventRequestDto) {
        String name = eventRequestDto.name().trim();
        if (eventRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Event already exists with name: " + name);
        }

        Event event = Event.builder()
                .name(name)
                .date(eventRequestDto.date())
                .location(eventRequestDto.location().trim())
                .build();

        return toResponse(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventResponseDto update(UUID id, EventRequestDto eventRequestDto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
        String name = eventRequestDto.name().trim();

        if (eventRepository.existsByNameAndIdNot(name, id)) {
            throw new ResourceAlreadyExistsException("Event already exists with name: " + name);
        }

        event.setName(name);
        event.setLocation(eventRequestDto.location().trim());
        event.setDate(eventRequestDto.date());

        return toResponse(event);
    }

    @Override
    public EventResponseDto findById(UUID id) {
        return eventRepository.findById(id).map(this::toResponse).orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
    }

    @Override
    public Page<EventResponseDto> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }

    private EventResponseDto toResponse(Event event) {
        return new EventResponseDto(
                event.getId(),
                event.getName(),
                event.getLocation(),
                event.getDate()
        );
    }
}
