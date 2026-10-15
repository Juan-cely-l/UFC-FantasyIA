package com.ufc.UFC_FantasyIA.repository;

import com.ufc.UFC_FantasyIA.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
