package io.games.api.gamesioapi.converter.impl;

import io.games.api.gamesioapi.converter.CategoryConverter;
import io.games.api.gamesioapi.converter.GameConverter;
import io.games.api.gamesioapi.dto.request.GameRequest;
import io.games.api.gamesioapi.dto.response.GameResponse;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.model.Category;
import io.games.api.gamesioapi.model.Game;
import io.games.api.gamesioapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameConverterImpl implements GameConverter {

    private final CategoryConverter categoryConverter;
    private final CategoryRepository categoryRepository;

    @Override
    public GameResponse gameToGameResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .name(game.getName())
                .categories(categoryConverter.categoryListToCategoryResponseList(game.getCategories()))
                .build();
    }

    @Override
    public Game gameRequestToGame(GameRequest gameRequest) {

        List<Category> categories = gameRequest.getCategories();

        for (Category category: categories) {
            categoryRepository.findById(category.getId())
                    .orElseThrow(() -> {
                        throw new ApiRequestException("Category does not exists", HttpStatus.BAD_REQUEST);
                    });
        }

        return Game.builder()
                .name(gameRequest.getName())
                .categories(categories)
                .build();
    }

    @Override
    public Page<GameResponse> gamePageToGameResponsePage(Page<Game> gamePage) {

        List<GameResponse> gameList = gamePage
                .stream()
                .map(this::gameToGameResponse)
                .collect(Collectors.toList());

        Page<GameResponse> gameResponsePage = new PageImpl<>(gameList, gamePage.getPageable(), gamePage.getTotalElements());

        return gameResponsePage;
    }
}
