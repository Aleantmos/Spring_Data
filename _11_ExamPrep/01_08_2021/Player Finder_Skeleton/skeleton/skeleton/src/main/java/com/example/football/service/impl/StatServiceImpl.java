package com.example.football.service.impl;

import com.example.football.models.dto.stat.StatRootSeedDTO;
import com.example.football.models.dto.stat.StatSeedDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.MyValidation;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    private static final String STATS_FILE_PATH = "src/main/resources/files/xml/stats.xml";
    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;

    public StatServiceImpl(StatRepository statRepository, XmlParser xmlParser, ModelMapper modelMapper, MyValidation myValidation) {
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(STATS_FILE_PATH, StatRootSeedDTO.class)
                .getStats()
                .forEach(statSeedDTO -> {
                    boolean filtered = validateStats(statSeedDTO);

                    if (filtered) {
                        Stat stat = modelMapper.map(statSeedDTO, Stat.class);

                        statRepository.save(stat);
                        sb.append(String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                                stat.getPassing(), stat.getShooting(), stat.getEndurance()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid stat")
                                .append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    private boolean validateStats(StatSeedDTO statSeedDTO) {
        return myValidation.isValid(statSeedDTO) &&
                statRepository.checkStatUniqueness(statSeedDTO.getPassing(),
                        statSeedDTO.getShooting(),
                        statSeedDTO.getEndurance());
    }

    @Override
    public Stat getStatById(String strId) {
        Long id = Long.parseLong(strId);
        return statRepository.getById(id);
    }
}
