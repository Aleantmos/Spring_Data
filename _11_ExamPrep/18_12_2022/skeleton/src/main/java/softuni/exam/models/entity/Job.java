package softuni.exam.models.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "jobs")
public class Job extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Double salary;
    @Column(nullable = false)
    private Double hoursWeek;
    @Column(nullable = false, columnDefinition = "Text")
    private String description;

    @OneToOne
    private Company companies;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getHoursWeek() {
        return hoursWeek;
    }

    public void setHoursWeek(Double hoursWeek) {
        this.hoursWeek = hoursWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompanies() {
        return companies;
    }

    public void setCompanies(Company companies) {
        this.companies = companies;
    }
}
