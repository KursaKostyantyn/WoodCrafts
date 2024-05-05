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
    public void save(CategoryDto categoryDto) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDto.getName());
        if (existingCategory.isPresent()) {
            throw new DuplicateException(String.format("Category with name='%s' already exists", categoryDto.getName()));
        }
        Category category = categoryMapper.categoryDtoToCategory(categoryDto);
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public CategoryDto findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void updateCategoryById(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", id)));
        categoryMapper.updateCategoryFromCategoryDto(category, categoryDto);
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public List<CategoryDto> getAllCategories(boolean isDeleted) {
        List<Category> categories = categoryRepository.findAllByDeleted(isDeleted);
        List<CategoryDto> categoryDtos = categories.stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());

        return categoryDtos;
    }
}
