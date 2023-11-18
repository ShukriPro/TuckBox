package com.example.tuckbox.data.models;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM Food WHERE food_ID = :foodId")
    Food getFoodById(int foodId);
}