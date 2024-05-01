package io.teamchallenge.woodCrafts.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

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
    private String password;
}
