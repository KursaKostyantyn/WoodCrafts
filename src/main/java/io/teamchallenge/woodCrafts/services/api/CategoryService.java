package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<Void> saveCategory(CategoryDto categoryDto);

    ResponseEntity<CategoryDto> getCategoryById(Long id);
}
