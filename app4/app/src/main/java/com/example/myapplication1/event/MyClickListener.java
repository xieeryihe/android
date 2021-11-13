package com.example.myapplication1.event;

import android.app.Activity;
import android.view.View;

import com.example.myapplication1.util.ToastUtil;

public class MyClickListener implements View.OnClickListener {
    private Activity mActivity;
    public MyClickListener(Activity activity){//如果想要获取输入框内容，就再传入对应参数即可
        this.mActivity = activity;
    }

    @Override
    public void onClick(View view) {
        ToastUtil.showMsg(mActivity,"method4：click...");
    }
}
