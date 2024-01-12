package com.example.workshop.model.projects;

import com.example.workshop.model.companies.Company;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(name = "is_finished")
    private boolean isFinished;
    @Column(nullable = false)
    private BigDecimal payment;
    @Column(name = "start_date")
    private LocalDate startDate;
    @ManyToOne(optional = false)
    private Company company;

    public Project() {
    }

    public Project(String name, String description, boolean isFinished, BigDecimal payment, LocalDate startDate, Company company) {
        this.name = name;
        this.description = description;
        this.isFinished = isFinished;
        this.payment = payment;
        this.startDate = startDate;
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Project buildForCompany(Company company) {
        this.setCompany(company);
        return this;
    }
}
