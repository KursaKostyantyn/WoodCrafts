package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);
    default Status mapStatusStringToEnum(String statusString) {
        return Status.getStatusByRepresentationStatus(statusString);
    }
}
