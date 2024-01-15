package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import softuni.exam.models.entities.enums.DayENUM;
import softuni.exam.models.entities.forecast.Forecast;
import softuni.exam.models.entities.forecast.dto.ForecastExportDto;
import softuni.exam.models.entities.forecast.dto.ForecastImportDto;
import softuni.exam.models.entities.forecast.dto.ForecastWrapper;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static softuni.exam.util.AppPaths.FORECAST_XML_FILE_PATH;
import static softuni.exam.util.IO.reader;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final CityService cityService;
    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;

    public ForecastServiceImpl(CityService cityService,
                               ForecastRepository forecastRepository,
                               ModelMapper modelMapper) {
        this.cityService = cityService;
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return reader(FORECAST_XML_FILE_PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        if (!areImported()) {

            setConverter();

            File file = new File(FORECAST_XML_FILE_PATH);

            JAXBContext context = JAXBContext.newInstance(ForecastWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ForecastWrapper forecastWrapper = (ForecastWrapper) unmarshaller.unmarshal(file);

            Map<Long, List<DayENUM>> daysForCity = new HashMap<>();

            List<Forecast> forecasts = forecastWrapper.getForecastDtos().stream()
                    .filter(this::checkNullValues)
                    .filter(forecastDto -> checkUniqueness(forecastDto, daysForCity))
                    .filter(this::checkSizes)
                    .map(forecastDto -> modelMapper.map(forecastDto, Forecast.class))
                    .filter(forecast -> forecast.getCity() != null)
                    .collect(Collectors.toList());

            forecastRepository.saveAll(forecasts);

            return "Forecasts imported successfully.";
        }

        return "Forecasts not imported.";
    }

    @Override
    public String exportForecasts() {
        List<Object[]> roughObjects = forecastRepository
                .getAllForecastsForSundayWithConstraints();

        List<ForecastExportDto> forecastExportDtos = roughObjects.stream()
                .map(roughObject -> new ForecastExportDto((Long) roughObject[0],
                        (String) roughObject[1],
                        (Double) roughObject[2],
                        (Double) roughObject[3],
                        (LocalTime) roughObject[4],
                        (LocalTime) roughObject[5]))
                .toList();

        StringBuilder result = new StringBuilder();

        List<String> stringListResult = forecastExportDtos.stream()
                .sorted(Comparator.comparing(ForecastExportDto::getMaxTemp).reversed()
                        .thenComparing(ForecastExportDto::getId))
                .map(ForecastExportDto::toString)
                .toList();

        for (String element : stringListResult) {
            result.append(element);
        }

        return result.toString();
    }

//    private ForecastExportDto mapToExportDto(Forecast forecast) {
//        return ForecastExportDto.builder()
//                .maxTemp(forecast.getMaxTemperature())
//                .minTemp(forecast.getMinTemperature())
//                .sunrise(forecast.getSunrise())
//                .sunset(forecast.getSunset())
//                .build();
//
//    }

    private void setConverter() {
        modelMapper.addMappings(new PropertyMap<ForecastImportDto, Forecast>() {
            @Override
            protected void configure() {

                using(ctx -> {
                    Long countryId = ((ForecastImportDto) ctx.getSource()).getCity();
                    return countryId == null ? null : cityService.getCityById(countryId);
                }).map(source, destination.getCity());
            }
        });
    }


    private boolean checkNullValues(ForecastImportDto forecastDto) {
        return forecastDto.getSunset() != null &&
                forecastDto.getSunrise() != null;
    }

    private boolean checkSizes(ForecastImportDto forecastDto) {
        Double max = forecastDto.getMaxTemperature();
        Double min = forecastDto.getMinTemperature();

        return max >= -20 && max <= 60 &&
                min >= -50 && min <= 40;
    }

    private boolean checkUniqueness(ForecastImportDto forecastDto, Map<Long, List<DayENUM>> daysForCity) {
        List<DayENUM> daysENUM = daysForCity.get(forecastDto.getCity());
        String dayOfWeek = forecastDto.getDayOfWeek();
        //todo -< check enum null

        if (isValidDay(dayOfWeek)) {
            DayENUM dayENUM = DayENUM.valueOf(dayOfWeek);

            if (daysENUM != null) {

                if (daysENUM.contains(dayENUM)) {
                    return false;
                }

                daysENUM.add(dayENUM);
                return true;
            }

            daysForCity.put(forecastDto.getCity(), new ArrayList<>());
            daysForCity.get(forecastDto.getCity()).add(dayENUM);
            return true;
        }
        return false;
    }

    private boolean isValidDay(String dayOfWeek) {
        DayENUM result = Arrays.stream(DayENUM.values())
                .filter(dayENUM -> dayENUM.toString().equals(dayOfWeek))
                .findFirst()
                .orElse(null);

        return result != null;
    }
}
