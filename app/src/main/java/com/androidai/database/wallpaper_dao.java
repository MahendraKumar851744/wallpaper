package com.androidai.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface wallpaper_dao {
    @Query("SELECT * FROM wallpaper_model")
    List<wallpaper_model> getAllItems();

    @Insert
    void insert(wallpaper_model item);

    @Delete
    void delete(wallpaper_model item);

    @Update
    void update(wallpaper_model item);
}
