package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.exception.DuplicateException;
import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.mapper.CategoryMapper;
import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CategoryMapper categoryMapper;
    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void testSave_whenThereIsNoExistingCategory_shouldReturnNewSavedCategory() {
        //given
        Long id = 1L;
        String randomName = UUID.randomUUID().toString();
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();
        CategoryDto savedCategoryDto = new CategoryDto();
        savedCategoryDto.setId(id);
        savedCategoryDto.setName(randomName);
        Category savedCategory = new Category();
        savedCategory.setId(id);
        savedCategory.setName(randomName);
        when(categoryRepository.findByName(categoryDto.getName())).thenReturn(Optional.empty());
        when(categoryMapper.categoryDtoToCategory(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.categoryToCategoryDto(savedCategory)).thenReturn(savedCategoryDto);

        //when
        CategoryDto actualResult = categoryService.save(categoryDto);

        //then
        assertNotNull(actualResult);
        assertEquals(id, actualResult.getId());
        verify(categoryRepository, times(1)).findByName(categoryDto.getName());
        verify(categoryMapper, times(1)).categoryDtoToCategory(categoryDto);
        verify(categoryMapper, times(1)).categoryToCategoryDto(savedCategory);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testSave_whenThereIsExistCategory_shouldReturnException() {
        //given
        String randomName = UUID.randomUUID().toString();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(randomName);
        Category existingCategory = new Category();
        existingCategory.setName(randomName);
        String exceptionMessage = String.format("Category with name='%s' already exists", categoryDto.getName());
        when(categoryRepository.findByName(categoryDto.getName())).thenReturn(Optional.of(existingCategory));

        //when
        DuplicateException duplicateException = assertThrows(DuplicateException.class, () -> categoryService.save(categoryDto));


        //then
        assertEquals(exceptionMessage, duplicateException.getMessage());
        verify(categoryRepository, times(1)).findByName(categoryDto.getName());
        verify(categoryMapper, never()).categoryToCategoryDto(any());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void testFindCategoryByIdSuccess() {
        //given
        Random random = new Random();
        Long id = random.nextLong();
        Category category = new Category();
        category.setId(id);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        //when
        CategoryDto actualResult = categoryService.findById(id);

        //then
        assertNotNull(actualResult);
        assertEquals(id, actualResult.getId());
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryMapper, times(1)).categoryToCategoryDto(category);
    }

    @Test
    void testFindCategoryById_whenEntityNotFound_shouldReturnException() {
        //given
        Random random = new Random();
        Long id = random.nextLong();
        Category category = new Category();
        category.setId(id);
        category.setName("test category");
        String expectedMessage = String.format("Category with id='%s' not found", id);
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException actualResult = assertThrows(EntityNotFoundException.class, () -> categoryService.findById(id));

        //then
        assertNotNull(actualResult);
        assertEquals(expectedMessage, actualResult.getMessage());
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryMapper, never()).categoryToCategoryDto(any());
    }


    @Test
    void deleteCategoryById_EntityNotFound() {
        // Given
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategoryById(categoryId));

        // Then
        assertEquals("Category with id='1' not found", exception.getMessage());

        // Verify that save method was not called
        verify(categoryRepository, never()).save(any());

        // Verify that categoryMapper was not called
        verify(categoryMapper, never()).categoryToCategoryDto(any());
    }

    @Test
    void deleteCategoryById_Success() {
        //given
        Random random = new Random();
        Long categoryId = random.nextLong();
        Category category = new Category();
        category.setId(categoryId);
        category.setDeleted(false); // Assuming initially it's not deleted
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(new CategoryDto());

        //when
        CategoryDto deletedCategoryDto = categoryService.deleteCategoryById(categoryId);

        //then
        assertTrue(category.isDeleted()); // Check if the category is marked as deleted
        assertNotNull(deletedCategoryDto);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).categoryToCategoryDto(category);
    }

    @Test
    void updateCategoryById() {
        //given
        Random random = new Random();
        Long categoryId = random.nextLong();
        Category category = new Category();
        category.setId(categoryId);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryId);
        categoryDto.setName("new name");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        doAnswer((answer) -> {
            category.setName(categoryDto.getName());
            return null;
        }).when(categoryMapper).updateCategoryFromCategoryDto(category, categoryDto);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(new CategoryDto());

        //when
        CategoryDto actualResult = categoryService.updateCategoryById(categoryDto, categoryId);

        //then
        assertNotNull(actualResult);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, times(1)).updateCategoryFromCategoryDto(category, categoryDto);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).categoryToCategoryDto(category);
    }

    @Test
    void updateCategoryById_whenCategoryNotFound_shouldReturnException() {
        //given
        Random random = new Random();
        Long categoryId = random.nextLong();
        CategoryDto categoryDto = new CategoryDto();
        String expectedMessage = String.format("Category with id='%s' not found", categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException actualResult = assertThrows(EntityNotFoundException.class, () -> categoryService.updateCategoryById(categoryDto, categoryId));

        //then
        assertNotNull(actualResult);
        assertEquals(expectedMessage, actualResult.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, never()).updateCategoryFromCategoryDto(any(), any());
        verify(categoryRepository, never()).save(any());
        verify(categoryMapper, never()).categoryToCategoryDto(any());
    }

    @Test
    void getAllCategories() {
        //given
        Boolean deleted = false;
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();
        int expectedListSize = 1;
        when(categoryRepository.findAllByDeleted(deleted)).thenReturn(Collections.singletonList(category));
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        //when
        List<CategoryDto> categories = categoryService.getAllCategories(deleted);

        //then
        assertNotNull(categories);
        assertEquals(expectedListSize, categories.size());
        verify(categoryRepository, times(1)).findAllByDeleted(deleted);
        verify(categoryMapper, times(1)).categoryToCategoryDto(category);
    }
}