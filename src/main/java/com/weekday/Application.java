package com.weekday;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weekday.models.Order;
import com.weekday.services.RestaurantService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application {

    private List<Order> inputOrders() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("./src/main/java/com/weekday/configs/input.json"), new TypeReference<>(){});
    }

    private void processOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Order list is empty!");
            return;
        }

        RestaurantService restaurantService = new RestaurantService();
        restaurantService.calculateFoodDeliveryTimes(orders);
    }

    public void run() {
        try {
            List<Order> orders = inputOrders();
            processOrders(orders);
        } catch(IOException e) {
            System.out.println("Incorrect data format in input file!");
            System.out.println("To fix the error, add file named \"input.json\" in configs directory with the correct format.");
        }
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
