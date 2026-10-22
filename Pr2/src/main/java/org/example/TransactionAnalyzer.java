package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private TransactionAnalyzer() {}

    // Розрахунок загального балансу
    public static double calculateTotalBalance(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // Підрахунок кількості транзакцій за місяць ("MM-yyyy")
    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            try {
                LocalDate date = LocalDate.parse(transaction.getDate(), DATE_FORMATTER);
                String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                if (transactionMonthYear.equals(monthYear)) {
                    count++;
                }
            } catch (DateTimeParseException ignored) {}
        }
        return count;
    }

    // 10 найбільших витрат (amount < 0)
    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Самостійна робота: Найбільша витрата за період
    public static Optional<Transaction> findMaxExpenseForPeriod(List<Transaction> transactions, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> isWithinPeriod(t.getDate(), startDate, endDate))
                .min(Comparator.comparingDouble(Transaction::getAmount));
    }

    // Самостійна робота: Найменша витрата за період
    public static Optional<Transaction> findMinExpenseForPeriod(List<Transaction> transactions, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> isWithinPeriod(t.getDate(), startDate, endDate))
                .max(Comparator.comparingDouble(Transaction::getAmount));
    }

    // Самостійна робота: Сумарні витрати по категоріях
    public static Map<String, Double> calculateExpensesByCategory(List<Transaction> transactions) {
        Map<String, Double> categoryMap = new HashMap<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                String category = t.getDescription();
                categoryMap.put(category, categoryMap.getOrDefault(category, 0.0) + Math.abs(t.getAmount()));
            }
        }
        return categoryMap;
    }

    // Самостійна робота: Сумарні витрати по місяцях
    public static Map<String, Double> calculateExpensesByMonth(List<Transaction> transactions) {
        Map<String, Double> monthlyMap = new TreeMap<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                try {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    String monthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                    monthlyMap.put(monthYear, monthlyMap.getOrDefault(monthYear, 0.0) + Math.abs(t.getAmount()));
                } catch (DateTimeParseException ignored) {}
            }
        }
        return monthlyMap;
    }

    private static boolean isWithinPeriod(String dateStr, LocalDate startDate, LocalDate endDate) {
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                    (date.isEqual(endDate) || date.isBefore(endDate));
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}