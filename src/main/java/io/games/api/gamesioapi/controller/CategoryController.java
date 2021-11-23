package io.games.api.gamesioapi.controller;

import io.games.api.gamesioapi.dto.request.CategoryRequest;
import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.response.CategoryResponse;
import io.games.api.gamesioapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/by-id/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(categoryService.getCategoryById(id));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String name){
        return ResponseEntity.status(200).body(categoryService.getCategoryByName(name));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategoryByPage(PageableRequest pageableRequest){
        return ResponseEntity.status(200).body(categoryService.getCategoryPage(pageableRequest));
    }
}
