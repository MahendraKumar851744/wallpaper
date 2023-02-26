package com.androidai.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class wallpaper_model {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "SKILL")
    public String name;


    public ArrayList<String> items;

    public wallpaper_model(String name,ArrayList<String> items) {
        this.name = name;
        this.items = items;
    }
}
