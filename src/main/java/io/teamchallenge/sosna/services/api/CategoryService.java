package io.teamchallenge.sosna.services.api;

import io.teamchallenge.sosna.models.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<Void> saveCategory(CategoryDto categoryDto);

    ResponseEntity<CategoryDto> getCategoryById(Long id);
}
