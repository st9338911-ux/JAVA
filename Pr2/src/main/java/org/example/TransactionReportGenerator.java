package org.example;

import java.util.List;
import java.util.Map;

public abstract class TransactionReportGenerator {

    private static final double UNITS_PER_STAR = 1000.0;

    private TransactionReportGenerator() {}

    public static void printBalanceReport(double totalBalance) {
        System.out.println("=== ЗАГАЛЬНИЙ БАЛАНС ===");
        System.out.printf("Загальний баланс: %.2f грн%n%n", totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.printf("Кількість транзакцій за %s: %d%n%n", monthYear, count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("=== 10 НАЙБІЛЬШИХ ВИТРАТ ===");
        for (Transaction expense : topExpenses) {
            System.out.printf("%s | %s: %.2f грн%n", expense.getDate(), expense.getDescription(), expense.getAmount());
        }
        System.out.println();
    }

    // Самостійна робота: Звіт про макс/мін витрати за період
    public static void printPeriodExpenseMinMax(String periodName, Transaction maxExpense, Transaction minExpense) {
        System.out.println("=== ВИТРАТИ ЗА ПЕРІОД (" + periodName + ") ===");
        if (maxExpense != null) {
            System.out.printf("Найбільша витрата: %s (%s) -> %.2f грн%n", maxExpense.getDescription(), maxExpense.getDate(), maxExpense.getAmount());
        }
        if (minExpense != null) {
            System.out.printf("Найменша витрата:  %s (%s) -> %.2f грн%n", minExpense.getDescription(), minExpense.getDate(), minExpense.getAmount());
        }
        System.out.println();
    }

    // Самостійна робота: Візуалізований текстовий звіт по категоріях та місяцях (* = 1000 грн)
    public static void printVisualExpenseReport(Map<String, Double> categoryExpenses, Map<String, Double> monthlyExpenses) {
        System.out.println("=== ВІЗУАЛЬНИЙ ЗВІТ ВИТРАТ (1 '*' = 1000 грн) ===");

        System.out.println("\n--- Витрати по категоріях ---");
        categoryExpenses.forEach((category, amount) -> {
            int stars = (int) (amount / UNITS_PER_STAR);
            String chart = "*".repeat(Math.max(1, stars));
            System.out.printf("%-20s | %-30s (%.2f грн)%n", category, chart, amount);
        });

        System.out.println("\n--- Витрати по місяцях ---");
        monthlyExpenses.forEach((month, amount) -> {
            int stars = (int) (amount / UNITS_PER_STAR);
            String chart = "*".repeat(Math.max(1, stars));
            System.out.printf("%-10s | %-30s (%.2f грн)%n", month, chart, amount);
        });
        System.out.println();
    }
}