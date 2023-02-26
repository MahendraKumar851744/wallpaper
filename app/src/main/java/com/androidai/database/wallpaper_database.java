package com.androidai.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {wallpaper_model.class}, version  = 1)
@TypeConverters(Convertors.class)
public abstract class wallpaper_database extends RoomDatabase {

    public abstract wallpaper_dao wallpaper_dao();

    private static  wallpaper_database INSTANCE;

    public static wallpaper_database getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), wallpaper_database.class, "WALLPAPER_DATABASE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}