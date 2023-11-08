package io.teamchallenge.sosna.models.dto;

import io.teamchallenge.sosna.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {

    private LocalDateTime orderDate;
    private String address;
    private Status status;
}
