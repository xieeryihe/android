package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.myapplication1.R;

public class CheckBoxActivity extends AppCompatActivity {

    //这里只针对自定义的3个选项：篮球、足球、乒乓球，来获取是否选中的信息。
    //突然想起来上学期做数据库pj的时候，做动态的复选框，钻研了好久
    //最后是通过后端传入复选框条目列表，前端通过jinja2的渲染，利用for循环，来动态产生对应条目
    //现在竟然在学最基础的固定数量复选框条目，并获取选择信息，一时间真是唏嘘不已啊。

    private CheckBox mCb5,mCb6,mCb7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        mCb5 = findViewById(R.id.cb_5);
        mCb6 = findViewById(R.id.cb_6);
        mCb7 = findViewById(R.id.cb_7);
        mCb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {//注意，这里换对象了，是CompoundButton
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(CheckBoxActivity.this,b?"5选中":"5未选中",Toast.LENGTH_SHORT).show();
            }
        });
        mCb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(CheckBoxActivity.this,b?"6选中":"6未选中",Toast.LENGTH_SHORT).show();
            }
        });
        mCb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(CheckBoxActivity.this,b?"7选中":"7未选中",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
