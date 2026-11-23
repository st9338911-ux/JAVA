package com.shop.threading.internal;

public final class ThreadMetricsLogger {
    private ThreadMetricsLogger() {}

    public static void logPoolEvent(String message) {
        System.out.printf("[THREAD-MANAGER | %s] %s%n", Thread.currentThread().getName(), message);
    }
}