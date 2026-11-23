package com.shop.storage.internal;

import com.shop.storage.model.Order;
import com.shop.storage.model.Product;

public final class StorageValidator {
    private StorageValidator() {}

    public static <T extends Product> void validate(Order<T> order) {
        if (order == null || order.getOrderId() == null || order.getOrderId().isBlank()) {
            throw new IllegalArgumentException("Замовлення або його ідентифікатор не можуть бути порожніми.");
        }
        if (order.getProduct() == null) {
            throw new IllegalArgumentException("Товар у замовленні " + order.getOrderId() + " відсутній.");
        }
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Кількість товару має бути більшою за 0.");
        }
    }
}