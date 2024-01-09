package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.services.api.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<OrderDto> getOrderById(@RequestParam Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/updateOrderById")
    public ResponseEntity<Void> updateOrderById(@Valid @RequestParam Long id, @RequestBody OrderDto orderDto) {
        return orderService.updateOrderById(id, orderDto);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<PageWrapperDto<OrderDto>> getAllOrders(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "7") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted
    ) {
        return orderService.getAllOrders(PageRequest.of(page, size, direction, sortBy), isDeleted);
    }

    @GetMapping("/getFilteredOrders")
    public ResponseEntity<PageWrapperDto<OrderDto>> getFilteredOrders(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "7") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted,
            @RequestParam(required = false)LocalDateTime fromCreationDate,
            @RequestParam(required = false)LocalDateTime toCreationDate,
            @RequestParam(required = false, defaultValue = "0") double minTotal,
            @RequestParam(required = false, defaultValue = "100000000") double maxTotal,
            @RequestParam(required = false)Status status
            ){
        return orderService.getFilteredOrders();
    }

}
