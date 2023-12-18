package io.teamchallenge.woodCrafts.models.dto;

import io.teamchallenge.woodCrafts.constants.Status;
import lombok.Getter;
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
    private boolean deleted;
}
