package com.example.workshop.services;

import com.example.workshop.model.employees.Employee;
import com.example.workshop.model.employees.dto.ImportEmployeeDTO;
import com.example.workshop.model.employees.dto.ImportEmployeesDTO;
import com.example.workshop.model.projects.dto.ImportProjectsDTO;
import com.example.workshop.repositories.EmployeeRepository;
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
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validation;
    private final ProjectRepository projectRepository;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, ValidationUtil validation, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.validation = validation;
        this.projectRepository = projectRepository;
    }

    public String getXmlContent() throws IOException {
        Path path = Path.of("src", "main", "resource", "files", "xmls", "employee.xml");

        List<String> lines = Files.readAllLines(path);

        return String.join("\n", lines);
    }

    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    public void importEmployees() throws IOException, JAXBException {
        String xmlContent = getXmlContent();

        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext context = JAXBContext.newInstance(ImportEmployeesDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportEmployeesDTO employeesDTO = (ImportEmployeesDTO) unmarshaller.unmarshal(stream);

        List<Employee> employees = employeesDTO.getEmployees().stream()
                .filter(validation::isValid)
                .filter(importEmployeeDTO ->
                        this.validation
                                .isValid(importEmployeeDTO) &&
                        this.projectRepository
                                .findFirstByName(importEmployeeDTO.getProject().getName()).isPresent())
                .map(employeeDTO -> modelMapper.map(employeeDTO, Employee.class))
                .map(employee -> employee.builderWithProject(
                        this.projectRepository
                                .findFirstByName(employee.getProject().getName()).get()
                ))
                .toList();

        employeeRepository.saveAll(employees);
    }
}
