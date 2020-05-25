package com.example.flupperassignment;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    //This will return the ArrayList<Color>
    @TypeConverter
    public static ArrayList<Color> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Color>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    //This will return the json of ArrayList<Color>
    @TypeConverter
    public static String fromArrayList(ArrayList<Color> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //This will return the ArrayList<String>
    @TypeConverter
    public static ArrayList<City> fromStringToArrayList(String value) {
        Type listType = new TypeToken<ArrayList<City>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    //This will return the json of ArrayList<String>
    @TypeConverter
    public static String fromArrayListToString(ArrayList<City> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
