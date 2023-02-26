package com.androidai.wallpaper.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.androidai.database.wallpaper_database;
import com.androidai.database.wallpaper_model;
import com.androidai.wallpaper.Models.carousel.carousel_database;
import com.androidai.wallpaper.Models.carousel.carousel_model;
import com.androidai.wallpaper.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Act_splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_act_splash);
        GetWallpapers();
        GetCarosuel();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Act_splash.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, 3500);

    }
    private void GetWallpapers(){
        wallpaper_database wallpaper_db = wallpaper_database.getDbInstance(Act_splash.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Wallpapers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wallpaper_db.clearAllTables();
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String childKey = childSnapshot.getKey();
                    ArrayList<String> arrayList = new ArrayList<String>();
                    for (DataSnapshot grandchildSnapshot : childSnapshot.getChildren()) {
                        String grandchildValue = Objects.requireNonNull(grandchildSnapshot.getValue()).toString();
                        arrayList.add(grandchildValue);
                    }
                    wallpaper_model model = new wallpaper_model(childKey,arrayList);
                    wallpaper_db.wallpaper_dao().insert(model);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG",error.getMessage());
            }
        });
    }
    private void GetCarosuel(){
        carousel_database carousel_db = carousel_database.getDbInstance(Act_splash.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Carousel");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carousel_db.clearAllTables();
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String value = Objects.requireNonNull(childSnapshot.getValue()).toString();
                    carousel_model carousel_model = new carousel_model(value);
                    carousel_db.userDao().insertUser(carousel_model);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG",error.getMessage());
            }
        });
    }


}