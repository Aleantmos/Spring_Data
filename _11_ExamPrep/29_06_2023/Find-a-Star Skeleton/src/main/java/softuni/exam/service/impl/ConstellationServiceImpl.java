package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.MyValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private static final String CONSTELLATIONS_FILE_PATH = "src/main/resources/files/json/constellations.json";
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson, ModelMapper modelMapper, MyValidation myValidation) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATIONS_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readConstellationsFromFile(), ConstellationSeedDTO[].class))
                .filter(constellationSeedDTO -> {
                    boolean filtered = filterConstellations(constellationSeedDTO);

                    if (filtered) {
                        sb.append(String.format("Successfully imported constellation %s - %s.",
                                constellationSeedDTO.getName(), constellationSeedDTO.getDescription()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid constellation")
                                .append(System.lineSeparator());
                    }
                    return filtered;
                })
                .map(constellationSeedDTO -> modelMapper.map(constellationSeedDTO, Constellation.class))
                .forEach(constellationRepository::save);

        return sb.toString().trim();
    }

    @Override
    public Constellation getConstellationById(Long id) {
        return constellationRepository.findById(id).orElse(null);
    }

    private boolean filterConstellations(ConstellationSeedDTO constellationSeedDTO) {
        return myValidation.isValid(constellationSeedDTO) &&
                checkConstellationUniqueness(constellationSeedDTO.getName());
    }

    private boolean checkConstellationUniqueness(String name) {
        return constellationRepository.checkIfUniqueByName(name);
    }
}
