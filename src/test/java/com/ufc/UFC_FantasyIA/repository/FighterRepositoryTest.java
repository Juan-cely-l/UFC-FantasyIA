package com.ufc.UFC_FantasyIA.repository;

import com.ufc.UFC_FantasyIA.entity.Fighter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FighterRepositoryTest {

    @Autowired
    private FighterRepository fighterRepository;

    @Test
    void shouldSaveAndRetrieveAFighter() {
        Fighter fighter = Fighter.builder()
                .name("Jon Jones")
                .nickname("Bones")
                .heightCm(193)
                .reachCm(214)
                .country("United States")
                .build();

        Fighter savedFighter = fighterRepository.saveAndFlush(fighter);

        assertThat(savedFighter.getId()).isNotNull();

        UUID fighterId = savedFighter.getId();
        Fighter retrievedFighter = fighterRepository.findById(fighterId)
                .orElseThrow();

        assertThat(retrievedFighter.getId()).isEqualTo(fighterId);
        assertThat(retrievedFighter.getName()).isEqualTo("Jon Jones");
        assertThat(retrievedFighter.getNickname()).isEqualTo("Bones");
        assertThat(retrievedFighter.getHeightCm()).isEqualTo(193);
        assertThat(retrievedFighter.getReachCm()).isEqualTo(214);
        assertThat(retrievedFighter.getCountry()).isEqualTo("United States");
    }
}
