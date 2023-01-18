package com.olekhv.onlinestore.entity.order;

public enum OrderStatus {
    IN_PROCESS("In process"),
    WAIT_FOR_DELIVERY("Wait for delivery"),
    DELIVERING("Delivering"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled"),
    RETURNED("Returned");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
