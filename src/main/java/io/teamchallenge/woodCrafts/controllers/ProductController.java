package io.teamchallenge.woodCrafts.controllers;


import io.teamchallenge.woodCrafts.models.dto.PageDateDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world woodCrafts in products");
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDto productDto) {
        return productService.saveProduct(productDto);
    }

    @GetMapping("/findById")
    public ResponseEntity<ProductDto> findProductById(@RequestParam Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteProductById(Long id) {
        return productService.deleteProductById(id);
    }

    @PutMapping("/updateById")
    public ResponseEntity<Void> updateProductById(ProductDto productDto, Long id) {
        return productService.updateProductById(productDto, id);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<PageDateDto<ProductDto>> findAllProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "2") int size
    ) {
        return productService.findAllProducts(PageRequest.of(page,size));
    }


}
