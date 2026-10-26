package com.ufc.UFC_FantasyIA.service;

import com.ufc.UFC_FantasyIA.dto.FighterRequestDto;
import com.ufc.UFC_FantasyIA.dto.FighterResponseDto;
import com.ufc.UFC_FantasyIA.entity.Fighter;
import com.ufc.UFC_FantasyIA.repository.FighterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FighterServiceImplTest {

    @Mock
    private FighterRepository fighterRepository;

    @InjectMocks
    private FighterServiceImpl fighterService;

    @Test
    void shouldBeRegisteredAsSpringService() {
        assertThat(FighterServiceImpl.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    void shouldCreateAndMapAFighter() {
        FighterRequestDto request = new FighterRequestDto(
                "Jon Jones",
                "Bones",
                193,
                37,
                214,
                "United States"
        );

        when(fighterRepository.save(any(Fighter.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FighterResponseDto response = fighterService.create(request);

        ArgumentCaptor<Fighter> captor = ArgumentCaptor.forClass(Fighter.class);
        verify(fighterRepository).save(captor.capture());

        Fighter savedFighter = captor.getValue();
        assertThat(savedFighter.getName()).isEqualTo(request.name());
        assertThat(savedFighter.getNickname()).isEqualTo(request.nickname());
        assertThat(savedFighter.getHeightCm()).isEqualTo(request.heightCm());
        assertThat(savedFighter.getAge()).isEqualTo(request.age());
        assertThat(savedFighter.getReachCm()).isEqualTo(request.reachCm());
        assertThat(savedFighter.getCountry()).isEqualTo(request.country());
        assertThat(response.name()).isEqualTo(request.name());
    }
}
