package com.ufc.UFC_FantasyIA.repository;

import com.ufc.UFC_FantasyIA.entity.Fight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FightRepository extends JpaRepository<Fight, UUID> {
}
