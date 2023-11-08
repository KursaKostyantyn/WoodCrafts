package io.teamchallenge.sosna.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime registrationDate;
    private String password;
}
