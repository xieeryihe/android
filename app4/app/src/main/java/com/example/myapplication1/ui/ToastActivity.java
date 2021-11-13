package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;

public class ToastActivity extends AppCompatActivity {
    private Button mBtnToast1,mBtnToast2,mBtnToast3,mBtnToast4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        mBtnToast1 = findViewById(R.id.btn_toast_1);
        mBtnToast2 = findViewById(R.id.btn_toast_2);
        mBtnToast3 = findViewById(R.id.btn_toast_3);
        mBtnToast4 = findViewById(R.id.btn_toast_4);
        OnClick onClick = new OnClick();
        mBtnToast1.setOnClickListener(onClick);
        mBtnToast2.setOnClickListener(onClick);
        mBtnToast3.setOnClickListener(onClick);
        mBtnToast4.setOnClickListener(onClick);
    }
    class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_toast_1:
                    //Toast.makeText(ToastActivity.this,"Toast1",Toast.LENGTH_SHORT).show();//这个是之前我们用的
                    //Toast.makeText(getApplicationContext(),"Toast1",Toast.LENGTH_SHORT).show();这里换了一个方法
                    Toast toast = Toast.makeText(getApplicationContext(),null,Toast.LENGTH_SHORT);
                    toast.setText("Toast1");
                    toast.show();
                    break;
                case R.id.btn_toast_2:
                    //由于makeText的返回值就是Toast类型，所以这里直接创建Toast类型变量
                    //但是android 11中setGravity方法被禁止了，所以显示位置还是和默认一样
                    Toast toastCenter = Toast.makeText(getApplicationContext(),"居中Toast2",Toast.LENGTH_SHORT);//注意这里没有调用show方法
                    toastCenter.setGravity(Gravity.CENTER,0,0);
                    toastCenter.show();
                    break;
                case R.id.btn_toast_3:
                    Toast.makeText(ToastActivity.this,"setView方法已弃用，不再演示",Toast.LENGTH_SHORT).show();//这个是之前我们用的
                    //Toast toastCustom = new Toast(getApplicationContext());
                    //toast.setView();//setView方法官方已弃用，不再演示
                    //这个的核心目的就是将一个view作为一个消息提示出来
                    break;
                case R.id.btn_toast_4:
                    ToastUtil.showMsg(getApplicationContext(),"包装过的Toast");//自己封装的toast
                    break;
            }
        }
    }
}
