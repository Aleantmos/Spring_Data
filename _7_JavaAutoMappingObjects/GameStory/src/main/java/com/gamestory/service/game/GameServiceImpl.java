package com.gamestory.service.game;

import com.gamestory.domain.dto.GameDTO;
import com.gamestory.domain.entity.Game;
import com.gamestory.repository.GamesRepository;
import com.gamestory.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.gamestory.constants.Validations.ADDED_GAME_MESSAGE;
import static com.gamestory.constants.Validations.IMPOSSIBLE_COMMAND;

@Service
public class GameServiceImpl implements GameService {

    private final GamesRepository gamesRepository;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public GameServiceImpl(GamesRepository gamesRepository, UserService userService) {
        this.gamesRepository = gamesRepository;
        this.userService = userService;
    }

    @Override
    public String addGame(String[] args) {

        if (userService.getLoggedUser() == null || !userService.getLoggedUser().getAdmin()) {
            return IMPOSSIBLE_COMMAND;
        }

        final String title = args[1];
        final BigDecimal price = new BigDecimal(args[2]);
        final float size = Float.parseFloat(args[3]);
        final String trailer = args[4];
        final String imageURL = args[5];
        final String description = args[6];

        final String[] releaseDateString = args[7].split("-");
        final LocalDate releaseDate
                = LocalDate.of(
                Integer.parseInt(releaseDateString[0]),
                Integer.parseInt(releaseDateString[1]),
                Integer.parseInt(releaseDateString[2]));

        GameDTO gameDTO = new GameDTO(title, trailer, imageURL, size, price, description, releaseDate);

        Game gameToSave = modelMapper.map(gameDTO, Game.class);

        gamesRepository.save(gameToSave);

        return String.format(ADDED_GAME_MESSAGE, gameToSave.getTitle());
    }

    @Override
    public String editGame(String[] args) {
        return null;
    }

    @Override
    public String deleteGame(Long id) {
        return null;
    }
}
