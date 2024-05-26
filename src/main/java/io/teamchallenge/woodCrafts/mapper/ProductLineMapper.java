package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.ProductLine;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
uses = {ProductMapper.class})
public interface ProductLineMapper {
    @Mapping(target = "orderId", source = "order.id")
    ProductLineDto productLineToProductLineDto(ProductLine productLine);

    @Mapping(target = "order.id", source = "orderId")
    ProductLine productLineDtoToProductLine(ProductLineDto productLineDto);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    void updateProductLineFromProductLineDto(@MappingTarget ProductLine productLine, ProductLineDto productLineDto);
}
