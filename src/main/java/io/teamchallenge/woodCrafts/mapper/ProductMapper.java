package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.utils.IdConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static Product convertProductDtoToProduct(ProductDto productDto) {
        ModelMapper modelMapper = new ModelMapper();
        if (productDto.getDeleted() ==null){
            productDto.setDeleted(false);
        }
        modelMapper.typeMap(ProductDto.class,Product.class)
                .addMappings(mapper->{
                    mapper.skip(Product::setCreationDate);
                    mapper.skip(Product::setUpdateDate);
                });

        Product product = modelMapper.map(productDto, Product.class);
        product.setId(IdConverter.convertStringToId(productDto.getId()));
        return product;
    }

    public static ProductDto convertProductToProductDto(Product product) {
        ModelMapper modelMapper = new ModelMapper();

        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setId(IdConverter.convertIdToString(product.getId()));
        return productDto;
    }

    public static void updateProductFromProductDto(ProductDto productDto, Product product) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Condition<Object,Object> notNull=ctx-> Objects.nonNull(ctx.getSource());
        modelMapper.typeMap(ProductDto.class, Product.class)
                .setPropertyCondition(notNull)
                .addMappings(mapper -> {
                    mapper.skip(Product::setCategory);
                    mapper.skip(Product::setColor);
                    mapper.skip(Product::setMaterial);
                    mapper.skip(Product::setCreationDate);
                    mapper.when(notNull);
                });
        modelMapper.map(productDto, product);
    }
}
