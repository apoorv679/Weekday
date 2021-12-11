package com.weekday.models;

import com.weekday.configs.OrderConfig;

import java.util.List;

public class Order implements Comparable<Order> {
    private int orderId;
    private List<String> meals;
    private double distance;
    private int slotsRequired;
    private double totalTimeRequired;

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
        return slotsRequired;
    }

    public void calculateSlotsRequired(Order order) {
        if (order.getMeals().isEmpty()) {
            this.slotsRequired = 0;
        }

        int totalSlotsRequired = 0;
        for (String meal : order.getMeals()) {
            if (meal.equals("A")) {
                totalSlotsRequired += 1;
            } else if (meal.equals("M")) {
                totalSlotsRequired += 2;
            }
        }

        this.slotsRequired = totalSlotsRequired;
    }

    private double calculateCookingTimeForOrder(Order order) {
        double orderCookingTime = 0;
        for (String meal : order.getMeals()) {
            if (meal.equals("A")) {
                orderCookingTime = Math.max(orderCookingTime, OrderConfig.MEAL_TIME_FOR_APPETIZER_IN_MINUTES);
            } else if (meal.equals("M")) {
                orderCookingTime = Math.max(orderCookingTime, OrderConfig.MEAL_TIME_FOR_MAIN_COURSE_IN_MINUTES);
            }
        }

        return orderCookingTime;
    }

    private double calculateDeliveryTimeForOrder(Order order) {
        return order.getDistance() * OrderConfig.DELIVERY_TIME_TAKEN_PER_KILOMETER;
    }

    public double getTotalTimeRequired() {
        return totalTimeRequired;
    }

    public void calculateTotalTimeRequired(Order order) {
        double totalTimeRequiredForOrder = 0;
        totalTimeRequiredForOrder += calculateCookingTimeForOrder(order);
        totalTimeRequiredForOrder += calculateDeliveryTimeForOrder(order);

        this.totalTimeRequired = totalTimeRequiredForOrder;
    }

    @Override
    public int compareTo(Order o) {
        return Double.compare(totalTimeRequired, o.getTotalTimeRequired());
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
