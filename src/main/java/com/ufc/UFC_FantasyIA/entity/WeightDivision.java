package com.ufc.UFC_FantasyIA.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "weight_divisions")
public class WeightDivision {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "weightDivision", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<FighterWeightDivision> fighters = new HashSet<>();

    @OneToMany(mappedBy = "weightDivision", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Fight> fights = new HashSet<>();

}
