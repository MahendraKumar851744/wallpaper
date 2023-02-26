package com.androidai.wallpaper.Models.carousel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {carousel_model.class}, version  = 1)
public abstract class carousel_database extends RoomDatabase {

    public abstract carousel_Dao userDao();

    private static carousel_database INSTANCE;

    public static carousel_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), carousel_database.class, "CarouselDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}