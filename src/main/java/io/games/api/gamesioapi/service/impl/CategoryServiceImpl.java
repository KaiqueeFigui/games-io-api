package io.games.api.gamesioapi.service.impl;

import io.games.api.gamesioapi.converter.CategoryConverter;
import io.games.api.gamesioapi.dto.request.CategoryRequest;
import io.games.api.gamesioapi.dto.request.PageableRequest;
import io.games.api.gamesioapi.dto.response.CategoryResponse;
import io.games.api.gamesioapi.exception.ApiRequestException;
import io.games.api.gamesioapi.model.Category;
import io.games.api.gamesioapi.repository.CategoryRepository;
import io.games.api.gamesioapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryConverter categoryConverter;
    private final CategoryRepository categoryRepository;

    @Override
    @CacheEvict(value = {"category-by-id", "category-by-name", "category-page"})
    public CategoryResponse postCategory(CategoryRequest categoryRequest) {

        categoryRepository.findByName(categoryRequest.getName()).ifPresent(category -> {
            throw new ApiRequestException("Category already exists", HttpStatus.BAD_REQUEST);
        });

        Category category = categoryConverter.categoryRequestToCategory(categoryRequest);

        return categoryConverter.categoryToCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @Cacheable("category-by-id")
    public CategoryResponse getCategoryById(Integer id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            throw new ApiRequestException("", HttpStatus.NOT_FOUND);
        }

        return categoryConverter.categoryToCategoryResponse(categoryOptional.get());
    }

    @Override
    @Cacheable("category-by-name")
    public CategoryResponse getCategoryByName(String name) {

        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        if (categoryOptional.isEmpty()){
            throw new ApiRequestException("", HttpStatus.NOT_FOUND);
        }

        return categoryConverter.categoryToCategoryResponse(categoryOptional.get());
    }

    @Override
    public List<CategoryResponse> getCategoryList() {

        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()){
            throw new ApiRequestException("", HttpStatus.NO_CONTENT);
        }

        return categoryConverter.categoryListToCategoryResponseList(categories);
    }

    @Override
    @Cacheable("category-page")
    public Page<CategoryResponse> getCategoryPage(PageableRequest pageableRequest) {

        PageRequest pageable = PageRequest.of(pageableRequest.getPageNum(), pageableRequest.getPageSize());

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryConverter.categoryListToCategoryResponsePage(categoryPage);
    }
}
