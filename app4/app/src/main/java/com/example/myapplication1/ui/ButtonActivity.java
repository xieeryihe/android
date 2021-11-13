package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.R;

public class ButtonActivity extends AppCompatActivity {
    private Button mBtn3;//m代表成员变量，好像是c++里的命名法
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        mBtn3 = findViewById(R.id.btn_3);
        mBtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(ButtonActivity.this,"点击按钮3",Toast.LENGTH_SHORT).show();
            }//上面一行要标明是ButtonActivity.this，不然匿名内部类会是OnClickListener
        });//注意，对按钮3设置了点击事件，就不用在activity_button的xml文件中设置android:onClick="showToast"属性了
        mTv1 = findViewById(R.id.tv_1);
        mTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ButtonActivity.this,"textview1被点击了",Toast.LENGTH_SHORT).show();
                //注意，在activity_button和activity_text_view里面都有id：tv_1，但是由于前者相当于绑定的是本文件，所以不会出现歧义
            }
        });
    }

    public void showToast(View view){
        Toast.makeText(this,"点击按钮4",Toast.LENGTH_SHORT).show();//参数this指在当前活动，即ButtonActivity活动界面产生消息
        //LENGTH_SHORT是一个常量定义为0，表示1秒，如果是LENGTH_LONG的话就是1，表示2秒
    }
}

