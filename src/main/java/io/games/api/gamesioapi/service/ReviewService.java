package io.games.api.gamesioapi.service;

import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.request.ReviewRequest;
import io.games.api.gamesioapi.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;

public interface ReviewService {

    Page<ReviewResponse> getReviewsPage(PageableRequest pageRequest);

    ReviewResponse postReview(ReviewRequest reviewRequest);

    ReviewResponse getReviewById(Integer id);

    void deleteReviewById(Integer id);
}
