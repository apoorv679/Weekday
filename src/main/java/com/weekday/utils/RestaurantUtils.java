package com.weekday.utils;

import com.weekday.models.enums.OrderStatusEnum;

public class RestaurantUtils {
    public static void printResultForOrder(OrderStatusEnum status, int orderId) {
        printResultForOrder(status, orderId, 0);
    }

    public static void printResultForOrder(OrderStatusEnum status, int orderId, double deliveryTime) {
        switch(status) {
            case ORDER_SUCCESS:
                System.out.println("Order " + orderId + " will get delivered in " + deliveryTime + " minutes");
                break;
            case ORDER_FAILURE_SLOTS_REQUIRED:
                System.out.println("Order " + orderId + " is denied because the restaurant cannot accommodate it.");
                break;

            case ORDER_FAILURE_TIME_REQUIRED:
                System.out.println("Order " + orderId + " is denied because it will take too long to get delivered.");
                break;
            default:
                break;
        }
    }
}
