package io.games.api.gamesioapi.controller;

import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.request.ReviewRequest;
import io.games.api.gamesioapi.dto.response.ReviewResponse;
import io.games.api.gamesioapi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getPageReviewResponse(@Valid PageableRequest pageRequest){
        return ResponseEntity.status(200).body(reviewService.getReviewsPage(pageRequest));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> postReview(@RequestBody @Valid ReviewRequest reviewRequest){
        return ResponseEntity.status(201).body(reviewService.postReview(reviewRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(reviewService.getReviewById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReviewById(@PathVariable Integer id){
        reviewService.deleteReviewById(id);
        return ResponseEntity.status(200).build();
    }
}
