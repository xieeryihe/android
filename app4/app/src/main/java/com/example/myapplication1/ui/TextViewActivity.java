package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication1.R;

public class TextViewActivity extends AppCompatActivity {
    private TextView mTv4;//先定义变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        //然后绑定元素，使用
        mTv4 = findViewById(R.id.tv_4);//R指res下的xml文件中的id
        mTv4.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线，Paint的那个属性，字面意思就是穿过去的线
        mTv4.getPaint().setAntiAlias(true);//去除锯齿
    }
}
