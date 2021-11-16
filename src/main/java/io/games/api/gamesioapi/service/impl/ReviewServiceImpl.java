package io.games.api.gamesioapi.service.impl;

import io.games.api.gamesioapi.converter.ReviewConverter;
import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.request.ReviewRequest;
import io.games.api.gamesioapi.dto.response.ReviewResponse;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.model.Review;
import io.games.api.gamesioapi.repository.ReviewRepository;
import io.games.api.gamesioapi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    @Override
    public Page<ReviewResponse> getReviewsPage(PageableRequest pageRequest) {

        PageRequest pageable = PageRequest.of(pageRequest.getPageNum(), pageRequest.getPageSize());

        return reviewConverter.reviewPageToReviewResponsePage(reviewRepository
                        .findAll(pageable));
    }

    @Override
    public ReviewResponse postReview(ReviewRequest reviewRequest) {
        Review review = reviewRepository.save(reviewConverter.reviewRequestToReview(reviewRequest));
        return reviewConverter.reviewToReviewResponse(review);
    }

    @Override
    @Cacheable("review")
    public ReviewResponse getReviewById(Integer id) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            throw new ApiRequestException("Review not found", HttpStatus.NOT_FOUND);
        });

        return reviewConverter.reviewToReviewResponse(review);
    }
}
