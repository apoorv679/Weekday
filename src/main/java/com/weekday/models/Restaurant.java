package com.weekday.models;

public class Restaurant {
    private int maxSlots;
    private int availableSlots;

    public Restaurant() {
        maxSlots = 0;
        availableSlots = 0;
    }

    public Restaurant(int slots) {
        this();
        this.maxSlots = slots;
        this.availableSlots = slots;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public void setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "maxSlots=" + maxSlots +
                ", availableSlots=" + availableSlots +
                '}';
    }
}
