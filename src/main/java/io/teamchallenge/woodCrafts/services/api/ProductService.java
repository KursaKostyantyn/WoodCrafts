package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.IdsDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.models.dto.ProductFilterDto;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto productDto);

    List<ProductDto> getProductsById(List<Long> ids, String sortBy, Sort.Direction direction);

    ProductDto getProductById(Long id);

    ProductDto updateProductById(ProductDto updates, Long id);

    PageWrapperDto<ProductDto> getProducts(ProductFilterDto filterDto);

    ResponseEntity<PageWrapperDto<ProductDto>> findAllProductsByName(ProductFilterDto productFilterDto);

    void deleteProductList(List<IdsDto> productDtoList);
}
