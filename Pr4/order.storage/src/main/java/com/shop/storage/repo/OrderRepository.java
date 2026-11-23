package com.shop.storage.repo;

import com.shop.storage.internal.StorageValidator;
import com.shop.storage.model.Order;
import com.shop.storage.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepository<T extends Product> {
    private final Map<String, Order<T>> storage = new ConcurrentHashMap<>();

    public void save(Order<T> order) {
        StorageValidator.validate(order);
        storage.put(order.getOrderId(), order);
    }

    public Optional<Order<T>> findById(String orderId) {
        return Optional.ofNullable(storage.get(orderId));
    }

    public List<Order<T>> findAll() {
        return new ArrayList<>(storage.values());
    }

    public int count() {
        return storage.size();
    }
}