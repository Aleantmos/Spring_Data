package com.example.workshop.model.projects.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportProjectsDTO {

    @XmlElement(name = "project")
    private List<ImportProjectDTO> projects;

    public ImportProjectsDTO() {
    }

    public List<ImportProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ImportProjectDTO> projects) {
        this.projects = projects;
    }
}
