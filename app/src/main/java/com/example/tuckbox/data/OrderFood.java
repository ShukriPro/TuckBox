package com.example.tuckbox.data;

public class OrderFood {
    public String foodName;
    public int quantity;

    // Public default constructor is required for Firebase Realtime Database
    public OrderFood() {}

    // Constructor for initializing the fields
    public OrderFood(String foodName, int quantity) {
        this.foodName = foodName;
        this.quantity = quantity;
    }

    // Getter and setter methods
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

