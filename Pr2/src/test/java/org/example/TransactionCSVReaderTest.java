package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TransactionCSVReaderTest {

    @Test
    void testReadTransactionsFromUrl() {
        String testUrl = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(testUrl);

        Assertions.assertNotNull(transactions, "Список транзакцій не повинен бути null");
        Assertions.assertFalse(transactions.isEmpty(), "Список транзакцій не повинен бути порожнім");
    }
}