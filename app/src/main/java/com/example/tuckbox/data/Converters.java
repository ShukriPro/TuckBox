package com.example.tuckbox.data;

import androidx.room.TypeConverter;

import com.example.tuckbox.data.models.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<OrderFood> stringToOrderFoodList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<OrderFood>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String orderFoodListToString(List<OrderFood> orderFoods) {
        return new Gson().toJson(orderFoods);
    }
}
