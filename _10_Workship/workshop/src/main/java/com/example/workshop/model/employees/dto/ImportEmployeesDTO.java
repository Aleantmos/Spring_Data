package com.example.workshop.model.employees.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportEmployeesDTO {
    @XmlElement(name = "employee")
    private List<ImportEmployeeDTO> employees;

    public ImportEmployeesDTO() {
    }

    public List<ImportEmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<ImportEmployeeDTO> employees) {
        this.employees = employees;
    }
}
