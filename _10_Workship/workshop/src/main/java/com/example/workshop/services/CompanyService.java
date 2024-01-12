package com.example.workshop.services;

import com.example.workshop.model.companies.Company;
import com.example.workshop.model.companies.dto.ImportCompaniesDTO;
import com.example.workshop.repositories.CompanyRepository;
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
public class CompanyService {

    private final ModelMapper mapper;
    private final CompanyRepository companyRepository;

    public CompanyService(ModelMapper mapper, CompanyRepository companyRepository) {
        this.mapper = mapper;
        this.companyRepository = companyRepository;
    }

    public String getXMLContent() throws IOException {
        Path path = Path.of("resources","files", "xmls", "companies.xml");
        List<String> lines = Files.readAllLines(path);

        return String.join("\n", lines);
    }

    public void importCompanies() throws IOException, JAXBException {
        String xmlContent = this.getXMLContent();

        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext jaxbContext = JAXBContext.newInstance(ImportCompaniesDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ImportCompaniesDTO companiesDTO = (ImportCompaniesDTO) unmarshaller.unmarshal(stream);

        List<Company> entities = companiesDTO.getCompanies().stream()
                .map(dto -> this.mapper.map(dto, Company.class))
                .collect(Collectors.toList());


        companyRepository.saveAll(entities);
    }

    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }
}
