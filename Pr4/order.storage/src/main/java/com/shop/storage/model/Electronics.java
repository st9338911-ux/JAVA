package com.shop.storage.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class Electronics extends Product {
    private final String brand;
    private final int warrantyMonths;

    @Builder
    public Electronics(String id, String name, double price, String brand, int warrantyMonths) {
        super(id, name, price);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }
}