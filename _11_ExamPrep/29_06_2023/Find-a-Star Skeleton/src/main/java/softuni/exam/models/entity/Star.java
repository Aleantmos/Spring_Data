package softuni.exam.models.entity;

import softuni.exam.models.enums.StarTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "stars")
public class Star extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double lightYears;
    @Column(columnDefinition = "Text", nullable = false)
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StarTypeEnum starType;
    @OneToMany(mappedBy = "observingStar")
    private List<Astronomer> astronomers;
    @ManyToOne
    private Constellation constellation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StarTypeEnum getStarType() {
        return starType;
    }

    public void setStarType(StarTypeEnum starType) {
        this.starType = starType;
    }

    public List<Astronomer> getAstronomers() {
        return astronomers;
    }

    public void setAstronomers(List<Astronomer> astronomers) {
        this.astronomers = astronomers;
    }
}
