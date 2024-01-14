package softuni.exam.service.impl;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.entities.country.Country;
import softuni.exam.models.entities.country.dto.CountryImportDto;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.util.AppPaths.COUNTRY_JSON_FILE_PATH;
import static softuni.exam.util.IO.objectFromJson;
import static softuni.exam.util.IO.reader;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() {
        return reader(COUNTRY_JSON_FILE_PATH);
    }

    @Override
    @Transactional
    public String importCountries() {
        if (!areImported()) {
            Set<String> uniqueCountryNames = new HashSet<>();
            try {
                List<CountryImportDto> countryDtoCollection = objectFromJson(CountryImportDto[].class, COUNTRY_JSON_FILE_PATH);

                List<Country> countries = countryDtoCollection.stream()
                        .filter(this::doSizeFilter)
                        .filter(countryImportDto -> doUniquenessFilter(countryImportDto, uniqueCountryNames))
                        .map(countryDto -> modelMapper.map(countryDto, Country.class))
                        .collect(Collectors.toList());
                countryRepository.saveAll(countries);

                return "Countries are imported.";
            } catch (JsonSyntaxException | JsonIOException e) {
                return "File not imported.";
            }
        }
        return "Countries already imported";
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    private boolean doUniquenessFilter(CountryImportDto countryImportDto, Set<String> uniqueCountryNames) {
        String countryName = countryImportDto.getCountryName();
        if (uniqueCountryNames.contains(countryName)) {
            return false;
        }

        uniqueCountryNames.add(countryName);
        return true;
    }

    private boolean doSizeFilter(CountryImportDto countryImportDto) {
        return countryImportDto.getCountryName().length() > 2 &&
                countryImportDto.getCountryName().length() < 60 &&
                countryImportDto.getCurrency().length() > 2 &&
                countryImportDto.getCurrency().length() < 60;
    }
}
