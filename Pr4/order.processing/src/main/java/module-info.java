module order.processing {
    requires transitive order.storage;
    requires javafaker;
    requires java.sql;

    exports com.shop.processing.service;
    exports com.shop.processing.exception;
}