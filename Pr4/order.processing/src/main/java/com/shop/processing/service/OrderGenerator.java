package com.shop.processing.service;

import com.github.javafaker.Faker;
import com.shop.storage.model.Clothing;
import com.shop.storage.model.Electronics;
import com.shop.storage.model.Order;
import com.shop.storage.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class OrderGenerator {
    private final Faker faker = new Faker(Locale.ENGLISH);
    private static final String[] SIZES = {"XS", "S", "M", "L", "XL"};

    public Order<Electronics> generateElectronicsOrder() {
        Electronics electronics = Electronics.builder()
                .id("EL-" + UUID.randomUUID().toString().substring(0, 6))
                .name(faker.commerce().productName())
                .price(Double.parseDouble(faker.commerce().price(100.0, 1500.0).replace(",", ".")))
                .brand(faker.company().name())
                .warrantyMonths(faker.number().numberBetween(12, 36))
                .build();

        return new Order<>(
                "ORD-" + faker.number().digits(5),
                electronics,
                faker.number().numberBetween(1, 4)
        );
    }

    public Order<Clothing> generateClothingOrder() {
        Clothing clothing = Clothing.builder()
                .id("CL-" + UUID.randomUUID().toString().substring(0, 6))
                .name(faker.commerce().material() + " " + faker.commerce().productName())
                .price(Double.parseDouble(faker.commerce().price(20.0, 250.0).replace(",", ".")))
                .size(SIZES[faker.number().numberBetween(0, SIZES.length)])
                .material(faker.commerce().material())
                .build();

        return new Order<>(
                "ORD-" + faker.number().digits(5),
                clothing,
                faker.number().numberBetween(1, 5)
        );
    }

    public List<Order<? extends Product>> generateMixedOrders(int countPerType) {
        List<Order<? extends Product>> orders = new ArrayList<>();
        for (int i = 0; i < countPerType; i++) {
            orders.add(generateElectronicsOrder());
            orders.add(generateClothingOrder());
        }
        return orders;
    }
}