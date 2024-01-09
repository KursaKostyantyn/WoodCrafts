package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.mapper.OrderMapper;
import io.teamchallenge.woodCrafts.mapper.ProductLineMapper;
import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.ProductLine;
import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;
import io.teamchallenge.woodCrafts.repository.OrderRepository;
import io.teamchallenge.woodCrafts.services.api.OrderService;
import io.teamchallenge.woodCrafts.services.api.ProductLineService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductLineService productLineService;

    @Override
    public ResponseEntity<Void> createOrder(OrderDto orderDto) {
        Order order = OrderMapper.convertOrderDtoToOrder(orderDto);
        List<ProductLine> productLines = productLineService.getListOfProductLinesFromListOfProductLinesDto(orderDto.getProductLinesDto());

        productLines.forEach(productLine -> productLine.setOrder(order));
        order.setProductLines(productLines);
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<OrderDto> getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Order with id='%s' not found", id)));
        OrderDto orderDto = OrderMapper.convertOrderToOrderDto(order);
        List<ProductLineDto> productLineDtos = order.getProductLines().stream()
                .map(ProductLineMapper::convertProductLineToProductLineDto)
                .collect(Collectors.toList());
        orderDto.setProductLinesDto(productLineDtos);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @Override
    public ResponseEntity<Void> updateOrderById(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id='%s' not found", id)));
        List<ProductLine> productLines = productLineService.updateProductLines(orderDto.getProductLinesDto(), order);
        order.setAddress(orderDto.getAddress());
        order.setStatus(Status.valueOf(orderDto.getStatus()));
        order.setDeleted(orderDto.isDeleted());
        order.setProductLines(productLines);

        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<PageWrapperDto<OrderDto>> getAllOrders(PageRequest of, boolean isDeleted) {
        Page<Order> orderPage = orderRepository.findAllByDeleted(isDeleted, of);
        PageWrapperDto<OrderDto> pageWrapperDto = new PageWrapperDto<>();
        pageWrapperDto.setData(orderPage.getContent().stream().map(OrderMapper::convertOrderToOrderDto).collect(Collectors.toList()));
        pageWrapperDto.setTotalPages(orderPage.getTotalPages());
        pageWrapperDto.setTotalItems(orderPage.getTotalElements());

        return ResponseEntity.status(HttpStatus.OK).body(pageWrapperDto);
    }

    @Override
    public ResponseEntity<PageWrapperDto<OrderDto>> getFilteredOrders() {
        return null;
    }
}
