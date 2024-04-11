package com.example.football.models.dto.player;

import com.example.football.util.adapters.LocalDateAdapter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDTO {

    @XmlElement(name = "first-name")
    @Size(min = 2)
    private String firstName;
    @XmlElement(name = "last-name")
    @Size(min = 2)
    private String lastName;
    //must be unique
    @XmlElement(name = "email")
    @Email
    private String email;
    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthdate;
    @XmlElement(name = "position")
    private String position;
    @XmlElement(name = "town")
    private PlayerTownNameDTO town;
    @XmlElement(name = "team")
    private PlayerTeamNameDTO team;
    @XmlElement(name = "stat")
    private PlayerStatIdDTO statId;


    public PlayerSeedDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PlayerTownNameDTO getTown() {
        return town;
    }

    public void setTown(PlayerTownNameDTO town) {
        this.town = town;
    }

    public PlayerTeamNameDTO getTeam() {
        return team;
    }

    public void setTeam(PlayerTeamNameDTO team) {
        this.team = team;
    }

    public PlayerStatIdDTO getStatId() {
        return statId;
    }

    public void setStatId(PlayerStatIdDTO statId) {
        this.statId = statId;
    }
}
