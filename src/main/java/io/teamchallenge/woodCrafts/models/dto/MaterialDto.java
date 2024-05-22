package io.teamchallenge.woodCrafts.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class MaterialDto {

    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private boolean deleted;
}
