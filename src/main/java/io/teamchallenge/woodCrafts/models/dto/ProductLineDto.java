package io.teamchallenge.woodCrafts.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductLineDto {
    private Long id;
    private Long orderId;
    private ProductDto productDto;
    private int quantity;
    private double totalProductLineAmount;
}
