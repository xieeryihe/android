package com.example.myapplication1.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;
import com.example.myapplication1.widget.MyButton;
//基于监听的事件处理机制
public class ListenerActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnEventTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);
        mBtnEventTest = findViewById(R.id.btn_event_test);
        //1.通过内部类实现如下
        mBtnEventTest.setOnClickListener(new OnClick());
        //2.匿名内部类不展示了，就是最开始用的那个
        //3.通过事件源所在类实现onClickListener接口，如下
        mBtnEventTest.setOnClickListener(ListenerActivity.this);
        //4.通过外部类
        //mBtnEventTest.setOnClickListener(new MyClickListener(ListenerActivity.this));
    }

    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_event_test:
                    Log.d("内部类","method1：click...");
                    ToastUtil.showMsg(ListenerActivity.this,"method1：click...");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_event_test:
                Log.d("事件源","method3：click...");
                ToastUtil.showMsg(ListenerActivity.this,"method3：click...");
                break;
        }
    }



    //方法5.通过layout文件的onClick属性（已弃用）
//    public void show(View view){
//        //注意：方法必须是public void，并且方法名要和onClick属性的值一致。建议传入View类型参数，容易区分是哪个页面的show属性
//        switch (view.getId()){
//            case R.id.btn_event_test:
//                ToastUtil.showMsg(ListenerActivity.this,"method5：click...");
//                break;
//        }
//    }
}
