package com.example.workshop.model.projects.dto;

import com.example.workshop.model.companies.dto.ImportCompanyDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportProjectDTO {
    @XmlElement
    @NotNull
    private String name;
    @XmlElement
    @NotNull
    private String description;
    @XmlElement(name = "start-data")
    private String startDate;
    @XmlElement(name = "is-finished")
    private Boolean isFinished;
    @XmlElement
    @NotNull
    private BigDecimal payment;
    @XmlElement(name = "company")
    @NotNull
    private ImportCompanyDTO company;

    public ImportProjectDTO() {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public ImportCompanyDTO getCompany() {
        return company;
    }

    public void setCompany(ImportCompanyDTO company) {
        this.company = company;
    }
}
