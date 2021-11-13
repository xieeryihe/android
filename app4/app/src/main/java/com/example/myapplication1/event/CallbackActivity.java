package com.example.myapplication1.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication1.R;
import com.example.myapplication1.widget.MyButton;

//基于回调的事件处理机制
public class CallbackActivity extends AppCompatActivity {
    private MyButton mBtnMyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        mBtnMyButton = findViewById(R.id.btn_my_button);
        mBtnMyButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("Listener","---onTouch---");
                        break;
                }
                return false;
            }
        });
        mBtnMyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Listener","---onClick---");
            }
        });
        mBtnMyButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("Listener","---onLongClick---");
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);//这个是重写方法最开始给的内容
        //下面是自己写的内容
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("CallbackActivity","---OnTouchEvent---");
                break;
        }
        return false;
    }
}
