package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.mapper.PaymentAndDeliveryMapper;
import io.teamchallenge.woodCrafts.models.PaymentAndDelivery;
import io.teamchallenge.woodCrafts.models.dto.PaymentAndDeliveryDto;
import io.teamchallenge.woodCrafts.repository.PaymentAndDeliveryRepository;
import io.teamchallenge.woodCrafts.services.api.PaymentAndDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentAndDeliveryServiceImpl implements PaymentAndDeliveryService {
    private final PaymentAndDeliveryRepository paymentAndDeliveryRepository;
    private final PaymentAndDeliveryMapper paymentAndDeliveryMapper;

    @Transactional
    @Override
    public PaymentAndDeliveryDto save(PaymentAndDeliveryDto paymentAndDeliveryDto) {
        PaymentAndDelivery paymentAndDelivery = paymentAndDeliveryMapper.paymentAndDeliveryDtoToPaymentAndDelivery(paymentAndDeliveryDto);
        paymentAndDeliveryRepository.save(paymentAndDelivery);
        return paymentAndDeliveryMapper.paymentAndDeliveryToPaymentAndDeliveryDto(paymentAndDelivery);
    }
}
