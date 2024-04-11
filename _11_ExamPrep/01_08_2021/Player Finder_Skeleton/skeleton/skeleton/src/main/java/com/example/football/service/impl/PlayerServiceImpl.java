package com.example.football.service.impl;

import com.example.football.models.dto.BestPlayersProjection;
import com.example.football.models.dto.player.PlayerSeedDTO;
import com.example.football.models.dto.player.PlayerSeedRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.MyValidation;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final String PLAYER_FILE_PATH = "src/main/resources/files/xml/players.xml";
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;

    public PlayerServiceImpl(PlayerRepository playerRepository, XmlParser xmlParser, ModelMapper modelMapper, MyValidation myValidation, TownService townService, TeamService teamService, StatService statService) {
        this.playerRepository = playerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
    }


    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYER_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(PLAYER_FILE_PATH, PlayerSeedRootDTO.class)
                .getPlayers()
                .forEach(playerSeedDTO -> {
                    boolean filtered = validatePlayer(playerSeedDTO);

                    if (filtered) {
                        Player player = modelMapper.map(playerSeedDTO, Player.class);
                        player.setTown(townService.getTownByName(playerSeedDTO.getTown().getName()));
                        player.setTeam(teamService.getTeamByName(playerSeedDTO.getTeam().getName()));
                        player.setStat(statService.getStatById(playerSeedDTO.getStatId().getId()));

                        playerRepository.save(player);

                        sb.append(String.format("Successfully imported Player %s %s - %s",
                                        player.getFirstName(), player.getLastName(), player.getTeam().getName()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid team")
                                .append(System.lineSeparator());

                    }
                });

        return sb.toString().trim();
    }

    private boolean validatePlayer(PlayerSeedDTO playerSeedDTO) {
        return myValidation.isValid(playerSeedDTO) &&
                playerRepository.playerEmailUniqueness(playerSeedDTO.getEmail());
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();
        List<BestPlayersProjection> bestPlayersProjections = playerRepository.exportBestPlayers();

        for (BestPlayersProjection player : bestPlayersProjections) {

            sb.append(String.format(
                    "Player - %s %s\n" +
                            "\tPosition - %s\n" +
                            "\tTeam - %s\n" +
                            "\tStadium - %s\n",
                    player.getFirstName(),
                    player.getLastName(),
                    player.getPositionName(),
                    player.getTeamName(),
                    player.getStadiumName()));
        }

        return sb.toString().trim();
    }
}
