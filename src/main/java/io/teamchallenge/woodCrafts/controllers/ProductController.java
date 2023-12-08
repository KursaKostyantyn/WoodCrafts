package io.teamchallenge.woodCrafts.controllers;


import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.multipart.MultipartFile;

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
    public  ResponseEntity<PageWrapperDto<ProductDto>> findAllProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "7") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction
    ) {
        return productService.findAllProducts(PageRequest.of(page, size, direction, sortBy));
    }

    @PostMapping("/importListOfProducts")
    public ResponseEntity<Void> importListOfProducts(@RequestParam MultipartFile productsFile) {
        return productService.importListOfProducts(productsFile);
    }
}
