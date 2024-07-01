package io.teamchallenge.woodCrafts.controllers;


import io.swagger.v3.oas.annotations.media.Schema;
import io.teamchallenge.woodCrafts.models.dto.FilterDto;
import io.teamchallenge.woodCrafts.models.dto.IdsDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto savedProductDto = productService.save(productDto);
        return ResponseEntity.ok(savedProductDto);
    }

    @GetMapping("/products/byIds")
    public ResponseEntity<List<ProductDto>> findProductById(
            @RequestParam @Min(1) List<Long> ids,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        List<ProductDto> products = productService.getProductsById(ids, sortBy, direction);
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProductById(@RequestBody ProductDto updates,
                                                        @PathVariable @Min(1) Long id) {
        ProductDto productDto = productService.updateProductById(updates, id);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/products")
    public ResponseEntity<PageWrapperDto<ProductDto>> getProducts(
            @ModelAttribute FilterDto filterDto
    ) {
        PageWrapperDto<ProductDto> products = productService.getProducts(filterDto);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/byName/{name}")
    public ResponseEntity<PageWrapperDto<ProductDto>> findAllProductsByName
            (
                    @RequestParam(required = false, defaultValue = "0") int page,
                    @RequestParam(required = false, defaultValue = "7") int size,
                    @RequestParam(required = false, defaultValue = "id") String sortBy,
                    @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction,
                    @PathVariable String name,
                    @RequestParam(required = false, defaultValue = "false") boolean isDeleted
            ) {
        return productService.findAllProductsByName(PageRequest.of(page, size, direction, sortBy), name, isDeleted);
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteProductList(@RequestBody @NotNull List<IdsDto> productDtoList) {
        productService.deleteProductList(productDtoList);
        return ResponseEntity.ok().build();
    }

    @Schema(hidden = true)
    @PostMapping("/addPhoto")
    public ResponseEntity<String> addPhoto(@RequestParam String comment, @RequestParam MultipartFile multipartFile) {
        String s = comment + ", " + multipartFile.getName() + ", " + multipartFile.getOriginalFilename() + ", " + multipartFile.getSize();

        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

}
