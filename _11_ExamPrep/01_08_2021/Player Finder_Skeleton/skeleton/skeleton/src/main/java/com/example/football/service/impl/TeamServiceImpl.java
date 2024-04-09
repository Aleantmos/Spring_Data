package com.example.football.service.impl;

import com.example.football.models.dto.TeamSeedDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {
    public static final String TEAMS_FILE_PATH = "src/main/resources/files/json/teams.json";
    private final TeamRepository teamRepository;
    private final TownService townService;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;

    public TeamServiceImpl(TeamRepository teamRepository, TownService townService, Gson gson, ModelMapper modelMapper, MyValidation myValidation) {
        this.teamRepository = teamRepository;
        this.townService = townService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
    }


    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readTeamsFileContent(), TeamSeedDTO[].class))
                .forEach(teamSeedDTO -> {
                    boolean filtered = filterTeams(teamSeedDTO);
                    if (filtered) {
                        Team team = modelMapper.map(teamSeedDTO, Team.class);
                        Town townByName = townService.getTownByName(teamSeedDTO.getName());

                        team.setTown(townByName);
                        teamRepository.save(team);
                        sb.append(String.format("Successfully imported Team Rowe - 333624",
                                        team.getName(), team.getFanBase()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid Team")
                                .append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    private boolean filterTeams(TeamSeedDTO teamSeedDTO) {
        Boolean nameUniqueness = teamRepository.checkNameUniqueness(teamSeedDTO.getName());
        return myValidation.isValid(teamSeedDTO) && nameUniqueness;
    }
}
