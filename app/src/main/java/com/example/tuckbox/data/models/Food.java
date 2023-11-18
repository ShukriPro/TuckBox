package com.example.tuckbox.data.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import java.util.List;

import com.example.tuckbox.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.room.TypeConverter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import java.util.List;

@Entity
@TypeConverters(Converters.class)
public class Food {
    @PrimaryKey
    public int food_ID;
    public String name;
    public String img;
    public String extra_choice_type;
    public List<String> extra_choice_options;
    public int quantity;

    // Getter and Setter for quantity

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExtra_choice_type(String extra_choice_type) {
        this.extra_choice_type = extra_choice_type;
    }

    public void setExtra_choice_options(List<String> extra_choice_options) {
        this.extra_choice_options = extra_choice_options;
    }

    public List<String> getExtra_choice_options() {
        return extra_choice_options;
    }

    public String getFoodName() {
        return name;
    }



}

