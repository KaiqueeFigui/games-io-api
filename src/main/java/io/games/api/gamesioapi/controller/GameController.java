package io.games.api.gamesioapi.controller;

import io.games.api.gamesioapi.dto.request.GameRequest;
import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.response.GameResponse;
import io.games.api.gamesioapi.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<Page<GameResponse>> getGamePage(PageableRequest pageableRequest){
        return ResponseEntity.status(200).body(gameService.getGamePage(pageableRequest));
    }

    @PostMapping
    public ResponseEntity<GameResponse> postGame(@RequestBody GameRequest gameRequest){
        return ResponseEntity.status(201).body(gameService.postGame(gameRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGameById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(gameService.getGameById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<GameResponse> getGameByName(@PathVariable String name){
        return ResponseEntity.status(200).body(gameService.getGameByName(name));
    }
}
