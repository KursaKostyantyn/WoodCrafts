package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import io.teamchallenge.woodCrafts.services.api.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@Validated
@AllArgsConstructor
@RequestMapping()
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable @Min(1) Long id) {
        return categoryService.findCategoryById(id);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable @Min(1) Long id) {
        return categoryService.deleteCategoryById(id);
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<Void> updateCategoryById(@Valid @RequestBody CategoryDto categoryDto,
                                                   @PathVariable @Min(1) Long id) {
        return categoryService.updateCategoryById(categoryDto, id);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted
    ) {
        return categoryService.getAllCategories(isDeleted);
    }
}
