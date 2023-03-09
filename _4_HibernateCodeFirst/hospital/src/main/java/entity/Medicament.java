package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity {

    @Column(name = "medicament_name")
    private String name;


    @ManyToOne
    protected Diagnose diagnose;

    public Medicament() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
