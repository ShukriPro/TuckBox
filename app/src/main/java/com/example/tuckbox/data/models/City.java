package com.example.tuckbox.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {
    @PrimaryKey
    public int city_ID;
    public String cityName;
}
