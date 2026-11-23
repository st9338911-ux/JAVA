package com.shop.processing.service;

import com.shop.processing.exception.OrderProcessingException;
import com.shop.processing.internal.DiscountCalculator;
import com.shop.storage.model.Order;
import com.shop.storage.model.Product;

public class OrderProcessor<T extends Product> {
    private final Class<T> supportedType;

    public OrderProcessor(Class<T> supportedType) {
        this.supportedType = supportedType;
    }

    /**
     * Обробка типізованого замовлення з валідацією узагальненого типу.
     */
    public void processOrder(Order<T> order) {
        if (order == null || order.getProduct() == null) {
            throw new OrderProcessingException("Помилка обробки: замовлення або товар відсутні.");
        }

        T product = order.getProduct();
        if (!supportedType.isInstance(product)) {
            throw new OrderProcessingException(
                    String.format("Невідповідність узагальненого типу: очікувався %s, отримано %s",
                            supportedType.getSimpleName(), product.getClass().getSimpleName())
            );
        }

        if (product.getPrice() <= 0) {
            throw new OrderProcessingException(
                    "Некоректна ціна товару '" + product.getName() + "': " + product.getPrice()
            );
        }

        double finalAmount = DiscountCalculator.calculateFinalPrice(product, order.getQuantity());
        order.markAsProcessed();

        System.out.printf("[%s] Оброблено замовлення #%s | Категорія: %s | Товар: %s (x%d) | До сплати: $%.2f%n",
                Thread.currentThread().getName(),
                order.getOrderId(),
                product.getCategory(),
                product.getName(),
                order.getQuantity(),
                finalAmount);
    }

    /**
     * Безпечне приведення сирого об'єкта до узагальненого типу T з обробкою ClassCastException.
     */
    public T castProductSafely(Object rawProduct) {
        try {
            return supportedType.cast(rawProduct);
        } catch (ClassCastException e) {
            throw new OrderProcessingException(
                    "Критична помилка приведення узагальненого типу до " + supportedType.getSimpleName(), e
            );
        }
    }

    /**
     * Статичний метод для виклику через посилання на метод (OrderProcessor::printAuditSummary).
     */
    public static void printAuditSummary(Order<? extends Product> order) {
        System.out.printf("Аудит -> ID: %s | Статус: %s | Товар: %s%n",
                order.getOrderId(),
                order.isProcessed() ? "ВИКОНАНО" : "ОЧІКУЄ",
                order.getProduct().getName());
    }
}