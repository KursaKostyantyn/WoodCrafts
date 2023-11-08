package io.teamchallenge.sosna.services.impl;

import io.teamchallenge.sosna.mapper.CategoryMapper;
import io.teamchallenge.sosna.models.Category;
import io.teamchallenge.sosna.models.dto.CategoryDto;
import io.teamchallenge.sosna.repository.CategoryRepository;
import io.teamchallenge.sosna.services.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<Void> saveCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.convertCategoryDtoToCategory(categoryDto);
        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(CategoryMapper.convertCategoryToCategoryDto(category));
    }
}
