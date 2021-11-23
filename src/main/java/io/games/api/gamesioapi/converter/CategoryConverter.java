package io.games.api.gamesioapi.converter;

import io.games.api.gamesioapi.dto.request.CategoryRequest;
import io.games.api.gamesioapi.dto.response.CategoryResponse;
import io.games.api.gamesioapi.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryConverter {

    CategoryResponse categoryToCategoryResponse(Category category);

    Category categoryRequestToCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> categoryListToCategoryResponseList(List<Category> categoryList);

    List<Category> categoryRequestListToCategoryList(List<CategoryRequest> categoryRequestList);

    Page<CategoryResponse> categoryListToCategoryResponsePage(Page<Category> categoryPage);

}
