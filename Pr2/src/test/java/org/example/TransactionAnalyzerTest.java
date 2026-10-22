package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TransactionAnalyzerTest {

    private List<Transaction> testTransactions;

    @BeforeEach
    void setUp() {
        testTransactions = Arrays.asList(
                new Transaction("01-01-2024", 1000.0, "Зарплата"),
                new Transaction("02-01-2024", -500.0, "Продукти"),
                new Transaction("05-01-2024", -1500.0, "Комуналка"),
                new Transaction("10-01-2024", -200.0, "Кафе"),
                new Transaction("15-02-2024", -3000.0, "Одяг"),
                new Transaction("20-02-2024", -100.0, "Таксі")
        );
    }

    @Test
    void testCalculateTotalBalance() {
        double balance = TransactionAnalyzer.calculateTotalBalance(testTransactions);
        Assertions.assertEquals(-2300.0, balance, 0.001);
    }

    @Test
    void testCountTransactionsByMonth() {
        int countJan = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "01-2024");
        int countFeb = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "02-2024");

        Assertions.assertEquals(4, countJan);
        Assertions.assertEquals(2, countFeb);
    }

    @Test
    void testFindTopExpenses() {
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(testTransactions);

        Assertions.assertEquals(5, topExpenses.size());
        Assertions.assertEquals(-3000.0, topExpenses.get(0).getAmount());
        Assertions.assertEquals(-1500.0, topExpenses.get(1).getAmount());
    }

    @Test
    void testFindMaxAndMinExpenseForPeriod() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);

        Optional<Transaction> maxExp = TransactionAnalyzer.findMaxExpenseForPeriod(testTransactions, start, end);
        Optional<Transaction> minExp = TransactionAnalyzer.findMinExpenseForPeriod(testTransactions, start, end);

        Assertions.assertTrue(maxExp.isPresent());
        Assertions.assertEquals(-1500.0, maxExp.get().getAmount());

        Assertions.assertTrue(minExp.isPresent());
        Assertions.assertEquals(-200.0, minExp.get().getAmount());
    }
}