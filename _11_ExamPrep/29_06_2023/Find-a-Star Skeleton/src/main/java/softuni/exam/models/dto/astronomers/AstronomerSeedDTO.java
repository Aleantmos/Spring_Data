package softuni.exam.models.dto.astronomers;

import org.springframework.beans.factory.annotation.Value;
import softuni.exam.config.LocalDateAdapter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedDTO {
    @XmlElement(name = "average_observation_hours")
    @Min(15000)
    private Double averageObservationHours;
    @XmlElement(name = "birthday")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthday;
    @Size(min = 2, max = 30)
    @XmlElement(name = "first_name")
    private String firstName;
    @Size(min = 2, max = 30)
    @XmlElement(name = "last_name")
    private String lastName;
    @XmlElement(name = "salary")
    @Min(500)
    private Double salary;
    @XmlElement(name = "observing_star_id")
    private Long observingStarId;

    public AstronomerSeedDTO() {
    }

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getObservingStarId() {
        return observingStarId;
    }

    public void setObservingStarId(Long observingStarId) {
        this.observingStarId = observingStarId;
    }
}
