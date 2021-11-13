package com.example.myapplication1.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.R;

public class BroadActivity2 extends AppCompatActivity {
    private Button mBtnBoard21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad2);
        mBtnBoard21 = findViewById(R.id.btn_broad21);
        mBtnBoard21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.update");
                //new Intent()的参数可以传一个字符串，随便写，不过一般是.包名那种类型。是广播的行为名字
                LocalBroadcastManager.getInstance(BroadActivity2.this).sendBroadcast(intent);
            }
        });
    }
}
