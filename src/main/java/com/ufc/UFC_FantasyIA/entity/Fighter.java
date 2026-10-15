package com.ufc.UFC_FantasyIA.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "fighters")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Fighter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Column(name = "reach_cm")
    private Integer reachCm;

    @Column(name = "country", length = 100)
    private String country;

    @OneToMany(mappedBy = "fighter1",fetch=FetchType.LAZY)
    @Builder.Default
    private Set<Fight> fightsAsFighter1 = new HashSet<>();

    @OneToMany(mappedBy = "fighter2",fetch=FetchType.LAZY)
    @Builder.Default
    private Set<Fight> fightsAsFighter2 = new HashSet<>();


    @OneToMany(mappedBy = "fighter", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<FighterWeightDivision> weightDivisions = new HashSet<>();

}
