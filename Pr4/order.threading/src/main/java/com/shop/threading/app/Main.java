package com.shop.threading.app;

import com.shop.processing.exception.OrderProcessingException;
import com.shop.processing.service.OrderGenerator;
import com.shop.processing.service.OrderProcessor;
import com.shop.storage.model.Clothing;
import com.shop.storage.model.Electronics;
import com.shop.storage.model.Order;
import com.shop.storage.model.Product;
import com.shop.storage.repo.OrderRepository;
import com.shop.threading.manager.OrderExecutionManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderGenerator generator = new OrderGenerator();
        OrderRepository<Product> repository = new OrderRepository<>();

        // Генерація тестових замовлень (JavaFaker + @Builder)
        List<Order<? extends Product>> generatedOrders = generator.generateMixedOrders(4);

        // Збереження замовлень у потокобезпечний репозиторій через лямбда-вираз
        generatedOrders.forEach(order ->
                repository.save(new Order<>(order.getOrderId(), order.getProduct(), order.getQuantity()))
        );

        System.out.println("=== Усього збережено замовлень: " + repository.count() + " ===\n");

        // Багатопотокова обробка замовлень
        OrderExecutionManager executionManager = new OrderExecutionManager(4);
        executionManager.processAllOrdersConcurrently(repository.findAll());
        executionManager.shutdown();

        System.out.println("\n=== Підсумковий аудит замовлень (Method Reference) ===");
        repository.findAll().forEach(OrderProcessor::printAuditSummary);

        // Демонстрація обробки виключення для узагальнених типів
        System.out.println("\n=== Перевірка захисту узагальнених типів ===");
        try {
            OrderProcessor<Electronics> strictProcessor = new OrderProcessor<>(Electronics.class);
            Clothing wrongTypeProduct = Clothing.builder()
                    .id("CL-999")
                    .name("Winter Jacket")
                    .price(120.0)
                    .size("L")
                    .material("Wool")
                    .build();

            // Спроба привести Clothing до узагальненого типу Electronics
            strictProcessor.castProductSafely(wrongTypeProduct);
        } catch (OrderProcessingException ex) {
            System.out.println("Перехоплено виключення типізації: " + ex.getMessage());
        }
    }
}