package io.games.api.gamesioapi.controller;

import io.games.api.gamesioapi.dto.request.CategoryRequest;
import io.games.api.gamesioapi.dto.response.CategoryResponse;
import io.games.api.gamesioapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> postCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return ResponseEntity.status(201).body(categoryService.postCategory(categoryRequest));
    }
}
