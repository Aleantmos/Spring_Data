package com.example.workshop.model.companies.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportCompaniesDTO {
    @XmlElement(name = "company")
    List<ImportCompanyDTO> companies;

    public ImportCompaniesDTO() {
    }

    public List<ImportCompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(List<ImportCompanyDTO> companies) {
        this.companies = companies;
    }
}
