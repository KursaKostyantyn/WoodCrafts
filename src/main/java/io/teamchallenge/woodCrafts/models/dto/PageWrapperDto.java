package io.teamchallenge.woodCrafts.models.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PageWrapperDto<T> {
    List<T> data;
    int totalPages;
    long totalItems;
}
