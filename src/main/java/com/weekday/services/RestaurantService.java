package com.weekday.services;

import com.weekday.configs.OrderConfig;
import com.weekday.configs.RestaurantConfig;
import com.weekday.models.Order;
import com.weekday.models.Restaurant;
import com.weekday.models.enums.OrderStatusEnum;
import com.weekday.utils.RestaurantUtils;

import java.util.*;

public class RestaurantService {

    private final Restaurant restaurant = new Restaurant(RestaurantConfig.MAX_SLOTS);

    private boolean isOrderValid(Order order) {
        if (order.getSlotsRequired() > RestaurantConfig.MAX_SLOTS) {
            RestaurantUtils.printResultForOrder(OrderStatusEnum.ORDER_FAILURE_SLOTS_REQUIRED, order.getOrderId());
            return false;
        }

        if (order.getPreparationTimeRequired() > OrderConfig.MAX_WAIT_TIME_FOR_ORDER_IN_MINUTES) {
            RestaurantUtils.printResultForOrder(OrderStatusEnum.ORDER_FAILURE_TIME_REQUIRED, order.getOrderId());
            return false;
        }

        return true;
    }

    private Double getWaitTimeFromRequiredSlots(PriorityQueue<Order> processingQueue, Order currentOrder) {
        double waitTimeForCurrentOrder = 0;
        int availableSlots = restaurant.getAvailableSlots();
        List<Order> tempOrderList = new ArrayList<>();

        while(!processingQueue.isEmpty() && currentOrder.getSlotsRequired() > availableSlots) {
            Order tempOrder = processingQueue.poll();
            availableSlots += tempOrder.getSlotsRequired();
            tempOrderList.add(tempOrder);
            waitTimeForCurrentOrder = tempOrder.getActualDeliveryTime();
            if (waitTimeForCurrentOrder + currentOrder.getPreparationTimeRequired() > OrderConfig.MAX_WAIT_TIME_FOR_ORDER_IN_MINUTES) {
                processingQueue.addAll(tempOrderList);
                RestaurantUtils.printResultForOrder(OrderStatusEnum.ORDER_FAILURE_TIME_REQUIRED, currentOrder.getOrderId());
                return null;
            }
        }

        restaurant.setAvailableSlots(availableSlots);
        return waitTimeForCurrentOrder;
    }

    public void calculateFoodDeliveryTimes(List<Order> orders) {
        PriorityQueue<Order> orderProcessingQueue = new PriorityQueue<>();

        for (Order order : orders) {
            if (isOrderValid(order)) {
                Double waitTimeForOrder = 0.0;
                if (order.getSlotsRequired() > restaurant.getAvailableSlots()) {
                    waitTimeForOrder = getWaitTimeFromRequiredSlots(orderProcessingQueue, order);
                }

                if (waitTimeForOrder != null) {
                    order.setActualDeliveryTime(order.getPreparationTimeRequired() + waitTimeForOrder);
                    RestaurantUtils.printResultForOrder(OrderStatusEnum.ORDER_SUCCESS, order.getOrderId(), order.getActualDeliveryTime());
                    int slotsAvailable = restaurant.getAvailableSlots() - order.getSlotsRequired();
                    restaurant.setAvailableSlots(slotsAvailable);
                    orderProcessingQueue.add(order);
                }
            }
        }
    }
}
