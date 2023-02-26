package com.androidai.wallpaper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidai.database.wallpaper_database;
import com.androidai.database.wallpaper_model;
import com.androidai.wallpaper.Adapter.Ad_Select_Template;
import com.androidai.wallpaper.Models.carousel.carousel_database;
import com.androidai.wallpaper.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_templates;
    ArrayList<String> arrayList;
    Ad_Select_Template ad_select_template;
    BottomNavigationView bottomNavigationView;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heading = findViewById(R.id.heading);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        carousel();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        heading.setText("Recently Added");
                        setData("recent");
                        return true;
                    case R.id.categories:
                        startActivity(new Intent(MainActivity.this,Act_Categories.class));
                        return false;

                    case R.id.trending:
                        heading.setText("Trending");
                        setData("Trending");
                        return true;
                    case R.id.random:
                        heading.setText("Random");
                        setData("random");
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);




    }
    private void setData(String data){
        rv_templates = findViewById(R.id.rv_templates);;
        rv_templates.setNestedScrollingEnabled(false);
        ArrayList<String> arrayList2 = getData(data);
        ad_select_template = new Ad_Select_Template(MainActivity.this, arrayList2);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(MainActivity.this,3);
        rv_templates.setLayoutManager(linearLayoutManager);
        rv_templates.setAdapter(ad_select_template);

    }

    private ArrayList<String> getData(String data){
        arrayList = new ArrayList<>();
        wallpaper_database wallpaper_db = wallpaper_database.getDbInstance(MainActivity.this);
        for(int i=0;i<wallpaper_db.wallpaper_dao().getAllItems().size();i++){
            if(wallpaper_db.wallpaper_dao().getAllItems().get(i).name.equalsIgnoreCase(data)) {
                if (wallpaper_db.wallpaper_dao().getAllItems().get(i).items != null) {
                    arrayList.addAll(wallpaper_db.wallpaper_dao().getAllItems().get(i).items);
                    break;
                }
            }
        }
        return arrayList;
    }
    private void carousel(){
        ImageSlider imageSlider = findViewById(R.id.image_slider);

        List<SlideModel> slideModels = new ArrayList<>();
        carousel_database carousel_db = carousel_database.getDbInstance(MainActivity.this);
        for(int i=0;i<carousel_db.userDao().getAllUsers().size();i++){
            String s = getData(carousel_db.userDao().getAllUsers().get(i).item).get(0);
            slideModels.add(new SlideModel(s, ScaleTypes.CENTER_CROP));
        }
        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                startActivity(new Intent(MainActivity.this,Act_Categories.class));
            }
        });
    }
}