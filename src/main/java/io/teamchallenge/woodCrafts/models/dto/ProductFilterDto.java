package io.teamchallenge.woodCrafts.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDto {

    private List<Long> categoryIds;
    private List<Long> colorIds;
    private List<Long> materialIds;
    private int minPrice = 0;
    private int maxPrice = 1000000000;
    private int page = 0;
    private int size = 7;
    private String sortBy = "id";
    private Sort.Direction direction = Sort.Direction.ASC;
    private boolean isDeleted = false;
    private boolean inStock = true;
    private boolean notAvailable = true;
    private String name;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateFrom = LocalDate.of(2024, 1, 1);

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateTo = LocalDate.of(3000, 1, 1);
}
