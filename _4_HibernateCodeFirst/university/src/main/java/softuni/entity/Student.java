package softuni.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends User {

    @Column(name = "avg_grade")
    private Float avgGrade;

    @Column
    private Integer attendance;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    public Student() {
    }

    public Float getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Float avgGrade) {
        this.avgGrade = avgGrade;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }
}
