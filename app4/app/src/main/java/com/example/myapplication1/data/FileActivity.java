package com.example.myapplication1.data;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//文件存储，直接赋值的SharedPreferencesActivity的，删掉了之前的注释
public class FileActivity extends AppCompatActivity {
    private EditText mEtName;
    private Button mBtnSave;
    private Button mBtnShow;
    private TextView mTvContent;
    private final String mFileName = "test.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        mEtName = findViewById(R.id.et_name);
        mBtnSave = findViewById(R.id.btn_save);
        mBtnShow = findViewById(R.id.btn_show);
        mTvContent = findViewById(R.id.tv_content);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(mEtName.getText().toString());
                ToastUtil.showMsg(FileActivity.this,"内容已保存");
            }
        });
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvContent.setText(read());
                ToastUtil.showMsg(FileActivity.this,"内容已显示");
            }
        });
    }

    //存储数据
    private void save(String content){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(mFileName,MODE_PRIVATE);//参数分别为：文件名，模式
            //注意，这个方法只能在安卓应用中用，不能在java应用中用，因为openFileOutput必须在有context的情况下使用，
            //一般这个context默认就是当前activity.this文件
            fileOutputStream.write(content.getBytes());//因为传入的参数是必须是byte[] 的字节文件

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //读取数据
    private String read(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(mFileName);
            byte[] buff = new byte[1024];
            StringBuilder sb = new StringBuilder();//用以实现字符串的拼接
            int len;
            while ((len = fileInputStream.read(buff))!=-1){
                sb.append(new String(buff,0,len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
