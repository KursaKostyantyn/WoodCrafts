package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.mapper.ProductLineMapper;
import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.ProductLine;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;
import io.teamchallenge.woodCrafts.repository.ProductLineRepository;
import io.teamchallenge.woodCrafts.repository.ProductRepository;
import io.teamchallenge.woodCrafts.services.api.ProductLineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductLineServiceImpl implements ProductLineService {
    private final ProductRepository productRepository;
    private final ProductLineRepository productLineRepository;

    @Override
    public List<ProductLine> getListOfProductLinesFromListOfProductLinesDto(List<ProductLineDto> productLineDtos) {
        return productLineDtos.stream().map(productLineDto -> {
            ProductLine productLine = ProductLineMapper.convertProductLineDtoToProductLine(productLineDto);
            Long id = productLineDto.getProductDto().getId();
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Product with '%s' not found", id)));
            productLine.setProduct(product);
            return productLine;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductLine> updateProductLines(List<ProductLineDto> productLineDtos, Order order) {
        List<ProductLine> productLines = new ArrayList<>();
        productLineDtos.forEach(productLineDto -> {
            if (productLineDto.getId() != null) {
                Long productLineId = productLineDto.getId();
                ProductLine productLine = productLineRepository.findById(productLineId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Product line with id'%s' not found", productLineId)));
                Long productId = productLineDto.getProductDto().getId();
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", productId)));
                ProductLineMapper.updateProductLineFromProductLineDto(productLine, productLineDto);
                productLine.setProduct(product);
                productLine.setOrder(order);
                productLines.add(productLine);
            } else {
                Long id = productLineDto.getProductDto().getId();
                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Product with '%s' not found", id)));
                ProductLine productLine = ProductLineMapper.convertProductLineDtoToProductLine(productLineDto);
                productLine.setProduct(product);
                productLine.setOrder(order);
                productLines.add(productLine);
            }
        });
        List<Long> productLinesId = productLines.stream().map(ProductLine::getId).collect(Collectors.toList());
        List<ProductLine> oldProductLines = productLineRepository.findProductLinesByOrder(order);
        order.setProductLines(new ArrayList<>());
        oldProductLines.forEach(productLine -> {
            if (!productLinesId.contains(productLine.getId())) {
                productLineRepository.deleteById(productLine.getId());
            }
        });


        return productLines;
    }


}
