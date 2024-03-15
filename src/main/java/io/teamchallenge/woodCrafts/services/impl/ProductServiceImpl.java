package io.teamchallenge.woodCrafts.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
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
import io.teamchallenge.woodCrafts.utils.JacksonUtils;
import io.teamchallenge.woodCrafts.utils.ProductSpecificationsUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;

    @Override
    public ResponseEntity<Void> createProduct(ProductDto productDto) {
        Product product = ProductMapper.convertProductDtoToProduct(productDto);
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", productDto.getCategoryId())));
        product.setCategory(category);
        Color color = colorRepository.findById(productDto.getColorId()).orElseThrow(() -> new EntityNotFoundException(String.format("Color with id='%s' not found", productDto.getColorId())));
        product.setColor(color);
        Material material = materialRepository.findById(productDto.getMaterialId()).orElseThrow(() -> new EntityNotFoundException(String.format("Material with id='%s' not found", productDto.getMaterialId())));
        product.setMaterial(material);
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<ProductDto> getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", id)));

        return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.convertProductToProductDto(product));
    }

    @Deprecated
    @Override
    public ResponseEntity<Void> deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", id)));
        product.setDeleted(true);
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateProductById(ObjectNode updates, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Product with id='%s' not found", id)));
        Iterator<Map.Entry<String, JsonNode>> fields = updates.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            updateField(field, product);
        }
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).

                build();
    }

    @Override
    @Deprecated
    public ResponseEntity<Void> importListOfProducts(@NonNull MultipartFile productsFile) {
        if (!productsFile.isEmpty()) {
            try (Workbook workbook = WorkbookFactory.create(productsFile.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        String name = getStringCellValue(row, 0);
                        double price = getDoubleCellValue(row, 1);
                        String description = getStringCellValue(row, 2);
                        Long colorId = getLongCellValue(row, 3);
                        double weight = getDoubleCellValue(row, 4);
                        double height = getDoubleCellValue(row, 5);
                        double length = getDoubleCellValue(row, 6);
                        double width = getDoubleCellValue(row, 7);
                        Long categoryId = getLongCellValue(row, 8);
                        int quantity = (int) getDoubleCellValue(row, 9);
                        int warranty = (int) getDoubleCellValue(row, 10);
                        Long materialId = getLongCellValue(row, 11);
                        ProductDto productDto = new ProductDto();
                        productDto.setName(name);
                        productDto.setPrice(price);
                        productDto.setDescription(description);
                        productDto.setColorId(colorId);
                        productDto.setWeight(weight);
                        productDto.setHeight(height);
                        productDto.setLength(length);
                        productDto.setWidth(width);
                        productDto.setCategoryId(categoryId);
                        productDto.setQuantity(quantity);
                        productDto.setWarranty(warranty);
                        productDto.setMaterialId(materialId);

                        createProduct(productDto);
                    }
                }
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    private String getStringCellValue(Row row, int index) {
        return row.getCell(index) != null ? row.getCell(index).getStringCellValue() : null;
    }

    private double getDoubleCellValue(Row row, int index) {
        return row.getCell(index) != null ? row.getCell(index).getNumericCellValue() : 0.0;
    }

    private Long getLongCellValue(Row row, int index) {
        return row.getCell(index) != null ? (long) row.getCell(index).getNumericCellValue() : null;
    }

    @Override
    public ResponseEntity<PageWrapperDto<ProductDto>> getProducts
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
        List<ProductDto> collect = filteredProductsPage.getContent().stream().map(ProductMapper::convertProductToProductDto).collect(Collectors.toList());
        pageWrapperDto.setData(collect);
        pageWrapperDto.setTotalPages(filteredProductsPage.getTotalPages());
        pageWrapperDto.setTotalItems(filteredProductsPage.getTotalElements());

        return ResponseEntity.ok(pageWrapperDto);
    }

    @Override
    public ResponseEntity<PageWrapperDto<ProductDto>> findAllProductsByName(PageRequest pageRequest, String name,
                                                                            boolean isAvailable) {
        Page<Product> productsByName = productRepository.findAllByNameContainingIgnoreCaseAndDeleted(pageRequest, name, isAvailable);
        PageWrapperDto<ProductDto> pageWrapperDto = new PageWrapperDto<>();
        pageWrapperDto.setData(productsByName.getContent().stream().map(ProductMapper::convertProductToProductDto).collect(Collectors.toList()));
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

    private void updateField(Map.Entry<String, JsonNode> field, Product product) {
        String key = field.getKey();
        JsonNode value = field.getValue();
        switch (key) {
            case "categoryId":
                Long categoryId = value.asLong();
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id='%s' not found", categoryId)));
                product.setCategory(category);
            case "colorId":
                Long colorId = value.asLong();
                Color color = colorRepository.findById(colorId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Color with id='%s' not found", colorId)));
                product.setColor(color);
                break;
            case "materialId":
                Long materialId = value.asLong();
                Material material = materialRepository.findById(materialId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Material with id='%s' not found", materialId)));
                product.setMaterial(material);
                break;
            case "price":
                Double price = value.asDouble();
                product.setPrice(price);
                break;
            case "name":
                product.setName(value.asText());
                break;
            case "description":
                product.setDescription(value.asText());
                break;
            case "weight":
                Double weight = value.asDouble();
                product.setWeight(weight);
                break;
            case "height":
                Double height = value.asDouble();
                product.setHeight(height);
                break;
            case "length":
                Double length = value.asDouble();
                product.setLength(length);
                break;
            case "width":
                Double width = value.asDouble();
                product.setWidth(width);
                break;
            case "quantity":
                Integer quantity = value.intValue();
                product.setQuantity(quantity);
                break;
            case "warranty":
                Integer warranty = value.asInt();
                product.setWarranty(warranty);
                break;
            case "deleted":
                Boolean deleted = value.asBoolean();
                product.setDeleted(deleted);
                break;
            case "photos":
                List<String> list = JacksonUtils.toStringList(value);
                product.setPhotos(list);
                break;
        }
    }

}
