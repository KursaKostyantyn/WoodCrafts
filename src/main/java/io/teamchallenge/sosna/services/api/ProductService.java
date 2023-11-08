package io.teamchallenge.sosna.services.api;

import io.teamchallenge.sosna.models.Product;
import io.teamchallenge.sosna.models.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {

    ResponseEntity<Void> saveProduct(ProductDto productDto);

    ResponseEntity<ProductDto> getProductById(Long id);
}
