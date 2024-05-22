package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto findById(Long id);

    CategoryDto deleteCategoryById (Long id);

    CategoryDto updateCategoryById(CategoryDto categoryDto, Long id);

    List<CategoryDto> getAllCategories(boolean isDeleted);
}
