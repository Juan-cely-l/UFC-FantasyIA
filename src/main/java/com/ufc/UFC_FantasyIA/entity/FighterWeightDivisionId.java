package com.ufc.UFC_FantasyIA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FighterWeightDivisionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "fighter_id")
    private UUID fighterId;

    @Column(name = "weight_division_id")
    private UUID weightDivisionId;
}
