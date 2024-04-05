package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.StarsSeedDTO;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.MyValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Repository
public class StarServiceImpl implements StarService {
    public static final String STARS_FILE_PATH = "src/main/resources/files/json/stars.json";
    private final StarRepository starRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;

    public StarServiceImpl(Gson gson, StarRepository starRepository, ModelMapper modelMapper, MyValidation myValidation) {
        this.gson = gson;
        this.starRepository = starRepository;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STARS_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readStarsFileContent(), StarsSeedDTO[].class))
                .filter(starsSeedDTO -> {
                    boolean filtered = filterStars(starsSeedDTO);

                    if (filtered) {
                        sb.append(String.format("Successfully imported star %s - %.2f light years",
                                starsSeedDTO.getName(), starsSeedDTO.getLightYears()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid star")
                                .append(System.lineSeparator());
                    }
                    return filtered;
                })
                .map(starsSeedDTO -> modelMapper.map(starsSeedDTO, Star.class))
                .forEach(starRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String exportStars() {
        return null;
    }

    @Override
    public Star getStarById(Long observingStarId) {
        return starRepository.getStarById(observingStarId).orElse(null);
    }

    private boolean filterStars(StarsSeedDTO starsSeedDTO) {

        return myValidation.isValid(starsSeedDTO) &&
                starRepository.checkNameUniqueness(starsSeedDTO.getName());
    }
}
