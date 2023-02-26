package com.androidai.wallpaper.Models.carousel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface carousel_Dao {
    @Query("SELECT * FROM carousel_model")
    List<carousel_model> getAllUsers();

    @Insert
    void insertUser(carousel_model users);

    @Delete
    void delete(carousel_model user);

    @Update
    void update(carousel_model user);
}
