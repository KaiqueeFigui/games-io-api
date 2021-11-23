package io.games.api.gamesioapi.converter.impl;

import io.games.api.gamesioapi.converter.CategoryConverter;
import io.games.api.gamesioapi.dto.request.CategoryRequest;
import io.games.api.gamesioapi.dto.response.CategoryResponse;
import io.games.api.gamesioapi.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryConverterImpl implements CategoryConverter {

    @Override
    public CategoryResponse categoryToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public Category categoryRequestToCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }

    @Override
    public List<CategoryResponse> categoryListToCategoryResponseList(List<Category> categoryList) {
        return categoryList
                .stream()
                .map(this::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> categoryRequestListToCategoryList(List<CategoryRequest> categoryRequestList) {
        return categoryRequestList.stream()
                .map(this::categoryRequestToCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryResponse> categoryListToCategoryResponsePage(Page<Category> categoryPage) {

        List<CategoryResponse> categoryResponsesList = categoryPage.getContent()
                .stream()
                .map(this::categoryToCategoryResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(
                categoryResponsesList, categoryPage.getPageable(), categoryPage.getTotalElements());
    }
}
