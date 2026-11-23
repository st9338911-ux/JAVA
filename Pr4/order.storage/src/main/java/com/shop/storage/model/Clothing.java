package com.shop.storage.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class Clothing extends Product {
    private final String size;
    private final String material;

    @Builder
    public Clothing(String id, String name, double price, String size, String material) {
        super(id, name, price);
        this.size = size;
        this.material = material;
    }

    @Override
    public String getCategory() {
        return "Clothing";
    }
}