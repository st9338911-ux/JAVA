module order.threading {
    requires order.processing;

    exports com.shop.threading.manager;
    exports com.shop.threading.app;
    // Пакет com.shop.threading.internal прихований від зовнішніх модулів
}