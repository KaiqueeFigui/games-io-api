package io.games.api.gamesioapi.service;

import io.games.api.gamesioapi.dto.request.GameRequest;
import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.response.GameResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GameService {

    GameResponse postGame(GameRequest gameRequest);

    GameResponse getGameById(Integer id);

    GameResponse getGameByName(String name);

    Page<GameResponse> getGamePage(PageableRequest pageableRequest);
}
