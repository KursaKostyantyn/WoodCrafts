package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    ResponseEntity<Void> save(CategoryDto categoryDto);

    ResponseEntity<CategoryDto> findCategoryById(Long id);

    ResponseEntity<Void> deleteCategoryById (Long id);

    ResponseEntity<Void> updateCategoryById(CategoryDto categoryDto, Long id);

    ResponseEntity<List<CategoryDto>> getAllCategories(boolean isDeleted);
}
