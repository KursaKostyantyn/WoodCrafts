package io.teamchallenge.sosna.controllers;

import io.teamchallenge.sosna.models.dto.CategoryDto;
import io.teamchallenge.sosna.services.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping ("/save")
    public ResponseEntity<Void> saveCategory (@RequestBody CategoryDto categoryDto){
        return categoryService.saveCategory(categoryDto);
    }

    @GetMapping("/findById")
    public ResponseEntity<CategoryDto> findCategoryById(@RequestParam Long id){
        return categoryService.getCategoryById(id);
    }
}
