package com.weekday;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weekday.configs.RestaurantConfig;
import com.weekday.models.Order;
import com.weekday.models.Restaurant;
import com.weekday.services.RestaurantService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Application {
    Restaurant restaurant = new Restaurant(RestaurantConfig.MAX_SLOTS);

    private List<Order> inputOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            orders = Arrays.asList(mapper.readValue(new File("./src/main/java/com/weekday/configs/input.json"), Order[].class));
        } catch (Exception e) {
            System.out.println("File named input.json not found!");
        }

        return orders;
    }

    private void processOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Order list is empty!");
        } else {
            RestaurantService restaurantService = new RestaurantService();
            Map<Integer, Double> foodDeliveryTimeInMinutes = restaurantService.calculateFoodDeliveryTimes(orders, restaurant);
            printResult(foodDeliveryTimeInMinutes);
        }
    }

    private void printResult(Map<Integer, Double> foodDeliveryTimeInMinutes) {
        for (Map.Entry<Integer, Double> foodDeliveryTime : foodDeliveryTimeInMinutes.entrySet()) {
            if (foodDeliveryTime.getValue() >= 0) {
                System.out.println("Order " + foodDeliveryTime.getKey() + " will get delivered in " + foodDeliveryTime.getValue() + " minutes");
            } else if (foodDeliveryTime.getValue() == -1.0) {
                System.out.println("Order " + foodDeliveryTime.getKey() + " is denied because the restaurant cannot accommodate it.");
            } else if (foodDeliveryTime.getValue() == -2.0) {
                System.out.println("Order " + foodDeliveryTime.getKey() + " is denied because it will take too long to deliver it.");
            }
        }
    }

    public void run() {
        processOrders(inputOrders());
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
