package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static Product convertProductDtoToProduct(ProductDto productDto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(productDto, Product.class);
    }

    public static ProductDto convertProductToProductDto(Product product) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(product, ProductDto.class);
    }
}
