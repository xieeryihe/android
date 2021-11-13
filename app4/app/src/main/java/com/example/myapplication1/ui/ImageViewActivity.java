package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication1.R;

public class ImageViewActivity extends AppCompatActivity {
    private ImageView mIv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        mIv2 = findViewById(R.id.iv_2);
        Glide.with(this).load("https://img2.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp").into(mIv2);


    }
}
