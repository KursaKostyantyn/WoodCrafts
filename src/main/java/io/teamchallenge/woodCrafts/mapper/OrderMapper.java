package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;
import io.teamchallenge.woodCrafts.models.dto.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {
    public static Order convertOrderDtoToOrder(OrderDto orderDto) {
        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderDto, Order.class);
        order.setStatus(Status.getStatusByRepresentationStatus(orderDto.getStatus()));
        return order;
    }

    public static OrderDto convertOrderToOrderDto(Order order) {
        ModelMapper modelMapper = new ModelMapper();
        List<ProductLineDto> productLineDtos = order.getProductLines().stream().map(ProductLineMapper::convertProductLineToProductLineDto).collect(Collectors.toList());
        UserDto userDtos = UserMapper.convertUserToUserDto(order.getUser());
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setProductLinesDto(productLineDtos);
        orderDto.setStatus(orderDto.getStatus());
        orderDto.setUserDto(userDtos);
        return orderDto;
    }
}
