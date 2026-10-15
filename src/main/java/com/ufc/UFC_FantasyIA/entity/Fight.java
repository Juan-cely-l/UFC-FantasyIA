package com.ufc.UFC_FantasyIA.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "fights")
public class Fight {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weight_division_id", nullable = false)
    private WeightDivision weightDivision;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fighter1_id", nullable = false)
    private Fighter fighter1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fighter2_id", nullable = false)
    private Fighter fighter2;

    @Column(name = "scheduled_rounds", nullable = false)
    private Integer scheduledRounds;

    @Column(name = "title_fight", nullable = false)
    @Builder.Default
    private Boolean titleFight = false;
}
