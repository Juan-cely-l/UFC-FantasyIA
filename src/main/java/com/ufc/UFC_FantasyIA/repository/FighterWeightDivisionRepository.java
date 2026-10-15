package com.ufc.UFC_FantasyIA.repository;

import com.ufc.UFC_FantasyIA.entity.FighterWeightDivision;
import com.ufc.UFC_FantasyIA.entity.FighterWeightDivisionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FighterWeightDivisionRepository extends JpaRepository<FighterWeightDivision, FighterWeightDivisionId>{
    boolean existsByIdFighterIdAndIdWeightDivisionId(
            UUID fighterId,
            UUID weightDivisionId
    );
}
