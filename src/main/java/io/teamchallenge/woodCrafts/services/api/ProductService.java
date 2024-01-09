package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ResponseEntity<Void> createProduct(ProductDto productDto);

    ResponseEntity<ProductDto> getProductById(Long id);

    ResponseEntity<Void> deleteProductById(Long id);

    ResponseEntity<Void> updateProductById(ProductDto productDto, Long id);

    ResponseEntity<Void> importListOfProducts(MultipartFile productsFile);

    ResponseEntity<PageWrapperDto<ProductDto>> findAllProducts(PageRequest pageRequest,boolean isDeleted);

    ResponseEntity<PageWrapperDto<ProductDto>> getFilteredProducts
            (
                    PageRequest pageRequest,
                    List<Long> categoryIds,
                    List<Long> colorIds,
                    List<Long> materialIds,
                    int minPrice,
                    int maxPrice,
                    boolean isDeleted,
                    boolean inStock
            );

    ResponseEntity<PageWrapperDto<ProductDto>> findAllByName(PageRequest pageRequest, String name,boolean isAvailable);

    ResponseEntity<Void> deleteProductList(List<ProductDto> productDtoList);
}
