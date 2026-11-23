package com.shop.storage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public abstract class Product {
    private final String id;
    private final String name;
    private final double price;

    public abstract String getCategory();
}