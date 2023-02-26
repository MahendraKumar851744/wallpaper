package com.androidai.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Convertors {
    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static ArrayList<String> toArrayList(String json) {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(json, type);
    }
}