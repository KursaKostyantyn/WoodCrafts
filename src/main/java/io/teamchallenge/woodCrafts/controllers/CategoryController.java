package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import io.teamchallenge.woodCrafts.services.api.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;


@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @GetMapping("/findCategoryById")
    public ResponseEntity<CategoryDto> findCategoryById(@RequestParam @Min(1) Long id) {
        return categoryService.findCategoryById(id);
    }

    @DeleteMapping("/deleteCategoryById")
    public ResponseEntity<Void> deleteCategoryById(@RequestParam @Min(1) Long id) {
        return categoryService.deleteCategoryById(id);
    }

    @PutMapping("/updateCategoryById")
    public ResponseEntity<Void> updateCategoryById(@RequestBody CategoryDto categoryDto, @RequestParam @Min(1) Long id) {
        return categoryService.updateCategoryById(categoryDto, id);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
