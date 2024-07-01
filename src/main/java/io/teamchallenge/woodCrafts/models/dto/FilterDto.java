package io.teamchallenge.woodCrafts.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
    private List<Long> categoryIds;
    private List<Long> colorIds;
    private List<Long> materialIds;
    @Builder.Default
    private Integer minPrice = 0;
    @Builder.Default
    private Integer maxPrice = 1000000000;
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 10;
    @Builder.Default
    private String sortBy = "id";
    @Builder.Default
    private Sort.Direction direction = Sort.Direction.ASC;
    @Builder.Default
    private Boolean isDeleted = false;
    @Builder.Default
    private Boolean inStock = true;
    @Builder.Default
    private Boolean notAvailable = true;
    private String name;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Builder.Default
    private LocalDate dateFrom = LocalDate.of(2024, 1,1);
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Builder.Default
    private LocalDate dateTo = LocalDate.of(2300, 1, 1);
}
