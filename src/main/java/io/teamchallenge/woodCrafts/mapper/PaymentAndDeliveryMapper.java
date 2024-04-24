package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.PaymentAndDelivery;
import io.teamchallenge.woodCrafts.models.dto.PaymentAndDeliveryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentAndDeliveryMapper  {
    PaymentAndDelivery toEntity(PaymentAndDeliveryDto paymentAndDeliveryDto);
    PaymentAndDeliveryDto toModel(PaymentAndDelivery paymentAndDelivery);
}
