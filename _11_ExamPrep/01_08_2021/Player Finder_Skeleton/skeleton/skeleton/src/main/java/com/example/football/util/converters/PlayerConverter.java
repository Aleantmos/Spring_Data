package com.example.football.util.converters;

import com.example.football.models.dto.player.PlayerSeedDTO;
import com.example.football.models.dto.player.PlayerStatIdDTO;
import com.example.football.models.dto.player.PlayerTeamNameDTO;
import com.example.football.models.dto.player.PlayerTownNameDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

public class PlayerConverter {
    private final ModelMapper modelMapper;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;

    public PlayerConverter(ModelMapper modelMapper, TownService townService, TeamService teamService, StatService statService) {
        this.modelMapper = modelMapper;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
        configure();
    }
    public Player convert(PlayerSeedDTO playerSeedDTO) {
        Player player = new Player();

        // Map PlayerSeedDTO to Player
        modelMapper.map(playerSeedDTO, player);

        // Convert PlayerTownNameDTO to Town and set in Player
        PlayerTownNameDTO townDTO = playerSeedDTO.getTown();
        if (townDTO != null) {
            Town town = modelMapper.map(townDTO, Town.class);
            player.setTown(town);
        }

        // Convert PlayerTeamNameDTO to Team and set in Player
        PlayerTeamNameDTO teamDTO = playerSeedDTO.getTeam();
        if (teamDTO != null) {
            Team team = modelMapper.map(teamDTO, Team.class);
            player.setTeam(team);
        }

        // Convert PlayerStatIdDTO to Stat and set in Player
        PlayerStatIdDTO statDTO = playerSeedDTO.getStatId();
        if (statDTO != null) {
            Stat stat = modelMapper.map(statDTO, Stat.class);
            player.setStat(stat);
        }

        return player;
    }
    private void configure() {
        modelMapper.typeMap(PlayerSeedDTO.class, Player.class)
                .addMappings(mapper -> {
                    mapper.map(src -> townService.getTownByName(src.getTeam().getName()), Player::setTown);
                    mapper.map(src -> teamService.getTeamByName(src.getTeam().getName()), Player::setTeam);
                    mapper.map(src -> statService.getStatById(src.getStatId().getId()), Player::setId);

//                    mapper.using(townConverter).map(PlayerSeedDTO::getTown, Player::setTown);
//                    mapper.using(teamConverter).map(PlayerSeedDTO::getTeam, Player::setTeam);
//                    mapper.using(statConverter).map(PlayerSeedDTO::getStatId, Player::setStat);
                });
    }

//    private final Converter<PlayerTownNameDTO, Town> townConverter = new AbstractConverter<PlayerTownNameDTO, Town>() {
//        @Override
//        protected Town convert(PlayerTownNameDTO source) {
//            return townService.getTownByName(source.getName());
//        }
//    };
//
//    private final Converter<PlayerTeamNameDTO, Team> teamConverter = new AbstractConverter<PlayerTeamNameDTO, Team>() {
//        @Override
//        protected Team convert(PlayerTeamNameDTO source) {
//            return teamService.getTeamByName(source.getName());
//        }
//    };
//
//    private final Converter<PlayerStatIdDTO, Stat> statConverter = new AbstractConverter<PlayerStatIdDTO, Stat>() {
//        @Override
//        protected Stat convert(PlayerStatIdDTO source) {
//            return statService.getStatById(source.getId());
//        }
//    };
}
