package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.repository.ColorRepository;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                CategoryMapper.class,
                ColorMapper.class,
                MaterialMapper.class,
                ProductMapper.EntityLoader.class
        })
public interface ProductMapper {
    @Mapping(source = "color.id", target = "colorId")
    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto productToProductDto(Product product);

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "findCategoryById")
    @Mapping(source = "materialId", target = "material", qualifiedByName = "findMaterialById")
    @Mapping(source = "colorId", target = "color", qualifiedByName = "findColorById")
    Product productDtoToProduct(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "categoryId", target = "category", qualifiedByName = "findCategoryById")
    @Mapping(source = "materialId", target = "material", qualifiedByName = "findMaterialById")
    @Mapping(source = "colorId", target = "color", qualifiedByName = "findColorById")
    void updateProductFromProductDto(@MappingTarget Product product, ProductDto productDto);


    @RequiredArgsConstructor
    @Component
    class EntityLoader {

        private final CategoryRepository categoryRepository;

        private final MaterialRepository materialRepository;

        private final ColorRepository colorRepository;

        @Named("findCategoryById")
        public Category findCategoryById(Long id) {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
        }

        @Named("findMaterialById")
        public Material findMaterialById(Long id) {
            return materialRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Material not found with id " + id));
        }

        @Named("findColorById")
        public Color findColorById(Long id) {
            return colorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Color not found with id " + id));
        }
    }
}
