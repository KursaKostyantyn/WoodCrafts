package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);
}
