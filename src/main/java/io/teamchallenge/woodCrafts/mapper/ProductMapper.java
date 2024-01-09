package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static Product convertProductDtoToProduct(ProductDto productDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(ProductDto.class,Product.class)
                .addMappings(mapper->{
                    mapper.skip(Product::setCreationDate);
                    mapper.skip(Product::setUpdateDate);
                });

        return modelMapper.map(productDto, Product.class);
    }

    public static ProductDto convertProductToProductDto(Product product) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(product, ProductDto.class);
    }

    public static void updateProductFromProductDto(ProductDto productDto, Product product) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(ProductDto.class, Product.class)
                .addMappings(mapper -> {
                    mapper.skip(Product::setCategory);
                    mapper.skip(Product::setColor);
                    mapper.skip(Product::setMaterial);
                    mapper.skip(Product::setCreationDate);
                });
        modelMapper.map(productDto, product);
    }
}
