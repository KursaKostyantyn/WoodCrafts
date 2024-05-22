package io.teamchallenge.woodCrafts.services.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
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
import io.teamchallenge.woodCrafts.utils.OrderSpecificationUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductLineService productLineService;
    private final OrderMapper orderMapper;
    private final ProductLineMapper productLineMapper;

    @Transactional
    @Override
    public ResponseEntity<Void> save(OrderDto orderDto) {
        Order order = orderMapper.orderDtoToOrder(orderDto);
        List<ProductLine> productLines = productLineService.getListOfProductLinesFromListOfProductLinesDto(orderDto.getProductLines());

        productLines.forEach(productLine -> productLine.setOrder(order));
        order.setProductLines(productLines);
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    @Override
    public ResponseEntity<OrderDto> getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Order with id='%s' not found", id)));
        OrderDto orderDto = orderMapper.orderToOrderDto(order);
        List<ProductLineDto> productLineDtos = order.getProductLines().stream()
                .map(productLineMapper::productLineToProductLineDto)
                .collect(Collectors.toList());
        orderDto.setProductLines(productLineDtos);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> updateOrderById(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id='%s' not found", id)));
//        List<ProductLine> productLines = productLineService.updateProductLines(orderDto.getProductLines(), order);
        order.setAddress(orderDto.getAddress());
        order.setStatus(Status.getStatusByRepresentationStatus(orderDto.getStatus()));
        order.setDeleted(orderDto.getDeleted());
//        order.setProductLines(productLines);

        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteOrders(List<ObjectNode> orderIds) {
        List<Order> orders = new ArrayList<>();
        for (ObjectNode node : orderIds) {
            if (node.has("id")) {
                Long id = node.get("id").asLong();
                Order order = orderRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id='%s' not found", id)));
                order.setDeleted(true);
                orders.add(order);
            }
        }
        orderRepository.saveAll(orders);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    @Override
    public ResponseEntity<PageWrapperDto<OrderDto>> getOrders(
            PageRequest pageRequest,
            boolean isDeleted,
            LocalDate fromCreationDate,
            LocalDate toCreationDate,
            double minTotal,
            double maxTotal,
            String statusName
    ) {
        Status status = null;
        if (statusName != null) {
            status = Status.getStatusByRepresentationStatus(statusName);
        }
        LocalDateTime fromDate = fromCreationDate.atStartOfDay();
        LocalDateTime toDate = LocalDateTime.of(toCreationDate, LocalTime.MAX);

        Specification<Order> specification = OrderSpecificationUtils
                .filterOrders(isDeleted, fromDate, toDate, minTotal, maxTotal, status);
        Page<Order> filteredOrderPage = orderRepository.findAll(specification, pageRequest);
        PageWrapperDto<OrderDto> pageWrapperDto = new PageWrapperDto<>();
        List<OrderDto> collect = filteredOrderPage.getContent().stream()
                .map(orderMapper::orderToOrderDto).collect(Collectors.toList());
        pageWrapperDto.setData(collect);
        pageWrapperDto.setTotalPages(filteredOrderPage.getTotalPages());
        pageWrapperDto.setTotalItems(filteredOrderPage.getTotalElements());

        return ResponseEntity.status(HttpStatus.OK).body(pageWrapperDto);
    }
}
