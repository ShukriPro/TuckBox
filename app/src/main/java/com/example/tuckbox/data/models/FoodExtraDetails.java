package com.example.tuckbox.data.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Food.class, parentColumns = "food_ID", childColumns = "foodFood_ID"))
public class FoodExtraDetails {
    @PrimaryKey
    public int food_Details_ID;
    public String details_Name;
    public int foodFood_ID;
}
