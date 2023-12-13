package io.teamchallenge.woodCrafts.utils;

import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.repository.ColorRepository;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import io.teamchallenge.woodCrafts.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class AutoFillingTablesUtil {
    private CategoryRepository categoryRepository;
    private ColorRepository colorRepository;
    private MaterialRepository materialRepository;
    private ProductRepository productRepository;

    @Bean
    public void autoFilling() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.size()==0){
            int numberOfProducts = 10;
            saveCategory();
            saveColoros();
            saveMaterials();
            saveProducts(numberOfProducts);
        }
    }

    private void saveCategory() {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("table");
        categoryNames.add("chairs");
        categoryNames.add("beds");
        categoryNames.add("armChairs");
        categoryNames.add("dresser");
        categoryNames.forEach(categoryName -> {
            Category category = new Category();
            category.setName(categoryName);
            category.setProducts(new ArrayList<>());
            categoryRepository.save(category);
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
            Color color = new Color();
            color.setName(colorName);
            color.setProducts(new ArrayList<>());
            colorRepository.save(color);
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
            Material material = new Material();
            material.setName(materialName);
            material.setProducts(new ArrayList<>());
            materialRepository.save(material);
        });
    }

    private void saveProducts(int numberOfProducts) {
        for (long i = 1; i < 6; i++) {
            for (int j = 0; j < numberOfProducts; j++) {
                Product product = getNewProduct(i);
                productRepository.save(product);
            }
        }
    }

    private Product getNewProduct(long i) {
        Product product = new Product();
        Category category = categoryRepository.findById(i).orElse(null);
        Color color = colorRepository.findById(i).orElse(null);
        Material material = materialRepository.findById(i).orElse(null);

        product.setCategory(category);
        product.setColor(color);
        product.setMaterial(material);
        product.setName("product " + generateRandomString(5));
        product.setDescription(generateRandomString(100));
        product.setHeight(Math.round(Math.random() * 100) / 10.0);
        product.setLength(Math.round(Math.random() * 100) / 10.0);
        product.setWight(Math.round(Math.random() * 100) / 10.0);
        product.setWeight(Math.round(Math.random() * 100) / 10.0);
        product.setPhotos(Collections.singletonList("https://content1.rozetka.com.ua/goods/images/big/273094280.jpg"));
        product.setPrice(Math.round(Math.random() * 100000) / 100.0);
        product.setQuantity((int) (Math.random() * 100));
        product.setWarranty(12);

        return product;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

}
