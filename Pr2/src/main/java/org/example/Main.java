package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        // 1. Читання даних зі статичного методу
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);
        System.out.println("Успішно прочитано " + transactions.size() + " транзакцій.\n");

        // 2. Розрахунок загального балансу
        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        // 3. Підрахунок транзакцій за місяць
        String monthYear = "01-2024";
        int count = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, count);

        // 4. Виведення 10 найбільших витрат
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        // 5. Визначення найбільшої і найменшої витрати за період (Самостійна робота)
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);
        Optional<Transaction> maxExp = TransactionAnalyzer.findMaxExpenseForPeriod(transactions, start, end);
        Optional<Transaction> minExp = TransactionAnalyzer.findMinExpenseForPeriod(transactions, start, end);
        TransactionReportGenerator.printPeriodExpenseMinMax(
                "01-2024",
                maxExp.orElse(null),
                minExp.orElse(null)
        );

        // 6. Текстовий звіт із візуалізацією символами '*' (Самостійна робота)
        Map<String, Double> categoryExpenses = TransactionAnalyzer.calculateExpensesByCategory(transactions);
        Map<String, Double> monthlyExpenses = TransactionAnalyzer.calculateExpensesByMonth(transactions);
        TransactionReportGenerator.printVisualExpenseReport(categoryExpenses, monthlyExpenses);
    }
}