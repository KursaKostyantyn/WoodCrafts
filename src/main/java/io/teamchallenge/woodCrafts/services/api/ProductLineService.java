package io.teamchallenge.woodCrafts.services.api;


import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.ProductLine;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;

import java.util.List;

public interface ProductLineService {
    List<ProductLine> getListOfProductLinesFromListOfProductLinesDto(List<ProductLineDto> productLineDtos);

    List<ProductLine> updateProductLines(List<ProductLineDto> productLineDtos, Order order);
}
