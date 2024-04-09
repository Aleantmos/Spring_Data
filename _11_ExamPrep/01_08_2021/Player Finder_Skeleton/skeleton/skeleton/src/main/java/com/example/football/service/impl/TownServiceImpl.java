package com.example.football.service.impl;

import com.example.football.models.dto.TownSeedDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.MyValidation;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


@Service
public class TownServiceImpl implements TownService {

    private final String TOWN_FILE_PATH = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, MyValidation myValidation) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
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
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readTownsFileContent(), TownSeedDTO[].class))
                .forEach(townSeedDTO -> {
                    Boolean filtered = filterTowns(townSeedDTO);

                    if (filtered) {
                        Town town = modelMapper.map(townSeedDTO, Town.class);

                        townRepository.save(town);

                        sb.append(String.format("Successfully imported Town %s - %d",
                                town.getName(), town.getPopulation()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid Town")
                                .append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    @Override
    public Town getTownByName(String name) {
        return townRepository.getTownByName(name);
    }

    private Boolean filterTowns(TownSeedDTO townSeedDTO) {
        return myValidation.isValid(townSeedDTO) && checkNameUniqueness(townSeedDTO.getName());
    }

    private boolean checkNameUniqueness(String name) {
        Town townByName = townRepository.getTownByName(name);
        return townByName == null;
    }
}
