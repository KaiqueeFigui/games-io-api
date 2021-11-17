package io.games.api.gamesioapi.service.impl;

import io.games.api.gamesioapi.converter.GameConverter;
import io.games.api.gamesioapi.dto.request.GameRequest;
import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.response.GameResponse;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.model.Game;
import io.games.api.gamesioapi.repository.GameRepository;
import io.games.api.gamesioapi.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameConverter gameConverter;

    @Override
    public GameResponse postGame(GameRequest gameRequest) {

        gameRepository.findByName(gameRequest.getName()).ifPresent(game -> {
            throw new ApiRequestException("Game's already exists", HttpStatus.BAD_REQUEST);
        });

        Game game = gameConverter.gameRequestToGame(gameRequest);

        return gameConverter.gameToGameResponse(gameRepository.save(game));
    }

    @Override
    @Cacheable("game-by-id")
    public GameResponse getGameById(Integer id) {

        Optional<Game> gameOptional = gameRepository.findById(id);

        gameOptional.orElseThrow(() -> {throw new ApiRequestException("", HttpStatus.NOT_FOUND);});

        return gameConverter.gameToGameResponse(gameOptional.get());
    }

    @Override
    @Cacheable("games-by-name")
    public GameResponse getGameByName(String name) {
        Optional<Game> gameOptional = gameRepository.findByName(name);

        gameOptional.orElseThrow(() -> {throw new ApiRequestException("", HttpStatus.NOT_FOUND);});

        return gameConverter.gameToGameResponse(gameOptional.get());
    }

    @Override
    @Cacheable("games-page")
    public Page<GameResponse> getGamePage(PageableRequest pageableRequest) {

        PageRequest pageable = PageRequest.of(pageableRequest.getPageNum(), pageableRequest.getPageSize());

        Page<Game> gamePage = gameRepository.findAll(pageable);

        return gameConverter.gamePageToGameResponsePage(gamePage);
    }
}
