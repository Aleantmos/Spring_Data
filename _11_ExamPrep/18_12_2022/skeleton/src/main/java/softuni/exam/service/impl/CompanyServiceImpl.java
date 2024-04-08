package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.companyDTO.CompanySeedDTO;
import softuni.exam.models.dto.companyDTO.CompanySeedRootDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.service.CountryService;
import softuni.exam.util.MyValidation;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CompanyServiceImpl implements CompanyService {
    public static final String COMPANY_FILE_PATH = "src/main/resources/files/xml/companies.xml";
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final CountryService countryService;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;

    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser, CountryService countryService, ModelMapper modelMapper, MyValidation myValidation) {
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
    }

    @Override
    public boolean areImported() {
        return companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(COMPANY_FILE_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(COMPANY_FILE_PATH, CompanySeedRootDTO.class)
                .getCompanies()
                .forEach(companySeedDTO -> {
                    boolean filtered = validateCompany(companySeedDTO);

                    if (filtered) {
                        Company company = modelMapper.map(companySeedDTO, Company.class);
                        company.setCountry(countryService.getCountryById(companySeedDTO.getCountryId()));

                        companyRepository.save(company);

                        sb.append(String.format("Successfully imported company %s - %d",
                                        company.getName(), companySeedDTO.getCountryId()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid company")
                                .append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    @Override
    public Company getCompanyById(Long companyId) {
        return companyRepository.getCompanyById(companyId).orElse(null);
    }

    private boolean validateCompany(CompanySeedDTO company) {
        return myValidation.isValid(company) && companyRepository.checkUniqueness(company.getCompanyName());
    }
}
