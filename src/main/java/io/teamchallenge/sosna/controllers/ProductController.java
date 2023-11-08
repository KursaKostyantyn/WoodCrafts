package io.teamchallenge.sosna.controllers;


import io.teamchallenge.sosna.models.dto.ProductDto;
import io.teamchallenge.sosna.services.api.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDto productDto) {

        return productService.saveProduct(productDto);
    }

    @GetMapping("/findById")
    public ResponseEntity<ProductDto> findProductById(@RequestParam Long id){
        return productService.getProductById(id);
    }
}
