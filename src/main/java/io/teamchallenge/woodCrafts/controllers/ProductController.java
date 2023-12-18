package io.teamchallenge.woodCrafts.controllers;


import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("/findProductById")
    public ResponseEntity<ProductDto> findProductById(@RequestParam Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/deleteProductById")
    public ResponseEntity<Void> deleteProductById(Long id) {
        return productService.deleteProductById(id);
    }

    @PutMapping("/updateProductById")
    public ResponseEntity<Void> updateProductById(ProductDto productDto, Long id) {
        return productService.updateProductById(productDto, id);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<PageWrapperDto<ProductDto>> findAllProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "7") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted
    ) {
        return productService.findAllProducts(PageRequest.of(page, size, direction, sortBy), isDeleted);
    }

    @PostMapping("/importListOfProducts")
    public ResponseEntity<Void> importListOfProducts(@RequestParam MultipartFile productsFile) {
        return productService.importListOfProducts(productsFile);
    }

    @GetMapping("/getFilteredProducts")
    public ResponseEntity<PageWrapperDto<ProductDto>> getFilteredProducts(
            @RequestParam(required = false, defaultValue = "0") List<Long> categoryIds,
            @RequestParam(required = false, defaultValue = "0") List<Long> colorIds,
            @RequestParam(required = false, defaultValue = "0") List<Long> materialIds,
            @RequestParam(required = false, defaultValue = "0") int minPrice,
            @RequestParam(required = false, defaultValue = "1000000000") int maxPrice,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "7") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted
    ) {
        return productService.getFilteredProducts(
                PageRequest.of(page, size, direction, sortBy),
                categoryIds,
                colorIds,
                materialIds,
                minPrice,
                maxPrice, isDeleted);
    }

    @GetMapping("/getProductByName")
    public ResponseEntity<PageWrapperDto<ProductDto>> findAllByName
            (
                    @RequestParam(required = false, defaultValue = "0") int page,
                    @RequestParam(required = false, defaultValue = "7") int size,
                    @RequestParam(required = false, defaultValue = "id") String sortBy,
                    @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction,
                    @RequestParam String name,
                    @RequestParam(required = false, defaultValue = "false") boolean isDeleted
            ) {
        return productService.findAllByName(PageRequest.of(page, size, direction, sortBy), name, isDeleted);
    }

}
