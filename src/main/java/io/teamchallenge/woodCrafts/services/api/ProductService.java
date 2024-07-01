package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.FilterDto;
import io.teamchallenge.woodCrafts.models.dto.IdsDto;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto productDto);

    List<ProductDto> getProductsById(List<Long> ids, String sortBy, Sort.Direction direction);

    ProductDto getProductById(Long id);

    ProductDto updateProductById(ProductDto updates, Long id);

    PageWrapperDto<ProductDto> getProducts(FilterDto filterDto);

    ResponseEntity<PageWrapperDto<ProductDto>> findAllProductsByName(PageRequest pageRequest, String name, boolean isAvailable);

    void deleteProductList(List<IdsDto> productDtoList);
}
