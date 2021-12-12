package com.weekday.models;

import com.weekday.configs.OrderConfig;

import java.util.List;

public class Order implements Comparable<Order> {
    private int orderId;
    private List<String> meals;
    private double distance;
    private int slotsRequired;
    private double preparationTimeRequired;
    private double actualDeliveryTime;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getSlotsRequired() {
        calculateSlotsRequired();
        return slotsRequired;
    }

    public double getPreparationTimeRequired() {
        calculatePreparationTimeRequired();
        return preparationTimeRequired;
    }

    private void calculateSlotsRequired() {
        if (meals.isEmpty()) {
            this.slotsRequired = 0;
        }

        int totalSlotsRequired = 0;
        for (String meal : meals) {
            if (meal.equals("A")) {
                totalSlotsRequired += 1;
            } else if (meal.equals("M")) {
                totalSlotsRequired += 2;
            }
        }

        this.slotsRequired = totalSlotsRequired;
    }

    public void calculatePreparationTimeRequired() {
        double preparationTimeRequired = 0;
        preparationTimeRequired += calculateCookingTimeForOrder();
        preparationTimeRequired += calculateDeliveryTimeForOrder();

        this.preparationTimeRequired = preparationTimeRequired;
    }

    private double calculateCookingTimeForOrder() {
        double orderCookingTime = 0;
        for (String meal : meals) {
            if (meal.equals("A")) {
                orderCookingTime = Math.max(orderCookingTime, OrderConfig.PREPARATION_TIME_FOR_APPETIZER_IN_MINUTES);
            } else if (meal.equals("M")) {
                orderCookingTime = Math.max(orderCookingTime, OrderConfig.PREPARATION_TIME_FOR_MAIN_COURSE_IN_MINUTES);
            }
        }

        return orderCookingTime;
    }

    private double calculateDeliveryTimeForOrder() {
        return distance * OrderConfig.DELIVERY_TIME_TAKEN_PER_KILOMETER;
    }

    public double getActualDeliveryTime() {
        return actualDeliveryTime;
    }

    public void setActualDeliveryTime(double actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime;
    }

    @Override
    public int compareTo(Order o) {
        return Double.compare(actualDeliveryTime, o.getActualDeliveryTime());
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
