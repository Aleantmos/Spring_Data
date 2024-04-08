package softuni.exam.models.dto.jobDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class JobSeedDTO {

    @XmlElement(name = "jobTitle")
    @Size(min = 2, max = 40)
    private String jobTitle;
    @XmlElement(name = "salary")
    @Min(value = 300)
    private Double salary;
    @XmlElement(name = "hoursAWeek")
    @Min(value = 10)
    private Double hoursAWeek;
    @XmlElement(name = "description")
    @Size(min = 5)
    private String description;
    @XmlElement(name = "companyId")
    private Long companyId;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getHoursAWeek() {
        return hoursAWeek;
    }

    public void setHoursAWeek(Double hoursAWeek) {
        this.hoursAWeek = hoursAWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
