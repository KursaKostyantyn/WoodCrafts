package io.teamchallenge.woodCrafts.controllers;


import io.swagger.v3.oas.annotations.media.Schema;
import io.teamchallenge.woodCrafts.models.dto.IdsDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) List<Long> colorIds,
            @RequestParam(required = false) List<Long> materialIds,
            @RequestParam(required = false, defaultValue = "0") int minPrice,
            @RequestParam(required = false, defaultValue = "1000000000") int maxPrice,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "7") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted,
            @RequestParam(required = false, defaultValue = "true") boolean inStock,
            @RequestParam(required = false, defaultValue = "true") boolean notAvailable,
            @RequestParam(required = false) String name,
            @DateTimeFormat(pattern = "dd.MM.yyyy") @RequestParam(required = false, defaultValue = "01.01.2024") LocalDate dateFrom,
            @DateTimeFormat(pattern = "dd.MM.yyyy") @RequestParam(required = false, defaultValue = "01.01.3000") LocalDate dateTo
    ) {
        PageWrapperDto<ProductDto> products = productService.getProducts(
                PageRequest.of(page, size, direction, sortBy),
                categoryIds,
                colorIds,
                materialIds,
                minPrice,
                maxPrice,
                isDeleted,
                inStock,
                notAvailable,
                name,
                dateFrom,
                dateTo
        );
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
