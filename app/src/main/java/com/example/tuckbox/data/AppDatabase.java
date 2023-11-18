package com.example.tuckbox.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tuckbox.data.models.City;
import com.example.tuckbox.data.models.DeliveryAddress;
import com.example.tuckbox.data.models.Food;
import com.example.tuckbox.data.models.FoodExtraDetails;
import com.example.tuckbox.data.models.Order;
import com.example.tuckbox.data.models.TimeSlot;
import com.example.tuckbox.data.models.User;

@Database(entities = {Food.class, FoodExtraDetails.class, User.class, City.class, TimeSlot.class, Order.class, DeliveryAddress.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}