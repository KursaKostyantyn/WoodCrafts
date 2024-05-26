package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",
        uses = {
                ProductLineMapper.class,
                UserMapper.class,
                PaymentAndDeliveryMapper.class
        })
public interface OrderMapper {
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatusEnumToString")
    OrderDto orderToOrderDto(Order order);

    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatusStringToEnum")
    Order orderDtoToOrder(OrderDto orderDto);

   @Named("mapStatusStringToEnum")
    default Status mapStatusStringToEnum(String statusString) {
        return Status.getStatusByRepresentationStatus(statusString);
    }

    @Named("mapStatusEnumToString")
    default String mapStatusEnumToString(Status status) {
        return status == null ? "" : status.getRepresentationStatus();
    }
}
