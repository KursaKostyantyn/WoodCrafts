package io.teamchallenge.woodCrafts.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {

    private Long id;
    private double price;
    private String name;
    private String description;
    private double rating;
    private String color;
    private String size;
    private LocalDateTime registrationDate;
    private LocalDateTime updateDate;
    private String material;
    private int guarantee;
    private double weight;
    private String photo;
    private Long categoryId;
}
