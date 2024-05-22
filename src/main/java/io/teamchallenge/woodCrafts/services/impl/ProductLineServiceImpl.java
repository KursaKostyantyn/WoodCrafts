package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.mapper.ProductLineMapper;
import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.ProductLine;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;
import io.teamchallenge.woodCrafts.repository.OrderRepository;
import io.teamchallenge.woodCrafts.repository.ProductLineRepository;
import io.teamchallenge.woodCrafts.repository.ProductRepository;
import io.teamchallenge.woodCrafts.services.api.ProductLineService;
import io.teamchallenge.woodCrafts.utils.IdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductLineServiceImpl implements ProductLineService {
    private final ProductRepository productRepository;
    private final ProductLineRepository productLineRepository;
    private final OrderRepository orderRepository;
    private final ProductLineMapper productLineMapper;

    @Override
    public List<ProductLine> getListOfProductLinesFromListOfProductLinesDto(List<ProductLineDto> productLineDtos) {
        return productLineDtos.stream().map(productLineDto -> {
            ProductLine productLine = productLineMapper.productLineDtoToProductLine(productLineDto);
            String formattedId = productLineDto.getProduct().getId();
            Long id = IdConverter.convertStringToId(formattedId);
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Product with '%s' not found", id)));
            productLine.setProduct(product);
            return productLine;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ProductLine> updateProductLines(List<ProductLineDto> productLineDtos, Order order) {
        List<ProductLine> productLines = new ArrayList<>();
        productLineDtos.forEach(productLineDto -> {
            if (productLineDto.getId() != null) {
                Long productLineId = productLineDto.getId();
                ProductLine productLine = productLineRepository.findById(productLineId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Product line with id'%s' not found", productLineId)));
                String formattedId = productLineDto.getProduct().getId();
                Long productId = IdConverter.convertStringToId(formattedId);
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", productId)));
                productLineMapper.updateProductLineFromProductLineDto(productLine, productLineDto);
                productLine.setProduct(product);
                productLine.setOrder(order);
                productLines.add(productLine);
            } else {
                String formattedId = productLineDto.getProduct().getId();
                Long productId = IdConverter.convertStringToId(formattedId);
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Product with '%s' not found", productId)));
                ProductLine productLine = productLineMapper.productLineDtoToProductLine(productLineDto);
                productLine.setProduct(product);
                productLine.setOrder(order);
                productLines.add(productLine);
            }
        });
        List<Long> productLinesId = productLines.stream().map(ProductLine::getId).collect(Collectors.toList());
        Order orderById = orderRepository.findById(order.getId()).orElse(new Order());
        List<ProductLine> oldProductLines = productLineRepository.findProductLinesByOrder(orderById);
        order.setProductLines(new ArrayList<>());
        oldProductLines.forEach(productLine -> {
            if (!productLinesId.contains(productLine.getId())) {
                productLineRepository.deleteById(productLine.getId());
            }
        });

        return productLines;
    }


}
