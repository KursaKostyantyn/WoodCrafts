package io.teamchallenge.woodCrafts.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDto {

    private Long id;
    @NotBlank(message = "Name cannot be blank")
    @NotNull
    private String name;
    private boolean deleted;
}
