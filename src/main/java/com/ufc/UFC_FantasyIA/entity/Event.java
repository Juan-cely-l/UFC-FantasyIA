package com.ufc.UFC_FantasyIA.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "events")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Fight> fights = new HashSet<>();

}
