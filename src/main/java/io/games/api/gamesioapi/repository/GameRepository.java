package io.games.api.gamesioapi.repository;

import io.games.api.gamesioapi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByName(String name);
}
