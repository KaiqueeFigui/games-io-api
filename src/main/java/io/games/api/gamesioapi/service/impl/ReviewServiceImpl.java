package io.games.api.gamesioapi.service.impl;

import io.games.api.gamesioapi.config.MyUserDetails;
import io.games.api.gamesioapi.converter.ReviewConverter;
import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.request.ReviewRequest;
import io.games.api.gamesioapi.dto.response.ReviewResponse;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.model.Review;
import io.games.api.gamesioapi.model.Role;
import io.games.api.gamesioapi.repository.ReviewRepository;
import io.games.api.gamesioapi.service.ReviewService;
import io.games.api.gamesioapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    @Override
    @Cacheable("reviews")
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
    @Cacheable("review-by-id")
    public ReviewResponse getReviewById(Integer id) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            throw new ApiRequestException("Review not found", HttpStatus.NOT_FOUND);
        });

        return reviewConverter.reviewToReviewResponse(review);
    }

    @Override
    @CacheEvict("review-by-id")
    public void deleteReviewById(Integer id) {

        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            throw new ApiRequestException("Review does not exists", HttpStatus.NOT_FOUND);
        });

        if (review.getAuthor().getId().equals(userDetails.getId()) || isUserAdmin(userDetails.getAuthorities())){

            reviewRepository.delete(review);
        }else {

            throw new ApiRequestException("User is not authorized", HttpStatus.FORBIDDEN);
        }
    }

    private boolean isUserAdmin(Collection<? extends GrantedAuthority> grantedAuthorities){
        List<GrantedAuthority> roles =  grantedAuthorities.stream()
                .filter(user -> user.equals(Constants.ROLE_ADMIN)).collect(Collectors.toList());

        return !roles.isEmpty();
    }
}
