package io.teamchallenge.woodCrafts.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.services.api.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping()
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Void> save(@Valid @RequestBody OrderDto orderDto) {
        return orderService.save(orderDto);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<OrderDto> updateOrderById(@PathVariable Long id,
                                                    @Valid @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrderById(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/orders")
    public ResponseEntity<PageWrapperDto<OrderDto>> getOrders(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "7") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted,
            @DateTimeFormat(pattern = "dd.MM.yyyy") @RequestParam(required = false, defaultValue = "01.01.2024") LocalDate fromCreationDate,
            @DateTimeFormat(pattern = "dd.MM.yyyy") @RequestParam(required = false, defaultValue = "01.01.3000") LocalDate toCreationDate,
            @RequestParam(required = false, defaultValue = "0") double minTotal,
            @RequestParam(required = false, defaultValue = "100000000") double maxTotal,
            @RequestParam(required = false, defaultValue = "В обробці") String statusName
    ) {
        return orderService.getOrders(
                PageRequest.of(page, size, direction, sortBy),
                isDeleted,
                fromCreationDate,
                toCreationDate,
                minTotal,
                maxTotal,
                statusName);
    }

    @DeleteMapping("/orders")
    public ResponseEntity<Void> deleteOrders(@RequestBody List<ObjectNode> orderIds) {
        return orderService.deleteOrders(orderIds);
    }


}
