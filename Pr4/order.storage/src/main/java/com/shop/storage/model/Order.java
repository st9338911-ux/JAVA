package com.shop.storage.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Order<T extends Product> {
    private final String orderId;
    private final T product;
    private final int quantity;
    private volatile boolean processed;

    public Order(String orderId, T product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.processed = false;
    }

    public synchronized void markAsProcessed() {
        this.processed = true;
    }

    public double getTotalPrice() {
        return product != null ? product.getPrice() * quantity : 0.0;
    }
}