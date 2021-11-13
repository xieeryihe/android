package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication1.R;

public class EditTextActivity extends AppCompatActivity {
    private Button mBtnLogin;
    private EditText mEtUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(EditTextActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            }
        });
        mEtUserName = findViewById(R.id.et_1);
        mEtUserName.addTextChangedListener(new TextWatcher() {//写到TextWatcher按tab会自动补全下面三个函数的模板
            //这个就是监视文本变化的，就不是用setOn打头的函数了
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            //三个函数主要用的的是on这个
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //charSequence就是当前输入框的内容，可以打印到控制台
                Log.d("username:",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
