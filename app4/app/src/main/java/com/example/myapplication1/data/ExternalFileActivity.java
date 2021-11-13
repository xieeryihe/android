package com.example.myapplication1.data;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

//文件存储，直接赋值的SharedPreferencesActivity的，删掉了之前的注释
public class ExternalFileActivity extends AppCompatActivity {
    private EditText mEtName;
    private Button mBtnSave;
    private Button mBtnShow;
    private TextView mTvContent;
    private final String mFileName = "external.txt";
    private final String mDirName = "testDir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_file);
        mEtName = findViewById(R.id.et_name);
        mBtnSave = findViewById(R.id.btn_save);
        mBtnShow = findViewById(R.id.btn_show);
        mTvContent = findViewById(R.id.tv_content);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(mEtName.getText().toString());
                ToastUtil.showMsg(ExternalFileActivity.this,"内容已保存");
            }
        });
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvContent.setText(read());
                ToastUtil.showMsg(ExternalFileActivity.this,"内容已显示");
            }
        });
    }

    //存储数据
    private void save(String content){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(mFileName,MODE_PRIVATE);//内部存储的代码
            //File dir1 = new File(Environment.getExternalStorageDirectory(),"testDir");//会显示方法已弃用，用下面一行的
            //文件夹
            File dir = new File(getExternalFilesDir(null),mDirName);//第二个参数是文件夹名称
            if (!dir.exists()){
                boolean b = dir.mkdirs();//mkdir只新建一层文件夹，mkdirs可以递归建立多层
                Log.d("创建文件夹", String.valueOf(b));//如果不用变量接收返回值的话，会报黄
            }
            //文件
            File file = new File(dir,mFileName);
            if (!file.exists()){
                boolean b = file.createNewFile();
                Log.d("创建文件", String.valueOf(b));
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());

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
            //fileInputStream = openFileInput(mFileName);//内部存储
            File file = new File(Objects.requireNonNull(getExternalFilesDir(null)).getAbsolutePath()+File.separator+mDirName,mFileName);
            //File.separator就是文件路径中的斜杠"/"，
            fileInputStream = new FileInputStream(file);
            byte[] buff = new byte[1024];
            StringBuilder sb = new StringBuilder();//用以实现字符串的拼接
            int len;
            while ((len = fileInputStream.read(buff))!=-1){
                sb.append(new String(buff,0,len));
            }
            Log.d("External","读取文件");
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
