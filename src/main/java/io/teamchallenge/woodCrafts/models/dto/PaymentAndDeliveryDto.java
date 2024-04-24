package io.teamchallenge.woodCrafts.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentAndDeliveryDto {
    private Long id;
    private String paymentType;
    private String delivery;
    private String address;
}
