package io.teamchallenge.woodCrafts.services.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto productDto);

    List<ProductDto> getProductsById(List<Long> ids, String sortBy, Sort.Direction direction);

    ProductDto getProductById(Long id);

    ProductDto updateProductById(ProductDto updates, Long id);

    PageWrapperDto<ProductDto> getProducts
            (
                    PageRequest pageRequest,
                    List<Long> categoryIds,
                    List<Long> colorIds,
                    List<Long> materialIds,
                    int minPrice,
                    int maxPrice,
                    boolean isDeleted,
                    boolean inStock,
                    boolean notAvailable,
                    String name,
                    LocalDate dateFrom,
                    LocalDate dateTo
            );

    ResponseEntity<PageWrapperDto<ProductDto>> findAllProductsByName(PageRequest pageRequest, String name, boolean isAvailable);

    ResponseEntity<Void> deleteProductList(List<ObjectNode> productDtoList);
}
