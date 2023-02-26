package com.androidai.wallpaper.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidai.database.wallpaper_model;
import com.androidai.wallpaper.Activities.Act_Trending;
import com.androidai.wallpaper.Activities.Act_final;
import com.androidai.wallpaper.Models.category_model;
import com.androidai.wallpaper.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class category_adapter extends RecyclerView.Adapter<category_adapter.CategoryViewHolder> {

    Context context;
    ArrayList<category_model> template_itemArrayList;

    public category_adapter(Context context, ArrayList<category_model> template_itemArrayList) {
        this.context = context;
        this.template_itemArrayList = template_itemArrayList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(template_itemArrayList.get(position).url).into(holder.iv_template);
        holder.category_name.setText(template_itemArrayList.get(position).name);
        holder.iv_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Act_Trending.class);
                i.putExtra("name",template_itemArrayList.get(position).name);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return template_itemArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_template;
        TextView category_name;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_template = itemView.findViewById(R.id.iv_template);
            category_name = itemView.findViewById(R.id.category_name);
        }
    }


}