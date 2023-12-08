package io.teamchallenge.woodCrafts.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {

    private Long id;
    private double price;
    private String name;
    private String description;
    private List<String> photos;
    private Long colorId;
    private double weight;
    private double height;
    private double length;
    private double width;
    private Long categoryId;
    private int quantity;
    private int warranty;
    private Long materialId;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
