package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.PageDateDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<Void> saveProduct(ProductDto productDto);

    ResponseEntity<ProductDto> getProductById(Long id);

    ResponseEntity<Void> deleteProductById(Long id);

    ResponseEntity<Void> updateProductById (ProductDto productDto, Long id);

    ResponseEntity<PageDateDto<ProductDto>> findAllProducts(PageRequest pageRequest);
}
