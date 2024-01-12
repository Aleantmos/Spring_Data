package com.example.workshop.services;

import com.example.workshop.model.projects.Project;
import com.example.workshop.model.projects.dto.ImportProjectsDTO;
import com.example.workshop.repositories.CompanyRepository;
import com.example.workshop.repositories.ProjectRepository;
import com.example.workshop.utils.ValidationUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CompanyRepository companyRepository;

    public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper, ValidationUtil validationUtil, CompanyRepository companyRepository) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.companyRepository = companyRepository;
    }

    public String getXMLContent() throws IOException {
        Path path = Path.of("resources", "files", "xmls", "projects.xml");

        List<String> lines = Files.readAllLines(path);

        return String.join("\n", lines);
    }

    public void importProjects() throws IOException, JAXBException {
        String xmlContent = getXMLContent();

        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext jaxbContext = JAXBContext.newInstance(ImportProjectsDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ImportProjectsDTO projectsDTO = (ImportProjectsDTO) unmarshaller.unmarshal(stream);

        List<Project> projects = projectsDTO.getProjects().stream()
                .filter(this.validationUtil::isValid)
                .map(projectDTO -> this.modelMapper.map(projectDTO, Project.class))
                .filter(project -> this.companyRepository.findFirstByName(project.getCompany().getName()).isPresent())
                .map(project -> project.buildForCompany(
                        this.companyRepository.findFirstByName(project.getCompany().getName()).get()))
                .collect(Collectors.toList());

        this.projectRepository.saveAll(projects);
    }


    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }
}
