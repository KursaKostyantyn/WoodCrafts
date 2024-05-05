package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    void save(CategoryDto categoryDto);

    CategoryDto findCategoryById(Long id);

    void deleteCategoryById (Long id);

    void updateCategoryById(CategoryDto categoryDto, Long id);

    List<CategoryDto> getAllCategories(boolean isDeleted);
}
