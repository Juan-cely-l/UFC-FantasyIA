package com.ufc.UFC_FantasyIA.repository;

import com.ufc.UFC_FantasyIA.entity.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FighterRepository extends JpaRepository<Fighter, UUID> {

    Optional<Fighter> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}
