package com.androidai.wallpaper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.androidai.database.wallpaper_database;
import com.androidai.wallpaper.Adapter.Ad_Select_Template;
import com.androidai.wallpaper.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Act_Trending extends AppCompatActivity {
    RecyclerView rv_templates;
    ArrayList<String> arrayList;
    Ad_Select_Template ad_select_template;
    BottomNavigationView bottomNavigationView;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_trending);
        heading = findViewById(R.id.heading);
        String name = getIntent().getStringExtra("name");
        heading.setText(name);
        setData(name);
    }
    private void setData(String data){
        rv_templates = findViewById(R.id.rv_templates);;
        rv_templates.setNestedScrollingEnabled(false);
        ArrayList<String> arrayList2 = getData(data);
        ad_select_template = new Ad_Select_Template(Act_Trending.this, arrayList2);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(Act_Trending.this,3);
        rv_templates.setLayoutManager(linearLayoutManager);
        rv_templates.setAdapter(ad_select_template);

    }
    private ArrayList<String> getData(String data){
        arrayList = new ArrayList<>();
        wallpaper_database wallpaper_db = wallpaper_database.getDbInstance(Act_Trending.this);
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
}