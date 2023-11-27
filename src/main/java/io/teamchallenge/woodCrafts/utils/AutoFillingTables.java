package io.teamchallenge.woodCrafts.utils;

import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.repository.ColorRepository;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import io.teamchallenge.woodCrafts.repository.ProductRepository;
import io.teamchallenge.woodCrafts.services.api.CategoryService;
import io.teamchallenge.woodCrafts.services.api.ColorService;
import io.teamchallenge.woodCrafts.services.api.MaterialService;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AutoFillingTables {
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final MaterialService materialService;
    private final ProductService productService;


//    @Bean
    public void autoFilling() {
        saveCategory();
        saveColoros();
        saveMaterials();
        saveProducts();
    }

    private void saveCategory() {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("table");
        categoryNames.add("chairs");
        categoryNames.add("beds");
        categoryNames.add("armChairs");
        categoryNames.add("dresser");
        categoryNames.forEach(categoryName -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(categoryName);
            categoryService.saveCategory(categoryDto);
        });
    }

    private void saveColoros() {
        List<String> colorNames = new ArrayList<>();
        colorNames.add("red");
        colorNames.add("blue");
        colorNames.add("green");
        colorNames.add("yellow");
        colorNames.add("black");
        colorNames.forEach(colorName -> {
            ColorDto colorDto = new ColorDto();
            colorDto.setName(colorName);
            colorService.saveColor(colorDto);
        });
    }

    private void saveMaterials() {
        List<String> materialNames = new ArrayList<>();
        materialNames.add("wood");
        materialNames.add("stone");
        materialNames.add("iron");
        materialNames.add("plastic");
        materialNames.add("glass");
        materialNames.forEach(materialName -> {
            MaterialDto materialDto = new MaterialDto();
            materialDto.setName(materialName);
            materialService.saveMaterial(materialDto);
        });
    }

    private void saveProducts() {
        for (int i = 0; i < 5; i++) {
            Long k = (long) i;
            ProductDto productDto = getNewProduct(i,k,k,k);
            productService.saveProduct(productDto);
            productDto = getNewProduct(i + 1,k,k,k );
            productService.saveProduct(productDto);
            productDto = getNewProduct(i + 1,k,k,k );
            productService.saveProduct(productDto);
        }
    }

    private ProductDto getNewProduct(int i,Long categoryId, Long colorId, Long materialId) {
        ProductDto productDto = new ProductDto();
        productDto.setCategoryId(categoryId);
        productDto.setColorId(colorId);
        productDto.setMaterialId(materialId);
        productDto.setName("product " + i);
        productDto.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris venenatis, quam quis tristique rhoncus, sapien sapien tempor felis, et efficitur enim purus ut elit. Sed a lorem augue.");
        productDto.setHeight(17.5);
        productDto.setLength(19.5);
        productDto.setWight(18.5);
        productDto.setWeight(20.5);
        productDto.setPhotos(Collections.singletonList("https://content1.rozetka.com.ua/goods/images/big/273094280.jpg"));
        productDto.setPrice(245.99);
        productDto.setQuantity(5);
        productDto.setWarranty(12);
        return productDto;
    }

}
