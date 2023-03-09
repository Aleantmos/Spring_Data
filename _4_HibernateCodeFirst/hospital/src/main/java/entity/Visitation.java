package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {

    @Column(name = "visitation_date")
    private LocalDate visitationDate;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    private Patient patient;

    public Visitation() {
    }

    public LocalDate getVisitationDate() {
        return visitationDate;
    }

    public void setVisitationDate(LocalDate visitationDate) {
        this.visitationDate = visitationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
