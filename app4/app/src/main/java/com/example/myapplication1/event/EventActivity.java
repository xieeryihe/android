package com.example.myapplication1.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.R;

public class EventActivity extends AppCompatActivity {
    private Button mBtnListener;
    private Button mBtnCallback;
    private Button mBtnHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mBtnListener = findViewById(R.id.btn_listener);
        mBtnCallback = findViewById(R.id.btn_callback);
        mBtnHandler = findViewById(R.id.btn_handler);
        mBtnListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, ListenerActivity.class);
                startActivity(intent);
            }
        });
        mBtnCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, CallbackActivity.class);
                startActivity(intent);
            }
        });
        mBtnHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, HandlerActivity.class);
                startActivity(intent);
            }
        });
    }
}
