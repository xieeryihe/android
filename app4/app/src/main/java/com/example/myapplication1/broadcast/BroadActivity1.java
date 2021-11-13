package com.example.myapplication1.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication1.R;

public class BroadActivity1 extends AppCompatActivity {
    private Button mBtnBoard11;
    private TextView mTvBoard11;
    private MyBroadcast mBroadcast;//声明一个自己的广播
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad1);
        mBtnBoard11 = findViewById(R.id.btn_broad11);
        mTvBoard11 = findViewById(R.id.tv_broad11);
        mBtnBoard11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BroadActivity1.this, BroadActivity2.class);
                startActivity(intent);
            }
        });
        mBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter();//intent过滤器
        intentFilter.addAction("com.example.update");//接收哪些广播
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcast,intentFilter);
    }
    private class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "com.example.update":
                    mTvBoard11.setText("123");
                    break;
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcast);
    }
}
