package io.teamchallenge.sosna.mapper;

import io.teamchallenge.sosna.models.Category;
import io.teamchallenge.sosna.models.dto.CategoryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(categoryDto, Category.class);
    }

    public static CategoryDto convertCategoryToCategoryDto(Category category) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(category, CategoryDto.class);
    }
}
