package com.example.myapplication1.event;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.myapplication1.R;
import com.example.myapplication1.ui.ButtonActivity;
import com.example.myapplication1.util.ToastUtil;
public class HandlerActivity extends AppCompatActivity {
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
//        mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(HandlerActivity.this, ButtonActivity.class);//实现一个页面跳转
//                startActivity(intent);
//            }
//        },3000);//两个参数，分别为Runnable，delayMillis（毫秒）
//        //效果：三秒钟之后，执行Runnable，跳转到ButtonActivity界面。
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        ToastUtil.showMsg(HandlerActivity.this,"线程通信成功");
                        break;
                }
            }
        };
        //mHandler里面的是主线程，下面的是新线程。在新线程里面发送一个消息，然后在主线程中处理。
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }.start();
    }
}
