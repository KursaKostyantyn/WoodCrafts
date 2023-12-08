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
import java.util.Random;

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
    public void autoFilling(int numberOfProducts) {
        saveCategory();
        saveColoros();
        saveMaterials();
        saveProducts(numberOfProducts);
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

    private void saveProducts(int numberOfProducts) {
        for (long i = 1; i < 6; i++) {
          for (int j=0; j<numberOfProducts;j++){
              ProductDto productDto=getNewProduct(i);
              productService.saveProduct(productDto);
          }
        }
    }

    private ProductDto getNewProduct(long i) {
        ProductDto productDto = new ProductDto();
        productDto.setCategoryId(i);
        productDto.setColorId(i);
        productDto.setMaterialId(i);
        productDto.setName("product " + generateRandomString(5));
        productDto.setDescription(generateRandomString(100));
        productDto.setHeight(Math.round(Math.random()*100)/10.0);
        productDto.setLength(Math.round(Math.random()*100)/10.0);
        productDto.setWidth(Math.round(Math.random()*100)/10.0);
        productDto.setWeight(Math.round(Math.random()*100)/10.0);
        productDto.setPhotos(Collections.singletonList("https://content1.rozetka.com.ua/goods/images/big/273094280.jpg"));
        productDto.setPrice(Math.round(Math.random()*100000)/100.0);
        productDto.setQuantity((int) (Math.random()*100));
        productDto.setWarranty(12);
        return productDto;
    }

    private String generateRandomString (int length){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
        StringBuilder stringBuilder =new StringBuilder();
        Random random =new Random();
        for (int i=0; i<length;i++){
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

}
