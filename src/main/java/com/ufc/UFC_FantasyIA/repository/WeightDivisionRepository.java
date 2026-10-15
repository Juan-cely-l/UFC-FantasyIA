package com.ufc.UFC_FantasyIA.repository;

import com.ufc.UFC_FantasyIA.entity.WeightDivision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeightDivisionRepository extends JpaRepository<WeightDivision, UUID> {
}
