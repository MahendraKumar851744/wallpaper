package com.androidai.wallpaper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.androidai.database.wallpaper_database;
import com.androidai.wallpaper.Adapter.Ad_Select_Template;
import com.androidai.wallpaper.Adapter.category_adapter;
import com.androidai.wallpaper.Models.category_model;
import com.androidai.wallpaper.R;

import java.util.ArrayList;

public class Act_Categories extends AppCompatActivity {
    RecyclerView rv_templates;
    ArrayList<category_model> arrayList;
    category_adapter ad_select_template;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_categories);
        rv_templates = findViewById(R.id.rv_templates);;
        rv_templates.setNestedScrollingEnabled(false);
        ArrayList<category_model> arrayList2 = getData();
        ad_select_template = new category_adapter(Act_Categories.this, arrayList2);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(Act_Categories.this,2);
        rv_templates.setLayoutManager(linearLayoutManager);
        rv_templates.setAdapter(ad_select_template);
    }
    public ArrayList<category_model> getData(){
        arrayList = new ArrayList<>();
        wallpaper_database wallpaper_db = wallpaper_database.getDbInstance(Act_Categories.this);
        for(int i=0;i<wallpaper_db.wallpaper_dao().getAllItems().size();i++){
            arrayList.add(new category_model(wallpaper_db.wallpaper_dao().getAllItems().get(i).name,
                    wallpaper_db.wallpaper_dao().getAllItems().get(i).items.get(0)));
        }
        return arrayList;
    }
}