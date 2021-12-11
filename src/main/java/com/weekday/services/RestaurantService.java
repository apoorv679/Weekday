package com.weekday.services;

import com.weekday.configs.OrderConfig;
import com.weekday.configs.RestaurantConfig;
import com.weekday.models.Order;
import com.weekday.models.Restaurant;

import java.util.*;

public class RestaurantService {

    public Map<Integer, Double> calculateFoodDeliveryTimes(List<Order> orders, Restaurant restaurant) {
        Map<Integer, Double> foodDeliveryTimes = new LinkedHashMap<>();
        PriorityQueue<Order> orderProcessingQueue = new PriorityQueue<>();

        for (Order order : orders) {
            order.calculateSlotsRequired(order);
            if (order.getSlotsRequired() > RestaurantConfig.MAX_SLOTS) {
                foodDeliveryTimes.put(order.getOrderId(), -1.0);
                continue;
            }
            order.calculateTotalTimeRequired(order);

            if (restaurant.getAvailableSlots() >= order.getSlotsRequired()) {
                foodDeliveryTimes.put(order.getOrderId(), order.getTotalTimeRequired());
                orderProcessingQueue.add(order);
                restaurant.setAvailableSlots(restaurant.getAvailableSlots() - order.getSlotsRequired());
            } else {
                Order orderWithMinimumWaitTime = orderProcessingQueue.peek();
                if (orderWithMinimumWaitTime == null) {
                    continue;
                }

                if (order.getTotalTimeRequired() + orderWithMinimumWaitTime.getTotalTimeRequired() > OrderConfig.MAX_WAIT_TIME_FOR_ORDER_IN_MINUTES) {
                    foodDeliveryTimes.put(order.getOrderId(), -2.0);
                    continue;
                }
                orderProcessingQueue.poll();
                restaurant.setAvailableSlots(restaurant.getAvailableSlots() + orderWithMinimumWaitTime.getSlotsRequired() - order.getSlotsRequired());
                foodDeliveryTimes.put(order.getOrderId(), order.getTotalTimeRequired() + orderWithMinimumWaitTime.getTotalTimeRequired());
            }
        }

        return foodDeliveryTimes;
    }
}
