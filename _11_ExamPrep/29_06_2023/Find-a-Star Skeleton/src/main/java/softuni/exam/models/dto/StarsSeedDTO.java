package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;

public class StarsSeedDTO {
    @Expose
    @Size(min = 6)
    private String description;
    @Expose
    private Double lightYears;
    @Expose
    @Size(min = 2, max = 30)
    private String name;
    @Expose
    private String starType;
    @Expose
    private Integer constellation;

    public StarsSeedDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    public Integer getConstellation() {
        return constellation;
    }

    public void setConstellation(Integer constellation) {
        this.constellation = constellation;
    }
}
