package com.example.myapplication1.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static Toast mToast;
    public static void showMsg(Context context, String msg){
        if(mToast == null){
            //非空情况下不再创建新Toast
            mToast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);

        }else {
            mToast.cancel();//取消排队，强行显示新的消息
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);//防止只显示第一个消息
        }
        mToast.show();
    }
}
