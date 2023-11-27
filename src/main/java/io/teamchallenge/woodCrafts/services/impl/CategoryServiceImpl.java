package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.mapper.CategoryMapper;
import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.services.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<CategoryDto> findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(CategoryMapper.convertCategoryToCategoryDto(category));
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoryRepository.delete(category);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateCategoryById(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(CategoryMapper::convertCategoryToCategoryDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(categoryDtos);
    }
}
