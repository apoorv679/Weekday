package com.weekday.models;

import java.util.List;

public class Order {
    private int orderId;
    private List<String> meals;
    private int distance;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<String> getMeals() {
        return meals;
    }

    public void setMeals(List<String> meals) {
        this.meals = meals;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", meals=" + meals +
                ", distance=" + distance +
                '}';
    }
}
