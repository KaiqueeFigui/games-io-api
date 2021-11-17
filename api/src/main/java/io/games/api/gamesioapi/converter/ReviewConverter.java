package io.games.api.gamesioapi.converter;

import io.games.api.gamesioapi.dto.request.ReviewRequest;
import io.games.api.gamesioapi.dto.response.ReviewResponse;
import io.games.api.gamesioapi.model.Review;
import org.springframework.data.domain.Page;

public interface ReviewConverter {

    ReviewResponse reviewToReviewResponse(Review review);

    Review reviewRequestToReview(ReviewRequest reviewRequest);

    Page<ReviewResponse> reviewPageToReviewResponsePage(Page<Review> reviewPage);
}
