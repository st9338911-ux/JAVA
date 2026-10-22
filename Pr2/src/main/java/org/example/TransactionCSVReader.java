package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    private TransactionCSVReader() {}

    public static List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            URL url = new URL(filePath);
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] values = line.split(",");
                    if (values.length >= 3) {
                        Transaction transaction = Transaction.builder()
                                .date(values[0].trim())
                                .amount(Double.parseDouble(values[1].trim()))
                                .description(values[2].trim())
                                .build();
                        transactions.add(transaction);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Помилка зчитування CSV даних: " + e.getMessage());
        }
        return transactions;
    }
}