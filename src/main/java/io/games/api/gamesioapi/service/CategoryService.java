package io.games.api.gamesioapi.service;

import io.games.api.gamesioapi.dto.request.CategoryRequest;
import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    CategoryResponse postCategory(CategoryRequest categoryRequest);

    CategoryResponse getCategoryById(Integer id);

    CategoryResponse getCategoryByName(String name);

    List<CategoryResponse> getCategoryList();

    Page<CategoryResponse> getCategoryPage(PageableRequest pageableRequest);
}
