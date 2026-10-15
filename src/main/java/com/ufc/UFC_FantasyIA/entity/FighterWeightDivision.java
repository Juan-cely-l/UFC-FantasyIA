package com.ufc.UFC_FantasyIA.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fighter_weight_divisions")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FighterWeightDivision {

    @EmbeddedId
    private FighterWeightDivisionId id;

    @MapsId("fighterId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fighter_id", nullable = false)
    private Fighter fighter;

    @MapsId("weightDivisionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weight_division_id", nullable = false)
    private WeightDivision weightDivision;

}
