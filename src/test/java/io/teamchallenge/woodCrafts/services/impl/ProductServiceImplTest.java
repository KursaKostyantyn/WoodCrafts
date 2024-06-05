package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.mapper.ProductMapper;
import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.dto.IdsDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.repository.ColorRepository;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import io.teamchallenge.woodCrafts.repository.ProductRepository;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ColorRepository colorRepository;
    private MaterialRepository materialRepository;
    private ProductMapper productMapper;

    private ProductService productService;

    @BeforeEach
    void beforeEach() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        colorRepository = mock(ColorRepository.class);
        materialRepository = mock(MaterialRepository.class);
        productMapper = mock(ProductMapper.class);
        productService = new ProductServiceImpl(
                productRepository,
                categoryRepository,
                colorRepository,
                materialRepository,
                productMapper
        );
    }


    @Test
    void save() {
        //given
        ProductDto productDto = createProductDto(1L);
        Category category = createCategory(1L);
        Color color = createColor(1L);
        Material material = createMaterial(1L);
        Product product = convertProductDtoToProduct(productDto, color, category, material);
        when(productMapper.productDtoToProduct(productDto)).thenReturn(product);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(colorRepository.findById(color.getId())).thenReturn(Optional.of(color));
        when(materialRepository.findById(material.getId())).thenReturn(Optional.of(material));
        when(productMapper.productToProductDto(product)).thenReturn(productDto);

        //when
        productService.save(productDto);

        //then
        verify(productMapper, times(1)).productDtoToProduct(productDto);
        verify(categoryRepository, times(1)).findById(category.getId());
        verify(colorRepository, times(1)).findById(color.getId());
        verify(materialRepository, times(1)).findById(material.getId());
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).productToProductDto(product);
    }

    @Test
    void getProductsById() {
        //given
        Long productId1 = 1L;
        Category category1 = createCategory(1L);
        Material material1 = createMaterial(1L);
        Color color1 = createColor(1L);
        LocalDateTime creationDate1 = LocalDateTime.now().minusDays(1);
        LocalDateTime updateDate1 = LocalDateTime.now();
        Product product1 = createProduct(
                productId1,
                category1,
                material1,
                color1,
                creationDate1,
                updateDate1);
        Long productId2 = 2L;
        Category category2 = createCategory(2L);
        Material material2 = createMaterial(2L);
        Color color2 = createColor(2L);
        LocalDateTime creationDate2 = LocalDateTime.now().minusDays(1);
        LocalDateTime updateDate2 = LocalDateTime.now();
        Product product2 = createProduct(
                productId2,
                category2,
                material2,
                color2,
                creationDate2,
                updateDate2);
        List<Long> ids = Arrays.asList(1L, 2L);
        String sortBy = "id";
        Sort.Direction direction = Sort.Direction.ASC;
        ProductDto productDto1 = convertProductToProductDto(product1);
        ProductDto productDto2 = convertProductToProductDto(product2);
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productRepository.findById(product2.getId())).thenReturn(Optional.of(product2));
        when(productMapper.productToProductDto(product1)).thenReturn(productDto1);
        when(productMapper.productToProductDto(product2)).thenReturn(productDto2);

        //when
        List<ProductDto> actualResult = productService.getProductsById(ids, sortBy, direction);

        //then
        assertEquals(2, actualResult.size());
        assertEquals(productDto1, actualResult.get(0));
        assertEquals(productDto2, actualResult.get(1));
        verify(productRepository, times(1)).findById(product1.getId());
        verify(productRepository, times(1)).findById(product2.getId());
        verify(productMapper, times(1)).productToProductDto(product1);
        verify(productMapper, times(1)).productToProductDto(product2);
    }

    @Test
    void getProductsById_whenProductNotFound_shouldReturnException() {
        //given
        Long productId = 1L;
        Category category = createCategory(1L);
        Material material = createMaterial(1L);
        Color color = createColor(1L);
        LocalDateTime creationDate = LocalDateTime.now().minusDays(1);
        LocalDateTime updateDate = LocalDateTime.now();
        Product product1 = createProduct(
                productId,
                category,
                material,
                color,
                creationDate,
                updateDate);
        List<Long> ids = Collections.singletonList(1L);
        String sortBy = "id";
        Sort.Direction direction = Sort.Direction.ASC;
        String expectedMessage = String.format("Product with id='%s' not found", product1.getId());
        when(productRepository.findById(product1.getId())).thenReturn(Optional.empty());

        //when
        EntityNotFoundException actualResult = assertThrows(EntityNotFoundException.class, () -> productService.getProductsById(ids, sortBy, direction));

        //then
        assertNotNull(actualResult);
        assertEquals(expectedMessage, actualResult.getMessage());
        verify(productRepository, times(1)).findById(product1.getId());
        verify(productMapper, never()).productToProductDto(product1);
    }

    @Test
    void getProductByIdSuccessful() {
        //given
        Long productId = 1L;
        Category category = createCategory(1L);
        Material material = createMaterial(1L);
        Color color = createColor(1L);
        LocalDateTime creationDate = LocalDateTime.now().minusDays(1);
        LocalDateTime updateDate = LocalDateTime.now();
        Product product = createProduct(
                productId,
                category,
                material,
                color,
                creationDate,
                updateDate);
        ProductDto productDto = convertProductToProductDto(product);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productMapper.productToProductDto(product)).thenReturn(productDto);

        //when
        ProductDto actualResult = productService.getProductById(product.getId());

        //then
        assertNotNull(actualResult);
        verify(productRepository, times(1)).findById(product.getId());
        verify(productMapper, times(1)).productToProductDto(product);
    }

    @Test
    void getProductById() {
        //given
        Long productId = 1L;
        Category category = createCategory(1L);
        Material material = createMaterial(1L);
        Color color = createColor(1L);
        LocalDateTime creationDate = LocalDateTime.now().minusDays(1);
        LocalDateTime updateDate = LocalDateTime.now();
        Product product = createProduct(
                productId,
                category,
                material,
                color,
                creationDate,
                updateDate);
        String expectedMessage = String.format("Product with id='%s' not found", product.getId());
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        //when
        EntityNotFoundException actualResult = assertThrows(EntityNotFoundException.class, () -> productService.getProductById(product.getId()));

        //then
        assertNotNull(actualResult);
        assertEquals(expectedMessage, actualResult.getMessage());
        verify(productRepository, times(1)).findById(product.getId());
        verify(productMapper, never()).productToProductDto(any());
    }

    @Test
    void updateProductById() {
        //given
        Long productId = 1L;
        Category category = createCategory(1L);
        Material material = createMaterial(1L);
        Color color = createColor(1L);
        LocalDateTime creationDate = LocalDateTime.now().minusDays(1);
        LocalDateTime updateDate = LocalDateTime.now();
        Product product = createProduct(
                productId,
                category,
                material,
                color,
                creationDate,
                updateDate);
        Long updatedCategoryId = 2L;
        Category updatedCategory = createCategory(updatedCategoryId);
        Product updatedProduct = createProduct(
                productId,
                updatedCategory,
                material,
                color,
                creationDate,
                updateDate
        );

        ProductDto productDto = convertProductToProductDto(updatedProduct);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doAnswer(invocation -> {
            product.setCategory(updatedCategory);
            return null;
        }).when(productMapper).updateProductFromProductDto(product, productDto);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDto);

        //when
        ProductDto actualResult = productService.updateProductById(productDto, productId);

        //then
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();

        assertNotNull(actualResult);
        assertEquals(updatedCategoryId, actualResult.getCategoryId());
        assertEquals(updatedCategoryId, savedProduct.getCategory().getId());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).updateProductFromProductDto(product, productDto);
        verify(productMapper, times(1)).productToProductDto(product);
    }

    @Test
    void updateProductById_whenProductNotFound_shouldThrowExpection() {
        //given
        Long productId = 1L;
        ProductDto productDto = createProductDto(productId);
        String expectedMessage = String.format("Product with id='%s' not found", productId);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        //when
        EntityNotFoundException actualResult = assertThrows(EntityNotFoundException.class, () -> productService.updateProductById(productDto, productId));

        //then
        assertNotNull(actualResult);
        assertEquals(expectedMessage, actualResult.getMessage());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, never()).productToProductDto(any());
    }


    @Test
    void getProducts() {
    }

    @Test
    void findAllProductsByName() {
    }

    @Test
    void deleteProductList_success() {
        //given
        Long firstId = 1L;
        Long secondId = 2L;
        List<IdsDto> productIds = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        Product firstProduct = new Product();
        firstProduct.setId(firstId);
        firstProduct.setDeleted(false);
        Product secondProduct = new Product();
        secondProduct.setId(secondId);
        secondProduct.setDeleted(false);
        productIds.add(new IdsDto(firstId));
        productIds.add(new IdsDto(secondId));
        products.add(firstProduct);
        products.add(secondProduct);
        when(productRepository.findById(firstId)).thenReturn(Optional.of(firstProduct));
        when(productRepository.findById(secondId)).thenReturn(Optional.of(secondProduct));

        //when
        productService.deleteProductList(productIds);

        //then
        verify(productRepository,times(1)).findById(firstId);
        verify(productRepository, times(1)).findById(secondId);
        verify(productRepository, times(1)).saveAll(products);
        assertEquals(true, products.get(0).getDeleted());
        assertEquals(true, products.get(1).getDeleted());
    }

    @Test
    void deleteProductList_whenNotFound_throwsException() {
        //given
        Long firstId = 1L;
        String expectedMessage = String.format("Product with id='%s' not found", firstId);
        List<IdsDto> productIds = new ArrayList<>();
        Product firstProduct = new Product();
        firstProduct.setId(firstId);
        firstProduct.setDeleted(false);
        productIds.add(new IdsDto(firstId));
        when(productRepository.findById(firstId)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> productService.deleteProductList(productIds));

        //then
        assertNotNull(entityNotFoundException);
        assertNotNull(expectedMessage, entityNotFoundException.getMessage());
    }

    public static ProductDto createProductDto(Long productId) {
        ProductDto productDto = new ProductDto();
        productDto.setId(String.valueOf(productId));
        productDto.setPrice(100.0);
        productDto.setName("Test Product");
        productDto.setDescription("This is a test product description.");
        List<String> photos = new ArrayList<>();
        photos.add("testPhoto1.jpg");
        photos.add("testPhoto2.jpg");
        productDto.setPhotos(photos);
        productDto.setColorId(1L); // Assuming colorId 1 for testing
        productDto.setWeight(10.5);
        productDto.setHeight(20.0);
        productDto.setLength(30.0);
        productDto.setWidth(15.0);
        productDto.setCategoryId(1L); // Assuming categoryId 1 for testing
        productDto.setQuantity(50);
        productDto.setWarranty(12);
        productDto.setMaterialId(1L); // Assuming materialId 1 for testing
        productDto.setDeleted(false);
        productDto.setCreationDate(LocalDateTime.now());
        productDto.setUpdateDate(LocalDateTime.now());
        return productDto;
    }

    private Product createProduct(
            Long productId,
            Category category,
            Material material,
            Color color,
            LocalDateTime creationDate,
            LocalDateTime updateDate) {
        Product product = new Product();
        product.setId(productId);
        product.setPrice(100.0);
        product.setName("Test Product");
        product.setDescription("This is a test product description.");
        List<String> photos = new ArrayList<>();
        photos.add("testPhoto1.jpg");
        photos.add("testPhoto2.jpg");
        product.setPhotos(photos);
        product.setColor(color);
        product.setWeight(10.5);
        product.setHeight(20.0);
        product.setLength(30.0);
        product.setWidth(15.0);
        product.setCategory(category);
        product.setQuantity(50);
        product.setWarranty(12);
        product.setMaterial(material);
        product.setDeleted(false);
        product.setCreationDate(creationDate);
        product.setUpdateDate(updateDate);
        return product;
    }

    private Product convertProductDtoToProduct(
            ProductDto productDto,
            Color color,
            Category category,
            Material material
    ) {
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPhotos(productDto.getPhotos());
        product.setWeight(productDto.getWeight());
        product.setHeight(productDto.getHeight());
        product.setLength(productDto.getLength());
        product.setWidth(productDto.getWidth());
        product.setQuantity(productDto.getQuantity());
        product.setWarranty(productDto.getWarranty());
        product.setDeleted(productDto.getDeleted());
        product.setCreationDate(productDto.getCreationDate());
        product.setUpdateDate(productDto.getUpdateDate());
        product.setColor(color);
        product.setCategory(category);
        product.setMaterial(material);

        return product;
    }

    private ProductDto convertProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(String.valueOf(product.getId()));
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPhotos(product.getPhotos());
        productDto.setWeight(product.getWeight());
        productDto.setHeight(product.getHeight());
        productDto.setLength(product.getLength());
        productDto.setWidth(product.getWidth());
        productDto.setQuantity(product.getQuantity());
        productDto.setWarranty(product.getWarranty());
        productDto.setDeleted(product.getDeleted());
        productDto.setCreationDate(product.getCreationDate());
        productDto.setUpdateDate(product.getUpdateDate());
        productDto.setColorId(product.getColor().getId());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setMaterialId(product.getMaterial().getId());

        return productDto;
    }

    private Color createColor(Long colorId) {
        return Color.builder()
                .id(colorId)
                .name("test color")
                .deleted(false)
                .build();
    }

    private Material createMaterial(Long materialId) {
        return Material.builder()
                .id(materialId)
                .name("test material")
                .deleted(false)
                .build();
    }

    private Category createCategory(Long categoryId) {
        return Category.builder()
                .id(categoryId)
                .name("test category")
                .deleted(false)
                .build();
    }

}