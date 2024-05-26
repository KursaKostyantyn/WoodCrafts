package io.teamchallenge.woodCrafts.services.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    ResponseEntity<Void> save(OrderDto orderDto);

    ResponseEntity<OrderDto> getOrderById(Long id);

    OrderDto updateOrderById(Long id, OrderDto orderDto);

    ResponseEntity<PageWrapperDto<OrderDto>> getOrders(
            PageRequest pageRequest,
            boolean isDeleted,
            LocalDate fromCreationDate,
            LocalDate toCreationDate,
            double minTotal,
            double maxTotal,
            String statusName
    );

    //    ResponseEntity<Void> deleteOrders(List<OrderDto> orderIds);
    ResponseEntity<Void> deleteOrders(List<ObjectNode> orderIds);

}
