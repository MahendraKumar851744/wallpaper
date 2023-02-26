package com.androidai.wallpaper.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidai.wallpaper.Activities.Act_final;
import com.androidai.wallpaper.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Ad_Select_Template extends RecyclerView.Adapter<Ad_Select_Template.CategoryViewHolder> {

    Context context;
    ArrayList<String> template_itemArrayList;

    public Ad_Select_Template(Context context, ArrayList<String> template_itemArrayList) {
        this.context = context;
        this.template_itemArrayList = template_itemArrayList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(template_itemArrayList.get(position)).into(holder.iv_template);
        holder.iv_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i = new Intent(context, Act_final.class);
              i.putExtra("url",template_itemArrayList.get(position));
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
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_template = itemView.findViewById(R.id.iv_template);
        }
    }


}
