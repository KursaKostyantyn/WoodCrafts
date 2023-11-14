package io.teamchallenge.woodCrafts.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDto {

    private Long id;
    private String name;
}
