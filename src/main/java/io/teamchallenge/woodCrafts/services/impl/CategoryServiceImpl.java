package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.exception.DuplicateException;
import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.mapper.CategoryMapper;
import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.services.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDto.getName());
        if (existingCategory.isPresent()) {
            throw new DuplicateException(String.format("Category with name='%s' already exists", categoryDto.getName()));
        }
        Category category = categoryMapper.categoryDtoToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(savedCategory);
    }

    @Transactional
    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        category.setDeleted(true);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(updatedCategory);
    }

    @Transactional
    @Override
    public CategoryDto updateCategoryById(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        categoryMapper.updateCategoryFromCategoryDto(category, categoryDto);
        categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Transactional
    @Override
    public List<CategoryDto> getAllCategories(boolean isDeleted) {
        List<Category> categories = categoryRepository.findAllByDeleted(isDeleted);
        return categories.stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }
}
