package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.PaymentAndDelivery;
import io.teamchallenge.woodCrafts.models.dto.PaymentAndDeliveryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentAndDeliveryMapper {

    PaymentAndDeliveryDto paymentAndDeliveryToPaymentAndDeliveryDto(PaymentAndDelivery paymentAndDelivery);

    PaymentAndDelivery paymentAndDeliveryDtoToPaymentAndDelivery(PaymentAndDeliveryDto paymentAndDeliveryDto);
}
