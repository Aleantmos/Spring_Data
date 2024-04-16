package exam.service.impl;

import exam.model.dto.town.TownSeedDTO;
import exam.model.dto.town.TownSeedRootDTO;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.MyValidation;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {
    private final String TOWN_FILE_PATH = "src/main/resources/files/xml/towns.xml";
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;
    private final XmlParser xmlParser;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, MyValidation myValidation, XmlParser xmlParser) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_PATH));
    }

    @Override
    public String importTowns() throws JAXBException, IOException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(TOWN_FILE_PATH, TownSeedRootDTO.class)
                .getTowns()
                .forEach(townSeedDTO -> {
                    boolean filtered = filterTown(townSeedDTO);

                    if (filtered) {

                        Town town = modelMapper.map(townSeedDTO, Town.class);

                        townRepository.save(town);
                        sb.append(String.format("Successfully imported Town %s", town.getName()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid town")
                                .append(System.lineSeparator());
                    }
                });


        return sb.toString().trim();
    }

    private boolean filterTown(TownSeedDTO townSeedDTO) {
        return myValidation.isValid(townSeedDTO) &&
                townRepository.getTownByName(townSeedDTO.getName()) == null;
    }

    @Override
    public Town getTownByName(String name) {
        return townRepository.getTownByName(name);
    }
}
