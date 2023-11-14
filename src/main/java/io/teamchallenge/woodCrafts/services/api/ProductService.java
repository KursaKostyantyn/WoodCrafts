package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    ResponseEntity<Void> saveProduct(ProductDto productDto);

    ResponseEntity<ProductDto> getProductById(Long id);
}
