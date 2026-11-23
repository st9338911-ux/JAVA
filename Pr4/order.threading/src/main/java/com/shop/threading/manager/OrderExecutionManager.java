package com.shop.threading.manager;

import com.shop.processing.exception.OrderProcessingException;
import com.shop.processing.service.OrderProcessor;
import com.shop.storage.model.Clothing;
import com.shop.storage.model.Electronics;
import com.shop.storage.model.Order;
import com.shop.storage.model.Product;
import com.shop.threading.internal.ThreadMetricsLogger;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrderExecutionManager {
    private final ExecutorService executorService;

    public OrderExecutionManager(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void processAllOrdersConcurrently(List<? extends Order<? extends Product>> allOrders) {
        OrderProcessor<Electronics> electronicsProcessor = new OrderProcessor<>(Electronics.class);
        OrderProcessor<Clothing> clothingProcessor = new OrderProcessor<>(Clothing.class);

        // 1. Фільтрація замовлень електроніки за допомогою лямбда-виразів
        List<Order<Electronics>> electronicsOrders = allOrders.stream()
                .filter(order -> order.getProduct() instanceof Electronics)
                .map(order -> new Order<>(
                        order.getOrderId(),
                        electronicsProcessor.castProductSafely(order.getProduct()),
                        order.getQuantity()
                ))
                .toList();

        // 2. Фільтрація замовлень одягу за допомогою лямбда-виразів
        List<Order<Clothing>> clothingOrders = allOrders.stream()
                .filter(order -> order.getProduct() instanceof Clothing)
                .map(order -> new Order<>(
                        order.getOrderId(),
                        clothingProcessor.castProductSafely(order.getProduct()),
                        order.getQuantity()
                ))
                .toList();

        ThreadMetricsLogger.logPoolEvent("Запуск паралельної обробки: "
                + electronicsOrders.size() + " електроніки, "
                + clothingOrders.size() + " одягу.");

        // 3. Запуск потоків через лямбда-вирази та виклик обробки через посилання на методи (::)
        CompletableFuture<Void> electronicsTask = CompletableFuture.runAsync(() -> {
            try {
                electronicsOrders.forEach(electronicsProcessor::processOrder);
            } catch (OrderProcessingException e) {
                System.err.println("Помилка в потоці електроніки: " + e.getMessage());
            }
        }, executorService);

        CompletableFuture<Void> clothingTask = CompletableFuture.runAsync(() -> {
            try {
                clothingOrders.forEach(clothingProcessor::processOrder);
            } catch (OrderProcessingException e) {
                System.err.println("Помилка в потоці одягу: " + e.getMessage());
            }
        }, executorService);

        // Очікування завершення обох потоків обробки
        CompletableFuture.allOf(electronicsTask, clothingTask).join();
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            ThreadMetricsLogger.logPoolEvent("Пул потоків коректно зупинено.");
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}