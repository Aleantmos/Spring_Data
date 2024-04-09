package com.example.football.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Long population;
    @Column(columnDefinition = "Text", nullable = false)
    private String travelGuide;
    @OneToMany(mappedBy = "town")
    private List<Team> team;
    @OneToMany(mappedBy = "town")
    private List<Player> players;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }
}
