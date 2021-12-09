package com.weekday;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weekday.models.Order;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<Order> orders = new ArrayList<>();
        try {
            orders = mapper.readValue(new File("./src/main/java/com/weekday/static/input.json"), new TypeReference<>() {
            });
        } catch (Exception e) {
            System.out.println("No such file found!");
        }

        if (orders.isEmpty()) {
            System.out.println("empty order list");
        }
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
