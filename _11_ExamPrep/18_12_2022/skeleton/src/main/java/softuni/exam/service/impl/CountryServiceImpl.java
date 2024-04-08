package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.MyValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CountryServiceImpl implements CountryService {
    public static final String COUNTRIES_FILE_PATH = "src/main/resources/files/json/countries.json";
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final MyValidation myValidation;
    private final ModelMapper modelMapper;


    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, MyValidation myValidation, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.myValidation = myValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readCountriesFileContent(), CountrySeedDTO[].class))
                .filter(countrySeedDTO -> {
                    boolean filtered = filterCountries(countrySeedDTO);

                    if (filtered) {
                        sb.append(String.format("Successfully imported country %s - %s",
                                        countrySeedDTO.getName(), countrySeedDTO.getCountryCode()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid country")
                                .append(System.lineSeparator());
                    }
                    return filtered;
                }).map(countrySeedDTO -> modelMapper.map(countrySeedDTO, Country.class))
                .forEach(countryRepository::save);

        return sb.toString().trim();
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    private boolean filterCountries(CountrySeedDTO countrySeedDTO) {
        Boolean nameUniqueness = countryRepository.checkNameUniqueness(countrySeedDTO.getName());
        Boolean codeUniqueness = countryRepository.checkCodeUniqueness(countrySeedDTO.getCountryCode());

        return myValidation.isValid(countrySeedDTO) && nameUniqueness && codeUniqueness;
    }
}
