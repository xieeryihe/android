package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.activity_fragment.ActFraActivity;
import com.example.myapplication1.broadcast.BroadActivity1;
import com.example.myapplication1.data.DataStorageActivity;
import com.example.myapplication1.event.EventActivity;
import com.example.myapplication1.ui.UIActivity;

public class MainActivity extends AppCompatActivity {
    private Button mBtnUI;
    private Button mBtnActFra;
    private Button mBtnEvent;
    private Button mBtnData;
    private Button mBtnBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);//第三个参数requestCode随便写。
        //上面这行是获取外部写权限的，最后的code是用于接收用户相应（给了权限还是没给之类的），最后拿信息的时候要用这个code去对应

        mBtnUI = findViewById(R.id.btn_ui);
        mBtnActFra = findViewById(R.id.btn_act_fra);
        mBtnEvent = findViewById(R.id.btn_event);
        mBtnData = findViewById(R.id.btn_data);
        mBtnBroadcast = findViewById(R.id.btn_broadcast);
        OnClick onClick = new OnClick();
        mBtnUI.setOnClickListener(onClick);
        mBtnActFra.setOnClickListener(onClick);
        mBtnEvent.setOnClickListener(onClick);
        mBtnData.setOnClickListener(onClick);
        mBtnBroadcast.setOnClickListener(onClick);
    }
    class OnClick implements View.OnClickListener{
        Intent intent = null;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_ui:
                    intent = new Intent(MainActivity.this, UIActivity.class);
                    break;
                case R.id.btn_act_fra:
                    intent = new Intent(MainActivity.this, ActFraActivity.class);
                    break;
                case R.id.btn_event:
                    intent = new Intent(MainActivity.this, EventActivity.class);
                    break;
                case R.id.btn_data:
                    intent = new Intent(MainActivity.this, DataStorageActivity.class);
                    break;
                case R.id.btn_broadcast:
                    intent = new Intent(MainActivity.this, BroadActivity1.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
