package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import io.teamchallenge.woodCrafts.services.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(categoryDto);
    }

    @GetMapping("/findById")
    public ResponseEntity<CategoryDto> findCategoryById(@RequestParam Long id) {
        return categoryService.findCategoryById(id);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteCategoryById(@RequestParam Long id) {
        return categoryService.deleteCategoryById(id);
    }

    @PutMapping("/updateById")
    public ResponseEntity<Void> updateCategoryById(@RequestBody CategoryDto categoryDto, @RequestParam Long id) {
        return categoryService.updateCategoryById(categoryDto, id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
