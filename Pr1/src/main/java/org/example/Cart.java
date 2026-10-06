package org.example;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Cart {
    private List<Product> products = new ArrayList<>(); // Список товарів у кошику

    // Метод для додавання товару до кошика
    public void addProduct(Product product) {
        products.add(product);
    }

    // Метод для видалення товару з кошика
    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products); // Повертаємо копію списку
    }

    public void clear() {
        products.clear(); // Очищення списку товарів у кошику
    }

    // Метод для виведення інформації про всі товари у кошику
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Кошик містить:\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Загальна вартість: ").append(getTotalPrice());
        return sb.toString();
    }
}