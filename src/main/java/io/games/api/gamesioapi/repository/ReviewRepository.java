package io.games.api.gamesioapi.repository;

import io.games.api.gamesioapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
