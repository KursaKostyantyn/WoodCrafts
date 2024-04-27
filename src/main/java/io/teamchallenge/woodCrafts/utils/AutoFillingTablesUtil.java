package io.teamchallenge.woodCrafts.utils;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.PaymentAndDelivery;
import io.teamchallenge.woodCrafts.models.Product;
import io.teamchallenge.woodCrafts.models.ProductLine;
import io.teamchallenge.woodCrafts.models.User;
import io.teamchallenge.woodCrafts.models.dto.UserDto;
import io.teamchallenge.woodCrafts.repository.CategoryRepository;
import io.teamchallenge.woodCrafts.repository.ColorRepository;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import io.teamchallenge.woodCrafts.repository.OrderRepository;
import io.teamchallenge.woodCrafts.repository.PaymentAndDeliveryRepository;
import io.teamchallenge.woodCrafts.repository.ProductRepository;
import io.teamchallenge.woodCrafts.services.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AutoFillingTablesUtil {
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PaymentAndDeliveryRepository paymentAndDeliveryRepository;

    @Bean
    @Transactional
    public void autoFilling() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            int numberOfProducts = 10;
            saveCategory();
            saveColoros();
            saveMaterials();
            saveUsers();
            saveProducts(numberOfProducts);
            saveOrders(200);

        }
    }


    private void saveOrders(int numberOfOrders) {
        List<User> users = userService.findAllUsers();
        Random random = new Random();
        for (int i = 0; i < numberOfOrders; i++) {
            Order order = new Order();
            LocalDateTime creationDate = getRandomDate(90);
            LocalDateTime updateAt = getRandomDate(90);
            if (updateAt.isBefore(creationDate)) {
                updateAt = creationDate;
            }
            boolean paidStatus = random.nextBoolean();
            List<ProductLine> productLines = getListOfProductLine(order);
            double total = productLines.stream()
                    .mapToDouble(ProductLine::getTotalProductLineAmount)
                    .sum();
            BigDecimal totalPayment = BigDecimal.ZERO;
            if (paidStatus) {
                totalPayment = BigDecimal.valueOf(total);
            }
            User user = users.get((int) Math.floor(Math.random() * users.size()));
            order.setStatus(getStatus());
            order.setAddress(getAdresses());
            order.setDeleted(false);

            order.setProductLines(productLines);
            PaymentAndDelivery paymentAndDelivery = generatePaymentAndDelivery();
            savePaymentAndDeliveryEntity(paymentAndDelivery);
            order.setPaymentAndDelivery(paymentAndDelivery);

            order.setTotalPrice(Math.round(total * 100.0) / 100.0);
            order.setUser(user);
            order.setComment(generateRandomString(250));
            order.setPaidStatus(random.nextBoolean());
            order.setTotalPayment(totalPayment);
            order.setCreationDate(creationDate);
            order.setUpdatedAt(updateAt);
            user.getOrders().add(order);
            orderRepository.save(order);
        }
    }

    private LocalDateTime getRandomDate(int daysFromNow) {
        Random random = new Random();
        int randomDays = random.nextInt(daysFromNow);
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.minus(randomDays, ChronoUnit.DAYS);
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


    private List<ProductLine> getListOfProductLine(Order order) {
        List<ProductLine> productLines = new ArrayList<>();
        for (int i = 1; i < (2 + (int) Math.round(Math.random() * 20)); i++) {
            long id = 1 + (long) Math.floor(Math.random() * productRepository.count());
            ProductLine productLine = new ProductLine();
            Product product = productRepository.findById(id).orElse(null);
            productLine.setProduct(product);
            productLine.setQuantity((int) Math.round(Math.random() * 50));
            assert product != null;
            productLine.setTotalProductLineAmount(productLine.getQuantity() * product.getPrice());
            productLine.setOrder(order);
            productLines.add(productLine);
        }
        return productLines;
    }


    private void saveCategory() {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("Столи");
        categoryNames.add("Стільці");
        categoryNames.add("Ліжка");
        categoryNames.add("Крісла");
        categoryNames.add("Шафи");
        categoryNames.add("Тумбочки приліжкова");
        categoryNames.add("Туалетні столики");
        categoryNames.add("Пуфи");
        categoryNames.add("Шафи для одягу");
        categoryNames.add("Дивани");
        categoryNames.add("Журнальні столики");
        categoryNames.add("Шезлонги");
        categoryNames.forEach(categoryName -> {
            Category category = new Category();
            category.setName(categoryName);
            category.setProducts(new ArrayList<>());
            categoryRepository.save(category);
        });
    }

    private void saveColoros() {
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
            Color color = new Color();
            color.setName(colorName);
            color.setProducts(new ArrayList<>());
            colorRepository.save(color);
        });
    }

    private void saveMaterials() {
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
            Material material = new Material();
            material.setName(materialName);
            material.setProducts(new ArrayList<>());
            materialRepository.save(material);
        });
    }

    private void saveUsers() {

        for (int i = 1; i <= 5; i++) {
            UserDto userDto = new UserDto();
            userDto.setAddress(getAdresses());
            userDto.setEmail("test" + i + "@test.com");
            userDto.setPassword("123456");
            userDto.setPhone("1234567" + i);
            userDto.setFirstName("Ім'я користувача " + generateRandomString(5));
            userDto.setSecondName("Прізвище користувача " + generateRandomString(7));
            userDto.setOrders(new ArrayList<>());

            userService.saveUser(userDto);
        }
    }

    private void saveProducts(int numberOfProducts) {
        for (long i = 1; i <= 20; i++) {
            for (int j = 0; j < numberOfProducts; j++) {
                Product product = getNewProduct();
                productRepository.save(product);
            }
        }
    }

    private Product getNewProduct() {
        Product product = new Product();
        Category category = categoryRepository
                .findById((long) (1 + Math.floor(Math.random() * categoryRepository.count())))
                .orElse(new Category());
        Color color = colorRepository
                .findById((long) (1 + Math.floor(Math.random() * colorRepository.count())))
                .orElse(new Color());
        Material material = materialRepository
                .findById((long) (1 + Math.floor(Math.random() * materialRepository.count())))
                .orElse(new Material());

        product.setCategory(category);
        product.setColor(color);
        product.setMaterial(material);
        product.setName("product " + generateRandomString(5));
        product.setDescription(generateRandomString(100));
        product.setHeight(Math.round(Math.random() * 100) / 10.0);
        product.setLength(Math.round(Math.random() * 100) / 10.0);
        product.setWidth(Math.round(Math.random() * 100) / 10.0);
        product.setWeight(Math.round(Math.random() * 100) / 10.0);
        product.setPhotos(Collections.singletonList("https://content1.rozetka.com.ua/goods/images/big/273094280.jpg"));
        product.setPrice(Math.round(Math.random() * 100000) / 100.0);
        product.setQuantity((int) (Math.random() * 100));
        product.setWarranty(12);

        return product;
    }

    private String generateRandomString(int length) {
        String characters = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    private PaymentAndDelivery generatePaymentAndDelivery() {
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

        Random random = new Random();
        return PaymentAndDelivery.builder()
                .paymentType(paymentTypes.get(random.nextInt(paymentTypes.size())))
                .delivery(deliveryType.get(random.nextInt(deliveryType.size())))
                .address(address.get(random.nextInt(address.size())))
                .city(cities.get(random.nextInt(cities.size())))
                .deliveryFee(deliveryFees.get(random.nextInt(deliveryFees.size())))
                .build();
    }

    private PaymentAndDelivery savePaymentAndDeliveryEntity(PaymentAndDelivery paymentAndDelivery) {
        paymentAndDeliveryRepository.save(paymentAndDelivery);
        return paymentAndDelivery;
    }
}
