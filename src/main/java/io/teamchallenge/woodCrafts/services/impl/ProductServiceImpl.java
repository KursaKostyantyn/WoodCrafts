package io.teamchallenge.woodCrafts.services.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.mapper.ProductMapper;
import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.dto.PageWrapperDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.repository.ColorRepository;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import io.teamchallenge.woodCrafts.repository.ProductRepository;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import io.teamchallenge.woodCrafts.utils.ProductSpecificationsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", productDto.getCategoryId())));
        product.setCategory(category);
        Color color = colorRepository.findById(productDto.getColorId()).orElseThrow(() -> new EntityNotFoundException(String.format("Color with id='%s' not found", productDto.getColorId())));
        product.setColor(color);
        Material material = materialRepository.findById(productDto.getMaterialId()).orElseThrow(() -> new EntityNotFoundException(String.format("Material with id='%s' not found", productDto.getMaterialId())));
        product.setMaterial(material);
        productRepository.save(product);

        return productMapper.productToProductDto(product);
    }

    @Transactional
    @Override
    public List<ProductDto> getProductsById(List<Long> ids, String sortBy, Sort.Direction direction) {
        List<Product> products = new ArrayList<>();
        ids.forEach(id -> {
            Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", id)));
            products.add(product);
        });
        Comparator<ProductDto> comparator = createComparator(sortBy, direction);
        List<ProductDto> productDtos = products.stream().map(productMapper::productToProductDto).collect(Collectors.toList());
        if (comparator != null) {
            productDtos.sort(comparator);
        }
        return productDtos;
    }

    @Transactional
    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", id)));
        return productMapper.productToProductDto(product);
    }

    @Transactional
    @Override
    public ProductDto updateProductById(ProductDto updates, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", id)));
        productMapper.updateProductFromProductDto(product, updates);
        productRepository.save(product);
        return productMapper.productToProductDto(product);
    }

    @Transactional
    @Override
    public PageWrapperDto<ProductDto> getProducts
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
            ) {
        List<Category> categories = new ArrayList<>();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                categories.add(categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", categoryId))));
            }
        }
        List<Color> colors = new ArrayList<>();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long colorId : colorIds) {
                colors.add(colorRepository.findById(colorId).orElseThrow(() -> new EntityNotFoundException(String.format("Color with id='%s' not found", colorId))));
            }
        }
        List<Material> materials = new ArrayList<>();
        if (materialIds != null && !materialIds.isEmpty()) {
            for (Long materialId : materialIds) {
                materials.add(materialRepository.findById(materialId).orElseThrow(() -> new EntityNotFoundException(String.format("Material with id='%s' not found", materialId))));
            }
        }
        LocalDateTime fromDate = dateFrom.atStartOfDay();
        LocalDateTime toDate = LocalDateTime.of(dateTo, LocalTime.MAX);
        Specification<Product> specification = ProductSpecificationsUtils
                .filterProduct(categories, colors, materials, minPrice, maxPrice, isDeleted, inStock, notAvailable, name, fromDate, toDate);
        Page<Product> filteredProductsPage = productRepository.findAll(specification, pageRequest);
        PageWrapperDto<ProductDto> pageWrapperDto = new PageWrapperDto<>();
        List<ProductDto> collect = filteredProductsPage.getContent().stream().map(productMapper::productToProductDto).collect(Collectors.toList());
        pageWrapperDto.setData(collect);
        pageWrapperDto.setTotalPages(filteredProductsPage.getTotalPages());
        pageWrapperDto.setTotalItems(filteredProductsPage.getTotalElements());

        return pageWrapperDto;
    }

    @Override
    public ResponseEntity<PageWrapperDto<ProductDto>> findAllProductsByName(PageRequest pageRequest, String name,
                                                                            boolean isAvailable) {
        Page<Product> productsByName = productRepository.findAllByNameContainingIgnoreCaseAndDeleted(pageRequest, name, isAvailable);
        PageWrapperDto<ProductDto> pageWrapperDto = new PageWrapperDto<>();
        pageWrapperDto.setData(productsByName.getContent().stream().map(productMapper::productToProductDto).collect(Collectors.toList()));
        pageWrapperDto.setTotalPages(productsByName.getTotalPages());
        pageWrapperDto.setTotalItems(productsByName.getTotalElements());

        return ResponseEntity.ok(pageWrapperDto);
    }

    @Override
    public ResponseEntity<Void> deleteProductList(List<ObjectNode> productIds) {
        List<Product> products = new ArrayList<>();
        for (ObjectNode node : productIds) {
            if (node.has("id")) {
                Long id = node.get("id").asLong();
                Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", id)));
                product.setDeleted(true);
                products.add(product);
            }
        }
        productRepository.saveAll(products);

        return ResponseEntity.ok().build();
    }

    private Comparator<ProductDto> createComparator(String sortBy, Sort.Direction direction) {
        Comparator<ProductDto> comparator = Comparator.comparing(ProductDto::getId);
        if ("id".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getId, Comparator.nullsFirst(String::compareTo));
        } else if ("price".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getPrice);
        } else if ("name".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getName);
        } else if ("description".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getDescription);
        } else if ("color".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getColorId);
        } else if ("weight".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getWeight);
        } else if ("height".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getHeight);
        } else if ("length".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getLength);
        } else if ("width".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getWidth);
        } else if ("category".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getCategoryId);
        } else if ("quantity".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getQuantity);
        } else if ("warranty".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getWarranty);
        } else if ("material".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getMaterialId);
        } else if ("creationDate".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getCreationDate);
        } else if ("updateDate".equals(sortBy)) {
            comparator = Comparator.comparing(ProductDto::getUpdateDate);
        }
        if (direction.isDescending()) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
