package com.example.myapplication1.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication1.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    //可以直接实现OnClickListener接口，然后重写OnClick方法
    //CustomDialog类也可以通过像上一节的ProgressDialog一样，通过builder来实现

    private TextView mTvTitle,mTvMessage,mTvCancel,mTvConfirm;
    private String title,message,cancel,confirm;
    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;

    //--------------------
    public CustomDialog(@NonNull Context context) {
        super(context);
    }
    //第二个构造方法，表示可以传入一个style，在values文件夹的styles文件里面，为一个双标签，里面包含了很多属性
    //意思是可以通过styles文件来设置样式
    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //--------------------
    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public CustomDialog setCancel(String cancel,IOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener = listener;
        return this;
    }

    public CustomDialog setConfirm(String confirm,IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener = listener;
        return this;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_dialog);
        //设置消息框宽度
        Window window = getWindow();
        WindowManager windowManager = window.getWindowManager();
        window.setBackgroundDrawableResource(R.drawable.bg_custom_dialog);//设置对话框圆角属性（在bg_custom_dialog文件中）
        //如果不在CustomDialogActivity中调用CustomDialog时传入bg_custom_dialog，就要在这里设置window的背景圆角属性
        //如果仅在CustomDialogActivity中传入bg_custom_dialog，就会出现圆角对话框，被切去的部分是自定义的背景颜色（在styles文件中说明了）
        //如果既在这里设置window属性，又传入bg_custom_dialog。或者单纯设置window的bg_custom_dialog属性，能正常显示。
        Display d = windowManager.getDefaultDisplay();//getDefaultDisplay已被弃用
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x*0.8);//设置dialog的宽度为当前手机屏幕宽度的80%
        getWindow().setAttributes(p);



        mTvTitle = findViewById(R.id.tv_title);
        mTvMessage = findViewById(R.id.tv_message);
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvConfirm = findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(title)){
            mTvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(message)){
            mTvMessage.setText(message);
        }
        if (!TextUtils.isEmpty(cancel)){
            mTvCancel.setText(cancel);
        }
        if (!TextUtils.isEmpty(confirm)){
            mTvConfirm.setText(confirm);
        }
        mTvCancel.setOnClickListener(this);//可以直接写this，因为当前类已经实现了OnClickListener接口
        mTvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                if (cancelListener != null){
                    cancelListener.OnCancel(this);
                }
                break;
            case R.id.tv_confirm:
                if (confirmListener != null){
                    confirmListener.OnConfirm(this);
                }
                break;
        }
    }

    //写一下点击取消和确定的事件监听接口
    public interface IOnCancelListener{
        void OnCancel(CustomDialog dialog);

    }
    public interface IOnConfirmListener{
        void OnConfirm(CustomDialog dialog);
    }
}
