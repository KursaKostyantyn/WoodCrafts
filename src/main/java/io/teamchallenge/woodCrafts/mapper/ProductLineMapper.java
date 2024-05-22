package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.ProductLine;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductLineMapper {
    ProductLineDto productLineToProductLineDto(ProductLine productLine);

    ProductLine productLineDtoToProductLine(ProductLineDto productLineDto);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    void updateProductLineFromProductLineDto(@MappingTarget ProductLine productLine, ProductLineDto productLineDto);
}
