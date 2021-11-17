package io.games.api.gamesioapi.converter.impl;

import io.games.api.gamesioapi.config.MyUserDetails;
import io.games.api.gamesioapi.converter.GameConverter;
import io.games.api.gamesioapi.converter.ReviewConverter;
import io.games.api.gamesioapi.converter.UserConverter;
import io.games.api.gamesioapi.dto.request.ReviewRequest;
import io.games.api.gamesioapi.dto.response.ReviewResponse;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.model.Game;
import io.games.api.gamesioapi.model.Review;
import io.games.api.gamesioapi.repository.GameRepository;
import io.games.api.gamesioapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewConverterImpl implements ReviewConverter {

    private final UserRepository userRepository;
    private final GameConverter gameConverter;
    private final GameRepository gameRepository;
    private final UserConverter userConverter;

    @Override
    public ReviewResponse reviewToReviewResponse(Review review) {

        Game game = gameRepository.findById(review.getGame().getId()).orElseThrow(() -> {
            throw new ApiRequestException("Game not found", HttpStatus.NOT_FOUND);
        });

        return ReviewResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .subTitle(review.getSubTitle())
                .content(review.getContent())
                .author(userConverter.userToUserResponse(review.getAuthor()))
                .game(gameConverter.gameToGameResponse(game))
                .build();
    }

    @Override
    public Review reviewRequestToReview(ReviewRequest reviewRequest) {

        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Review.builder()
                .title(reviewRequest.getTitle())
                .subTitle(reviewRequest.getSubTitle())
                .content(reviewRequest.getContent())
                .author(userRepository.findById(userDetails.getId()).get())
                .game(reviewRequest.getGame())
                .build();
    }

    @Override
    public Page<ReviewResponse> reviewPageToReviewResponsePage(Page<Review> reviewPage) {

        List<ReviewResponse> reviewResponseList = reviewPage.
                stream()
                .map(this::reviewToReviewResponse)
                .collect(Collectors.toList());

        Page<ReviewResponse> reviewResponses = new PageImpl<ReviewResponse>(reviewResponseList,
                reviewPage.getPageable(),
                reviewPage.getTotalElements());

        return reviewResponses;
    }
}
