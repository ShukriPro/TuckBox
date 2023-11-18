package com.example.tuckbox.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.tuckbox.data.Converters;
import com.example.tuckbox.data.OrderFood;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
@Entity
public class Order {
    @PrimaryKey
    public int order_ID;
    public String address;
    public String city;
    public String fullName;
    public long timestamp;
    public String timeSlot;
    @TypeConverters(Converters.class)
    public List<OrderFood> foods;

    // Getters and setters
    // Add a getter method for the timestamp
    public long getTimestamp() {
        return timestamp;
    }

    // If you have a constructor or a method to create a new order, ensure the timestamp is set
    public Order() {
        // Other initializations...
        this.timestamp = System.currentTimeMillis();
    }

    // Similarly, if you're using setters to create an order, ensure to set the timestamp
    public void createOrder() {
        // Other code...
        this.timestamp = System.currentTimeMillis();
    }

    public int getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(int order_ID) {
        this.order_ID = order_ID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public List<OrderFood> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderFood> foods) {
        this.foods = foods;
    }
}
