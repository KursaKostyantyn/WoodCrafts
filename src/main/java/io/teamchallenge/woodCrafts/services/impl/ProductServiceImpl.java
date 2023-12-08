package io.teamchallenge.woodCrafts.services.impl;

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
import lombok.Cleanup;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;

    @Override
    public ResponseEntity<Void> saveProduct(ProductDto productDto) {
        Product product = ProductMapper.convertProductDtoToProduct(productDto);
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        product.setCategory(category);
        Color color = colorRepository.findById(productDto.getColorId()).orElse(null);
        if (color == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        product.setColor(color);
        Material material = materialRepository.findById(productDto.getMaterialId()).orElse(null);
        if (material == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        product.setMaterial(material);
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

    @Override
    public ResponseEntity<Void> deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productRepository.delete(product);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateProductById(ProductDto productDto, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        Color color = colorRepository.findById(productDto.getColorId()).orElse(null);
        Material material = materialRepository.findById(productDto.getMaterialId()).orElse(null);
        if (category == null | color == null | material == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ProductMapper.updateProductFromProductDto(productDto, product);
        product.setCategory(category);
        product.setColor(color);
        product.setMaterial(material);
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<PageWrapperDto<ProductDto>> findAllProducts(PageRequest pageRequest) {
        Page<Product> page = productRepository.findAll(pageRequest);
        List<ProductDto> products = page.getContent().stream().map(ProductMapper::convertProductToProductDto).collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(page.getTotalPages()));
        headers.add("X-Total-Items", String.valueOf(page.getTotalElements()));
        PageWrapperDto<ProductDto> pageWrapperDto = new PageWrapperDto<>();
        pageWrapperDto.setData(products);
        pageWrapperDto.setTotalPages(page.getTotalPages());
        pageWrapperDto.setTotalItems(page.getTotalElements());

        return new ResponseEntity<>(pageWrapperDto, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> importListOfProducts(@NonNull MultipartFile productsFile) {
        if (!productsFile.isEmpty()) {
            try (Workbook workbook = WorkbookFactory.create(productsFile.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    String name = row.getCell(0).getStringCellValue();
                    double price = row.getCell(1).getNumericCellValue();
                    String description = row.getCell(2).getStringCellValue();
                    Long colorId = (long) row.getCell(3).getNumericCellValue();
                    double weight = row.getCell(4).getNumericCellValue();
                    double height = row.getCell(5).getNumericCellValue();
                    double length = row.getCell(6).getNumericCellValue();
                    double width = row.getCell(7).getNumericCellValue();
                    Long categoryId = (long) row.getCell(8).getNumericCellValue();
                    int quantity = (int) row.getCell(9).getNumericCellValue();
                    int warranty = (int) row.getCell(10).getNumericCellValue();
                    Long materialId = (long) row.getCell(11).getNumericCellValue();
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

                    saveProduct(productDto);
                }


            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
