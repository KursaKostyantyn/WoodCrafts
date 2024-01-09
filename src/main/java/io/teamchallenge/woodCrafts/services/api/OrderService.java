package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<Void> createOrder(OrderDto orderDto);

    ResponseEntity<OrderDto> getOrderById(Long id);

    ResponseEntity<Void> updateOrderById(Long id, OrderDto orderDto);

    ResponseEntity<PageWrapperDto<OrderDto>> getAllOrders(PageRequest of, boolean isDeleted);

    ResponseEntity<PageWrapperDto<OrderDto>> getFilteredOrders();
}
