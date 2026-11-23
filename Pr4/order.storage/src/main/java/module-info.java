module order.storage {
    requires static lombok;

    exports com.shop.storage.model;
    exports com.shop.storage.repo;
    // Пакет com.shop.storage.internal навмисно не експортується (інкапсуляція модуля)
}