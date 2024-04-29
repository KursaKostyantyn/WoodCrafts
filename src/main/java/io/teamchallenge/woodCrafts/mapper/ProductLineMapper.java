package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.ProductLine;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductLineMapper {

    public static ProductLine convertProductLineDtoToProductLine(ProductLineDto productLineDto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(productLineDto, ProductLine.class);
    }

    public static ProductLineDto convertProductLineToProductLineDto(ProductLine productLine) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDto productDto = ProductMapper.convertProductToProductDto(productLine.getProduct());
        ProductLineDto productLineDto = modelMapper.map(productLine, ProductLineDto.class);
        productLineDto.setProduct(productDto);
        return productLineDto;
    }

    public static void updateProductLineFromProductLineDto(ProductLine productLine, ProductLineDto productLineDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(ProductLineDto.class, ProductLine.class)
                .addMappings(mapper -> {
                    mapper.skip(ProductLine::setProduct);
                    mapper.skip(ProductLine::setOrder);
                });
        modelMapper.map(productLineDto, productLine);
    }

}
