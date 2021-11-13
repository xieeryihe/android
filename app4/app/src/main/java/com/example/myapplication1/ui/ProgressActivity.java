package com.example.myapplication1.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;

public class ProgressActivity extends AppCompatActivity {
    private ProgressBar mPb3;
    private Button mBtnStart,mBtnProgressDialog1,mBtnProgressDialog2;//ProgressDialog已被弃用，但是还可以用
    //将handler和Runnable定义为私有成员变量
    private Handler handler = new Handler(Looper.getMainLooper()){//Objects.requireNonNull(Looper.myLooper())也可以
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mPb3.getProgress()<100){
                handler.postDelayed(runnable,500);//延迟半秒跳一次
            }
            else{
                ToastUtil.showMsg(ProgressActivity.this,"加载完成");
            }
        }
    };
    private Runnable runnable = new Runnable() {//必须加final
        @Override
        public void run() {
            mPb3.setProgress(mPb3.getProgress()+5);
            handler.sendEmptyMessage(0);//设置完一次后，又回到消息处理，反复循环，直到走到handleMessage函数的else循环中停止
        }
    };

    //下面才是重写activity的onCreate函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        mPb3 = findViewById(R.id.pb3);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnProgressDialog1 = findViewById(R.id.btn_progress_dialog1);
        mBtnProgressDialog2 = findViewById(R.id.btn_progress_dialog2);
        mPb3.setProgress(30);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(0);
            }
        });
        mBtnProgressDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(ProgressActivity.this);
                progressDialog.setTitle("提示");
                progressDialog.setMessage("正在加载");
                //cancel是你人为取消，如按返回，点击dialog框外这种，的监听
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        ToastUtil.showMsg(ProgressActivity.this,"cancel...");
                    }
                });
                //dismiss是自然结束后的监听
                //progressDialog.setOnDismissListener();
                progressDialog.setCancelable(false);//不允许点击别的地方，消失对话框。连返回都没用了，只能强行关掉app
                progressDialog.show();
            }
        });
        mBtnProgressDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(ProgressActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("提示");
                progressDialog.setMessage("正在下载...");
                //progressDialog.setProgress(50);//根据具体的操作内容设置进度条，这里不多演示
                progressDialog.setCancelable(false);
                //虽然设置了不能返回退出，但是可以通过setButton，然后点击按钮退出对话框
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "好的", new DialogInterface.OnClickListener() {
                    //参数分别是按钮属性（积极，消极，中性），按钮文本内容，监听事件。
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //...
                    }
                });
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //...
                    }
                });
                progressDialog.show();
            }
        });
    }
}
