package com.chizhov.spring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RaceList {
    public RaceList(int id, boolean isChosen, int position, Horse horse, Race race) {
        this.id = id;
        this.chosen = isChosen;
        this.position = position;
        this.horse = horse;
        this.race = race;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean chosen;
    private int position;

    @ManyToOne()
    @JoinColumn(name = "horse_Id", nullable = false)
    private Horse horse;
    @ManyToOne()
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

}