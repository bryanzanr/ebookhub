package com.herokuapp.ebookhub.customers.entities;

public enum CustomersPreference {

    FOOD("Food", 1),
    FASHION("Fashion", 2),
    LIFESTYLE("Lifestyle", 3),
    PROPERTY("Property", 4);

    private final String value;

    private final Integer order;

    private CustomersPreference(String value, Integer order) {
        this.value = value;
        this.order = order;
    }

    public String getValue() {
        return this.value;
    }

    public Integer getOrder() {
        return this.order;
    }

}