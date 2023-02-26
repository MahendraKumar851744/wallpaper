package com.androidai.wallpaper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidai.wallpaper.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Act_final extends AppCompatActivity {

    ImageView wallpaper;
    CardView cv_download,cv_apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_final);
        wallpaper = findViewById(R.id.wallpaper);
        cv_apply = findViewById(R.id.cv_apply);
        cv_download = findViewById(R.id.cv_download);
        Intent i = getIntent() ;
        String url = i.getStringExtra("url");
        Glide.with(this).load(url).into(wallpaper);

        cv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage(url,"wallpaper");
            }
        });
        cv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(Act_final.this);
                dialog.setContentView(R.layout.lay_select);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                LinearLayout home = dialog.findViewById(R.id.home);
                LinearLayout lock = dialog.findViewById(R.id.lock);
                LinearLayout both = dialog.findViewById(R.id.both);

                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SaveImageTask(1).execute(url);
                        dialog.dismiss();
                    }
                });
                lock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SaveImageTask(2).execute(url);
                        dialog.dismiss();
                    }
                });
                both.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new SaveImageTask(3).execute(url);
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }
    private void downloadImage(String url, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Downloading image");
        request.setDescription("Downloading image file...");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);
    }

    private class SaveImageTask extends AsyncTask<String, Void, Bitmap> {
        int id;
        public SaveImageTask(int id){
            this.id = id;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                try {
                    ContextWrapper cw = new ContextWrapper(Act_final.this);
                    File directory = cw.getDir("imageDir", MODE_PRIVATE);
                    File mypath = new File(directory, "image.jpg");


                    FileOutputStream fos = new FileOutputStream(mypath);

                    ( Act_final.this).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        }
                    });

                    fos.close();

                    Bitmap bitmap2 = BitmapFactory.decodeFile(mypath.getAbsolutePath());
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(Act_final.this);


                    try {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            int wallpaperOptions;
                            if(id==1){
                                wallpaperOptions = WallpaperManager.FLAG_SYSTEM;
                            }else if(id == 2){
                                wallpaperOptions = WallpaperManager.FLAG_LOCK;
                            }else{
                                wallpaperOptions = WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK;
                            }
                            wallpaperManager.setBitmap(bitmap2, null, true, wallpaperOptions);
                        }else{
                            wallpaperManager.setBitmap(bitmap2);
                        }
                        Toast.makeText(Act_final.this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(Act_final.this, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}