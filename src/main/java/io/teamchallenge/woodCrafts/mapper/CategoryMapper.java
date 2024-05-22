package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryDtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToCategoryDto(Category category);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromCategoryDto(@MappingTarget Category category, CategoryDto categoryDto);
}
