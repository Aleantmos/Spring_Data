package softuni.exam.service.impl;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import softuni.exam.models.entities.city.City;
import softuni.exam.models.entities.city.dto.CityImportDto;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.CountryService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.util.AppPaths.CITY_JSON_FILE_PATH;
import static softuni.exam.util.IO.objectFromJson;
import static softuni.exam.util.IO.reader;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryService countryService;
    private final ModelMapper modelMapper;

    public CityServiceImpl(CityRepository cityRepository, CountryService countryService, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return reader(CITY_JSON_FILE_PATH);
    }

    @Override
    public String importCities() {
        if (!areImported()) {
            //File file = new File(CITY_JSON_FILE_PATH);
            Set<String> uniqueCityNames = new HashSet<>();

            setConverter();
            try {
                List<CityImportDto> cityCollectionDto = objectFromJson(CityImportDto[].class, CITY_JSON_FILE_PATH);

                List<City> cities = cityCollectionDto.stream()
                        .filter(this::doSizeFilter)
                        .filter(cityDto -> doUniquenessFilter(cityDto.getCityName(), uniqueCityNames))
                        .map(cityDto -> modelMapper.map(cityDto, City.class))
                        .filter(city -> city.getCountry() != null)
                        .collect(Collectors.toList());

                cityRepository.saveAll(cities);

                return "Cities are imported.";
            } catch (JsonSyntaxException | JsonIOException e) {
                return "File not imported.";
            }
        }
        return "Cities already imported.";
    }

    @Override
    public City getCityById(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    private void setConverter() {
        modelMapper.addMappings(new PropertyMap<CityImportDto, City>() {
            @Override
            protected void configure() {
                map().setName(source.getCityName());
                map().setDescription(source.getDescription());
                map().setPopulation(source.getPopulation());

                using(ctx -> {
                    Long countryId = ((CityImportDto) ctx.getSource()).getCountry();
                    return countryId == null ? null : countryService.getCountryById(countryId);
                }).map(source, destination.getCountry());
            }
        });
    }


    private boolean doUniquenessFilter(String name, Set<String> uniqueCityNames) {
        if (uniqueCityNames.contains(name)) {
            return false;
        }

        uniqueCityNames.add(name);
        return true;
    }

    private boolean doSizeFilter(CityImportDto cityDto) {
        String name = cityDto.getCityName();
        return name.length() > 2 &&
                name.length() <= 60 &&
                cityDto.getDescription().length() > 2 &&
                cityDto.getPopulation() >= 500;
    }
}
