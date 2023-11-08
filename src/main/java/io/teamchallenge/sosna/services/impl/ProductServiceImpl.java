package io.teamchallenge.sosna.services.impl;

import io.teamchallenge.sosna.mapper.ProductMapper;
import io.teamchallenge.sosna.models.Category;
import io.teamchallenge.sosna.models.Product;
import io.teamchallenge.sosna.models.dto.ProductDto;
import io.teamchallenge.sosna.repository.CategoryRepository;
import io.teamchallenge.sosna.repository.ProductRepository;
import io.teamchallenge.sosna.services.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Void> saveProduct(ProductDto productDto) {
        Product product = ProductMapper.convertProductDtoToProduct(productDto);
        Category category=categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        product.setCategory(category);
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<ProductDto> getProductById(Long id) {
        //todo create handler if product not found
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.convertProductToProductDto(product));
    }
}
