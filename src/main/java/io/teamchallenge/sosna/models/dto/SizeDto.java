package io.teamchallenge.sosna.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class SizeDto {

    private Long id;
    private double height;
    private double wight;
    private double length;
}
