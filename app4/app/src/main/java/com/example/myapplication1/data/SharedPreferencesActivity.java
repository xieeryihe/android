package com.example.myapplication1.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication1.R;

public class SharedPreferencesActivity extends AppCompatActivity {
    private EditText mEtName;
    private Button mBtnSave,mBtnShow;
    private TextView mTvContent;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        mEtName = findViewById(R.id.et_name);
        mBtnSave = findViewById(R.id.btn_save);
        mBtnShow = findViewById(R.id.btn_show);
        mTvContent = findViewById(R.id.tv_content);
        mSharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);//本来应该是this.getSharedPreferences(...)的，省略了this
        //getSharedPreferences方法有两个参数：String name, int mode，即文件名，模式。
        // 后者通常使用的都是MODE_PRIVATE，表示该文件只有当前本应用才能读写。还有一些其它的模式，自己看看
        //经典的是WORLD_READABLE，其他应用可读，由于安全性问题，官方已废弃，但是仍可用。更好的方法是用一些别的方法规避安全问题
        //APPEND（能用），表示追加，不会覆盖（一般很少使用），一般只需要知道MODE_PRIVATE就行了

        mEditor = mSharedPreferences.edit();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditor.putString("name",mEtName.getText().toString());
                //mEditor.commit();//用commit会报黄提示：用apply替换
                mEditor.apply();
                //commit是一个同步存储的过程，内存，磁盘一起存，存完手机才会干别的事。
                //但是apply会首先修改内存的内容，然后存到硬件上时，是一个异步的过程。
                //99%的情况下两者混用没关系，但是优先使用apply
            }
        });
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvContent.setText(mSharedPreferences.getString("name","对不起，没有之前保存的内容"));
                //第一个参数相当于key，第二个是缺省值，当为空的时候用缺省值
            }
        });
    }
}
