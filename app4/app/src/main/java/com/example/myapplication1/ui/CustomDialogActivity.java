package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;
import com.example.myapplication1.widget.CustomDialog;

public class CustomDialogActivity extends AppCompatActivity {
    private Button mBtnDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);
        mBtnDialog = findViewById(R.id.btn_custom_dialog);
        mBtnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomDialog customDialog = new CustomDialog(CustomDialogActivity.this,R.style.CustomDialog);
                customDialog.setCancelable(false);
                customDialog.setTitle("提示").setMessage("确认删除此项？");
                customDialog.setCancel("取消", new CustomDialog.IOnCancelListener() {
                    @Override
                    public void OnCancel(CustomDialog dialog) {
                        ToastUtil.showMsg(CustomDialogActivity.this,"cancel...");
                        customDialog.dismiss();//点完取消或确认，让对话框消失
                    }
                });
                customDialog.setConfirm("确认", new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void OnConfirm(CustomDialog dialog) {
                        ToastUtil.showMsg(CustomDialogActivity.this,"confirm...");
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
            }
        });
    }
}
