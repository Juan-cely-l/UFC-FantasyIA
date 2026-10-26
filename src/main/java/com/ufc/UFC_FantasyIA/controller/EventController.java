package com.ufc.UFC_FantasyIA.controller;

import com.ufc.UFC_FantasyIA.dto.EventRequestDto;
import com.ufc.UFC_FantasyIA.dto.EventResponseDto;
import com.ufc.UFC_FantasyIA.service.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Page<EventResponseDto>> getAllEvents(
            @PageableDefault(size = 20, sort = "date") Pageable pageable
    ) {
        return ResponseEntity.ok(eventService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(
            @Valid @RequestBody EventRequestDto eventRequestDto
    ) {
        EventResponseDto createdEvent = eventService.create(eventRequestDto);
        URI location = URI.create("/api/v1/events/" + createdEvent.id());
        return ResponseEntity.created(location).body(createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable UUID id,
            @Valid @RequestBody EventRequestDto eventRequestDto
    ) {
        EventResponseDto updatedEvent = eventService.update(id, eventRequestDto);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

