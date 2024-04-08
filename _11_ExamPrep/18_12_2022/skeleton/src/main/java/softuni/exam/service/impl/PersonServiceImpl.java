package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PersonSeedDTO;
import softuni.exam.models.entity.Person;
import softuni.exam.models.enums.StatusType;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.CountryService;
import softuni.exam.service.PersonService;
import softuni.exam.util.MyValidation;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PersonServiceImpl implements PersonService {
    public static final String PEOPLE_FILE_PATH = "src/main/resources/files/json/people.json";
    private final PersonRepository personRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;
    private final CountryService countryService;

    public PersonServiceImpl(PersonRepository personRepository, Gson gson, ModelMapper modelMapper, MyValidation myValidation, CountryService countryService) {
        this.personRepository = personRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
        this.countryService = countryService;
    }

    @Override
    public boolean areImported() {
        return personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(PEOPLE_FILE_PATH));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readPeopleFromFile(), PersonSeedDTO[].class))
                .filter(personSeedDTO -> {
                    boolean filtered = personFilter(personSeedDTO);

                    if (filtered) {
                        sb.append(String.format("Successfully imported person %s %s",
                                        personSeedDTO.getFirstName(), personSeedDTO.getLastName()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid person")
                                .append(System.lineSeparator());
                    }

                    return filtered;
                }).map(personSeedDTO -> {
                    Person person = modelMapper.map(personSeedDTO, Person.class);
                    Long id = Long.parseLong(personSeedDTO.getCountry());
                    person.setCountry(countryService.getCountryById(id));
                    person.setStatusType(StatusType.valueOf(personSeedDTO.getStatusType()));
                    return person;
                })
                .forEach(personRepository::save);

        return sb.toString().trim();
    }

    private boolean personFilter(PersonSeedDTO personSeedDTO) {
        Boolean emailUniqueness = personRepository.emailUniqueness(personSeedDTO.getEmail());
        Boolean phoneUniqueness = personRepository.phoneUniqueness(personSeedDTO.getPhone());

        Boolean checkValidStatusType = checkValidStatusType(personSeedDTO.getStatusType());

        return myValidation.isValid(personSeedDTO) &&
                emailUniqueness &&
                phoneUniqueness &&
                checkValidStatusType;
    }

    private Boolean checkValidStatusType(String statusType) {
        for (StatusType value : StatusType.values()) {
            if (value.name().equals(statusType)) {
                return true;
            }
        }
        return false;
    }
}
