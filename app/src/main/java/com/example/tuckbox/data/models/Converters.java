package com.example.tuckbox.data.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Converters {
    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<String> toList(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, new TypeToken<List<String>>() {
        }.getType());
    }
}
