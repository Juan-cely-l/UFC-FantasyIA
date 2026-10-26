package com.ufc.UFC_FantasyIA.controller;

import com.ufc.UFC_FantasyIA.dto.FighterRequestDto;
import com.ufc.UFC_FantasyIA.dto.FighterResponseDto;
import com.ufc.UFC_FantasyIA.service.FighterService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fighters")
public class FighterController {
    private final FighterService fighterService;

    public FighterController(FighterService fighterService) {
        this.fighterService = fighterService;
    }

    @GetMapping
    public ResponseEntity<Page<FighterResponseDto>> getAllFighters(
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.ok(fighterService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FighterResponseDto> getFighterById(@PathVariable UUID id) {
        return ResponseEntity.ok(fighterService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FighterResponseDto> createFighter(
            @Valid @RequestBody FighterRequestDto fighterRequestDto
    ) {
        FighterResponseDto createdFighter = fighterService.create(fighterRequestDto);
        URI location = URI.create("/api/v1/fighters/" + createdFighter.id());
        return ResponseEntity.created(location).body(createdFighter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FighterResponseDto> updateFighter(
            @PathVariable UUID id,
            @Valid @RequestBody FighterRequestDto fighterRequestDto
    ) {
        FighterResponseDto updatedFighter= fighterService.update(id, fighterRequestDto);
        return ResponseEntity.ok(updatedFighter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFighter(@PathVariable UUID id) {
        fighterService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
