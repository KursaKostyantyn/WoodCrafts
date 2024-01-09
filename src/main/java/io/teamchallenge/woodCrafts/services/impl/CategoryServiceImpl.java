package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.exception.DuplicateException;
import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<Void> createCategory(CategoryDto categoryDto) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDto.getName());
        if (existingCategory.isPresent()){
            throw new DuplicateException(String.format("Category with name='%s' already exists",categoryDto.getName()));
        }
        Category category = CategoryMapper.convertCategoryDtoToCategory(categoryDto);
        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<CategoryDto> findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(CategoryMapper.convertCategoryToCategoryDto(category));
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        category.setDeleted(true);
        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateCategoryById(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDto.getName());
        if (existingCategory.isPresent() && existingCategory.get().getId().equals(id)){
            throw new DuplicateException(String.format("Category with name='%s' already exists",categoryDto.getName()));
        }
        category.setName(categoryDto.getName());
        category.setDeleted(categoryDto.isDeleted());
        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategories(boolean isDeleted) {
        List<Category> categories = categoryRepository.findAllByDeleted(isDeleted);
        List<CategoryDto> categoryDtos = categories.stream()
                .map(CategoryMapper::convertCategoryToCategoryDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(categoryDtos);
    }
}
