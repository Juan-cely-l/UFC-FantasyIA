package com.ufc.UFC_FantasyIA.service;

import com.ufc.UFC_FantasyIA.dto.FighterRequestDto;
import com.ufc.UFC_FantasyIA.dto.FighterResponseDto;
import com.ufc.UFC_FantasyIA.entity.Fighter;
import com.ufc.UFC_FantasyIA.exception.FighterNotFoundException;
import com.ufc.UFC_FantasyIA.exception.ResourceAlreadyExistsException;
import com.ufc.UFC_FantasyIA.repository.FighterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FighterServiceImpl implements FighterService {
    private final FighterRepository fighterRepository;

    @Override
    @Transactional
    public FighterResponseDto create(FighterRequestDto fighterRequestDto) {
        String name = fighterRequestDto.name().trim();
        if (fighterRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Fighter already exists with name: " + name);
        }

        Fighter fighter = Fighter.builder()
                .name(name)
                .nickname(fighterRequestDto.nickname().trim())
                .heightCm(fighterRequestDto.heightCm())
                .reachCm(fighterRequestDto.reachCm())
                .age(fighterRequestDto.age())
                .country(fighterRequestDto.country().trim())
                .build();

        return toResponse(fighterRepository.save(fighter));
    }

    @Override
    @Transactional
    public FighterResponseDto update(UUID id, FighterRequestDto fighterRequestDto) {
        Fighter fighter = fighterRepository.findById(id)
                .orElseThrow(() -> new FighterNotFoundException("Fighter not found with id: " + id));
        String name = fighterRequestDto.name().trim();

        if (fighterRepository.existsByNameAndIdNot(name, id)) {
            throw new ResourceAlreadyExistsException("Fighter already exists with name: " + name);
        }

        fighter.setName(name);
        fighter.setNickname(fighterRequestDto.nickname().trim());
        fighter.setHeightCm(fighterRequestDto.heightCm());
        fighter.setReachCm(fighterRequestDto.reachCm());
        fighter.setAge(fighterRequestDto.age());
        fighter.setCountry(fighterRequestDto.country().trim());

        return toResponse(fighter);
    }

    @Override
    public FighterResponseDto findById(UUID id) {
        return fighterRepository.findById(id).map(this::toResponse).orElseThrow(() -> new FighterNotFoundException("Fighter not found with id:"+ id));
    }

    @Override
    public Page<FighterResponseDto> findAll(Pageable pageable) {
        return fighterRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!fighterRepository.existsById(id)) {
            throw new FighterNotFoundException("Fighter not found with id: " + id);
        }
        fighterRepository.deleteById(id);

    }
    private FighterResponseDto toResponse(Fighter fighter){
        return new FighterResponseDto(
                fighter.getId(),
                fighter.getName(),
                fighter.getNickname(),
                fighter.getHeightCm(),
                fighter.getReachCm(),
                fighter.getAge(),
                fighter.getCountry()
        );
    }
}
