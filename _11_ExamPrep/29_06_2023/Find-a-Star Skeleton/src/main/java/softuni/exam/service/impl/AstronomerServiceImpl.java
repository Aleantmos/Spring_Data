package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.astronomers.AstronomerSeedDTO;
import softuni.exam.models.dto.astronomers.AstronomerSeedRootDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.service.StarService;
import softuni.exam.util.MyValidation;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    public static final String ASTRONOMER_FILE_PATH = "src/main/resources/files/xml/astronomers.xml";
    private final AstronomerRepository astronomerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final StarService starService;
    private final MyValidation myValidation;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, XmlParser xmlParser, ModelMapper modelMapper, StarService starService, MyValidation myValidation) {
        this.astronomerRepository = astronomerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.starService = starService;
        this.myValidation = myValidation;
    }


    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMER_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(ASTRONOMER_FILE_PATH, AstronomerSeedRootDTO.class)
                .getAstronomerSeedDTO()
                .stream()
                .filter(astronomerSeedDTO -> {
                    Boolean filtered = filterAstronomers(astronomerSeedDTO);

                    if (filtered) {
                        sb.append(String.format("Successfully imported astronomer %s %s - %.2f",
                                        astronomerSeedDTO.getFirstName(),
                                        astronomerSeedDTO.getLastName(),
                                        astronomerSeedDTO.getSalary()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid star")
                                .append(System.lineSeparator());
                    }
                    return filtered;
                })
                .map(astronomerSeedDTO -> {
                    Astronomer astronomer = modelMapper.map(astronomerSeedDTO, Astronomer.class);
                    astronomer.setObservingStar(starService.getStarById(astronomerSeedDTO.getObservingStarId()));
                    return astronomer;
                })
                .forEach(astronomerRepository::save);

        return sb.toString().trim();
    }

    private Boolean filterAstronomers(AstronomerSeedDTO astronomerSeedDTO) {
        return myValidation.isValid(astronomerSeedDTO) &&
                astronomerRepository.nameUniqueness(astronomerSeedDTO.getFirstName(), astronomerSeedDTO.getLastName());
    }
}
