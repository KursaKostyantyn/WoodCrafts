package io.teamchallenge.woodCrafts.utils;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.mapper.UserMapper;
import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.User;
import io.teamchallenge.woodCrafts.models.dto.CategoryDto;
import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import io.teamchallenge.woodCrafts.models.dto.OrderDto;
import io.teamchallenge.woodCrafts.models.dto.PaymentAndDeliveryDto;
import io.teamchallenge.woodCrafts.models.dto.ProductDto;
import io.teamchallenge.woodCrafts.models.dto.ProductLineDto;
import io.teamchallenge.woodCrafts.models.dto.UserDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.repository.ColorRepository;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import io.teamchallenge.woodCrafts.repository.ProductRepository;
import io.teamchallenge.woodCrafts.services.api.CategoryService;
import io.teamchallenge.woodCrafts.services.api.ColorService;
import io.teamchallenge.woodCrafts.services.api.MaterialService;
import io.teamchallenge.woodCrafts.services.api.OrderService;
import io.teamchallenge.woodCrafts.services.api.PaymentAndDeliveryService;
import io.teamchallenge.woodCrafts.services.api.ProductService;
import io.teamchallenge.woodCrafts.services.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAMES_CHAIRS;
import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAMES_SOFAS;
import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAME_BEDS;
import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAME_BEDSIDE_TABLES;
import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAME_COFFEE_TABLES;
import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAME_OTHER;
import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAME_STOOLS;
import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAME_TABLES;
import static io.teamchallenge.woodCrafts.constants.CategoryConstants.CATEGORY_NAME_WARDROBE;

@Service
@RequiredArgsConstructor
public class AutoFillingTablesUtil {

    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final MaterialService materialService;
    private final ProductService productService;
    private final OrderService orderService;
    private final PaymentAndDeliveryService paymentAndDeliveryService;
    private final UserMapper userMapper;
    private final Random random = new Random();
    private final Map<String, String> photos;

    @Bean
    public void autoFilling() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            int numberOfProducts = 10;
            fillPhotos();
            saveCategory();
            saveColoros();
            saveMaterials();
            saveUsers();
            saveProducts(numberOfProducts);
            saveOrders(200);

        }
    }

    private void fillPhotos() {
        photos.put(CATEGORY_NAME_TABLES, "https://content1.rozetka.com.ua/goods/images/big/273094280.jpg");
        photos.put(CATEGORY_NAME_BEDS, "https://content2.rozetka.com.ua/goods/images/big/461741929.jpg");
        photos.put(CATEGORY_NAME_BEDSIDE_TABLES, "https://content.rozetka.com.ua/goods/images/big/392119650.jpg");
        photos.put(CATEGORY_NAME_STOOLS, "https://content1.rozetka.com.ua/goods/images/big/399435070.jpg");
        photos.put(CATEGORY_NAME_WARDROBE, "https://content2.rozetka.com.ua/goods/images/big/367690037.jpg");
        photos.put(CATEGORY_NAME_COFFEE_TABLES, "https://content2.rozetka.com.ua/goods/images/big/414081372.jpg");
        photos.put(CATEGORY_NAMES_SOFAS, "https://content.rozetka.com.ua/goods/images/big/27779876.jpg");
        photos.put(CATEGORY_NAMES_CHAIRS, "https://content.rozetka.com.ua/goods/images/big/10802140.jpg");
        photos.put(CATEGORY_NAME_OTHER, "https://images.prom.ua/3085268783_kartki-z-fotoilyustratsiyami.jpg");
    }

    private void saveOrders(int numberOfOrders) {
        List<User> users = userService.findAllUsers();

        for (int i = 0; i < numberOfOrders; i++) {
            OrderDto order = new OrderDto();
            LocalDateTime creationDate = getRandomDate(90);
            LocalDateTime updateAt = getRandomDate(90);
            if (updateAt.isBefore(creationDate)) {
                updateAt = creationDate;
            }
            boolean paidStatus = random.nextBoolean();
            List<ProductLineDto> productLines = getListOfProductLine(order);
            double total = productLines.stream()
                    .mapToDouble(ProductLineDto::getTotalProductLineAmount)
                    .sum();
            BigDecimal totalPayment = BigDecimal.ZERO;
            if (paidStatus) {
                totalPayment = BigDecimal.valueOf(total);
            }
            User user = users.get((int) Math.floor(Math.random() * users.size()));
            order.setStatus(getStatus().getRepresentationStatus());
            order.setAddress(getAdresses());
            order.setDeleted(false);

            order.setProductLines(productLines);
            PaymentAndDeliveryDto paymentAndDelivery = getPaymentAndDelivery();

            order.setPaymentAndDelivery(paymentAndDelivery);

            order.setTotalPrice(Math.round(total * 100.0) / 100.0);
            UserDto userDto = userMapper.userToUserDto(user);
            order.setUser(userDto);
            order.setComment(generateRandomString(250));
            order.setPaidStatus(random.nextBoolean());
            order.setTotalPayment(totalPayment);

            orderService.save(order);
            order.setCreationDate(creationDate);
        }
    }

    private LocalDateTime getRandomDate(int daysFromNow) {
        int randomDays = random.nextInt(daysFromNow);
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.minusDays(randomDays);
    }

    private Status getStatus() {

        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.NEW);
        statuses.add(Status.CANCELLED);
        statuses.add(Status.PENDING);
        statuses.add(Status.RECEIVED);
        statuses.add(Status.SENT);
        int i = (int) Math.floor(Math.random() * statuses.size());

        return statuses.get(i);
    }

    private String getAdresses() {
        List<String> addresses = new ArrayList<>();
        addresses.add("Київ");
        addresses.add("Львів");
        addresses.add("Одеса");
        addresses.add("Харків");
        int i = (int) Math.floor(Math.random() * addresses.size());

        return addresses.get(i);
    }


    private List<ProductLineDto> getListOfProductLine(OrderDto order) {
        List<ProductLineDto> productLines = new ArrayList<>();
        for (int i = 1; i < (2 + (int) Math.round(Math.random() * 20)); i++) {
            long id = 1 + (long) Math.floor(Math.random() * productRepository.count());
            ProductLineDto productLine = new ProductLineDto();
            ProductDto productDto = productService.getProductById(id);
            productLine.setProduct(productDto);
            productLine.setQuantity((int) Math.round(Math.random() * 50));
            assert productDto != null;
            productLine.setTotalProductLineAmount(productLine.getQuantity() * productDto.getPrice());
            productLine.setOrderId(order.getId());
            productLines.add(productLine);
        }
        return productLines;
    }

    public void saveCategory() {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add(CATEGORY_NAME_TABLES);
        categoryNames.add(CATEGORY_NAMES_CHAIRS);
        categoryNames.add(CATEGORY_NAME_WARDROBE);
        categoryNames.add(CATEGORY_NAME_BEDS);
        categoryNames.add(CATEGORY_NAME_BEDSIDE_TABLES);
        categoryNames.add(CATEGORY_NAME_COFFEE_TABLES);
        categoryNames.add(CATEGORY_NAME_STOOLS);
        categoryNames.add(CATEGORY_NAMES_SOFAS);
        categoryNames.add(CATEGORY_NAME_OTHER);
        categoryNames.forEach(categoryName -> {
            CategoryDto category = new CategoryDto();
            category.setName(categoryName);
            categoryService.save(category);
        });
    }

    public void saveColoros() {
        List<String> colorNames = new ArrayList<>();
        colorNames.add("червоний");
        colorNames.add("блакитний");
        colorNames.add("зелений");
        colorNames.add("жовтий");
        colorNames.add("чорний");
        colorNames.add("золотий");
        colorNames.add("яйця дрозда");
        colorNames.add("Аквамариновий");
        colorNames.add("Вода пляжа Бонді");
        colorNames.add("Яскраво-рожевий");
        colorNames.add("Вишневий");
        colorNames.add("Персиковий");

        colorNames.forEach(colorName -> {
            ColorDto color = new ColorDto();
            color.setName(colorName);
            colorService.save(color);
        });
    }

    public void saveMaterials() {
        List<String> materialNames = new ArrayList<>();
        materialNames.add("дерево");
        materialNames.add("камінь");
        materialNames.add("залізо");
        materialNames.add("пластик");
        materialNames.add("скло");
        materialNames.add("МДФ");
        materialNames.add("ДСП");
        materialNames.add("Очерет");
        materialNames.add("Ротанг");
        materialNames.add("Акрил");
        materialNames.add("Алюміній");
        materialNames.add("Чугуній");
        materialNames.forEach(materialName -> {
            MaterialDto material = new MaterialDto();
            material.setName(materialName);
            materialService.save(material);
        });
    }

    public void saveUsers() {

        for (int i = 1; i <= 5; i++) {
            UserDto userDto = new UserDto();
            userDto.setAddress(getAdresses());
            userDto.setEmail("test" + i + "@test.com");
            userDto.setPassword(PasswordGeneratorUtil.generatePassword());
            userDto.setPhone("1234567" + i);
            userDto.setFirstName("Ім'я користувача " + generateRandomString(5));
            userDto.setSecondName("Прізвище користувача " + generateRandomString(7));

            userService.saveUser(userDto);
        }
    }

    public void saveProducts(int numberOfProducts) {
        for (long i = 1; i <= 20; i++) {
            for (int j = 0; j < numberOfProducts; j++) {
                ProductDto product = getNewProductDto();
                productService.save(product);
                LocalDateTime creationDate = getRandomDate(90);
                product.setCreationDate(creationDate);
            }
        }
    }

    private ProductDto getNewProductDto() {
        ProductDto product = new ProductDto();
        Long categoryId = (long) (1 + Math.floor(Math.random() * categoryRepository.count()));
        Long colorId = (long) (1 + Math.floor(Math.random() * colorRepository.count()));
        Long materialId = (long) (1 + Math.floor(Math.random() * materialRepository.count()));

        product.setCategoryId(categoryId);
        product.setColorId(colorId);
        product.setMaterialId(materialId);
        product.setName("product " + generateRandomString(5));
        product.setDescription(generateRandomString(100));
        product.setHeight(Math.round(Math.random() * 100) / 10.0);
        product.setLength(Math.round(Math.random() * 100) / 10.0);
        product.setWidth(Math.round(Math.random() * 100) / 10.0);
        product.setWeight(Math.round(Math.random() * 100) / 10.0);
        product.setPhotos(Collections.singletonList(getCategoryPhoto(categoryId)));
        product.setPrice(Math.round(Math.random() * 100000) / 100.0);
        product.setQuantity((int) (Math.random() * 100));
        product.setDeleted(false);
        product.setWarranty(12);

        return product;
    }

    private String getCategoryPhoto(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    String categoryName = category.getName();
                    return photos.get(categoryName);
                }).orElse("");
    }

    private String generateRandomString(int length) {
        String characters = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    private PaymentAndDeliveryDto getPaymentAndDelivery() {
        List<String> paymentTypes = new ArrayList<>();
        paymentTypes.add("Готівка");
        paymentTypes.add("Картка");
        List<String> deliveryType = new ArrayList<>();
        deliveryType.add("Нова пошта");
        deliveryType.add("Укрпошта");
        List<String> address = new ArrayList<>();
        address.add("вул. Українських Героїв, 47");
        address.add("вул. Хрещатик, 25");
        List<String> cities = new ArrayList<>();
        cities.add("Київ");
        cities.add("Одеса");
        cities.add("Харків");
        List<String> deliveryFees = new ArrayList<>();
        deliveryFees.add("За тарифами перевізника");
        deliveryFees.add("Самовивіз");

        PaymentAndDeliveryDto paymentAndDelivery = PaymentAndDeliveryDto.builder()
                .paymentType(paymentTypes.get(random.nextInt(paymentTypes.size())))
                .delivery(deliveryType.get(random.nextInt(deliveryType.size())))
                .address(address.get(random.nextInt(address.size())))
                .city(cities.get(random.nextInt(cities.size())))
                .deliveryFee(deliveryFees.get(random.nextInt(deliveryFees.size())))
                .build();

        return savePaymentAndDeliveryEntity(paymentAndDelivery);
    }

    private PaymentAndDeliveryDto savePaymentAndDeliveryEntity(PaymentAndDeliveryDto paymentAndDeliveryDto) {
        return paymentAndDeliveryService.save(paymentAndDeliveryDto);
    }
}
