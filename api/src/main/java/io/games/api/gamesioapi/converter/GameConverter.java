package io.games.api.gamesioapi.converter;

import io.games.api.gamesioapi.dto.request.GameRequest;
import io.games.api.gamesioapi.dto.response.GameResponse;
import io.games.api.gamesioapi.model.Game;
import org.springframework.data.domain.Page;

public interface GameConverter {

    GameResponse gameToGameResponse(Game game);

    Game gameRequestToGame(GameRequest gameRequest);

    Page<GameResponse> gamePageToGameResponsePage(Page<Game> gamePage);
}
