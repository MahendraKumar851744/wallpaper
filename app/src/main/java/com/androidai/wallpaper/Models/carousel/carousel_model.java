package com.androidai.wallpaper.Models.carousel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class carousel_model {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "carousel_item")
    public String item;

    public carousel_model(String item) {
        this.item = item;
    }
}
