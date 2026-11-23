package com.shop.processing.internal;

import com.shop.storage.model.Clothing;
import com.shop.storage.model.Electronics;
import com.shop.storage.model.Product;

public final class DiscountCalculator {
    private DiscountCalculator() {}

    public static <T extends Product> double calculateFinalPrice(T product, int quantity) {
        double baseTotal = product.getPrice() * quantity;
        if (product instanceof Electronics && quantity >= 2) {
            return baseTotal * 0.90; // 10% знижка на партію електроніки
        }
        if (product instanceof Clothing && quantity >= 3) {
            return baseTotal * 0.85; // 15% знижка на одяг
        }
        return baseTotal;
    }
}