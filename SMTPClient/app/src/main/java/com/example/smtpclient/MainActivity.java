package com.example.smtpclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinear;
    public static SharedPreferences gShared;
    public static SharedPreferences.Editor gSharedEditor;
    public static String gUsername;
    public static String gAuthorizationCode;
    public static String gUsersJsonFileName;
    public static File gUsersJsonFile;
    public static SSLClient gSSLClient;
    public static String default_username;
    public static String default_authorizationCode;

    private void init(){
        //成员变量不能放到构造函数里，因为getSharedPreferences必须等到onCreate方法执行完之后才行
        gShared = getSharedPreferences("user_info", MODE_PRIVATE);
        gSharedEditor = gShared.edit();
        gUsersJsonFileName = "Users.json";
        gUsersJsonFile = new File(getFilesDir().getAbsolutePath(),"/"+gUsersJsonFileName);
        gUsername = gShared.getString("username","");//最开始是获取不到的，用空字符串即可
        gAuthorizationCode = gShared.getString("authorizationCode","");
        default_username = "2640182777@qq.com";
        default_authorizationCode = "fjbzxoivfccxdjbc";

        //设置共享文件夹内容
        gSharedEditor.putString("usersJsonFileName", gUsersJsonFileName);//所有用户信息的文件
        gSharedEditor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//初始化类的基本信息
        createUserJsonFile();
        mLinear = findViewById(R.id.background);

        //初始化一些信息


        mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean if_login = gShared.getBoolean("if_login",false);//如果没设置该属性，就是false
                Log.d("if_login", String.valueOf(if_login));
                Intent intent;
                if(if_login){//如果已经登录过了，或者没有记录登录信息
                    intent = new Intent(MainActivity.this,HomeActivity.class);
                }else {
                    intent = new Intent(MainActivity.this,LoginActivity.class);
                }
                startActivity(intent);
            }
        });
    }


    private void createUserJsonFile(){
        if (!gUsersJsonFile.exists()){//如果文件不存在，就打开并创建，初始化
            Log.d("detect file","file doesn't exist");
            try {
                Log.d("Open file","should have created");
                initUsersFile();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("create json","target file exist.");
        }
    }

    private void initUsersFile() throws IOException, JSONException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(gUsersJsonFile), StandardCharsets.UTF_8);
        JSONObject jsonObject=new JSONObject();//创建JSONObject对象
        osw.write(jsonObject.toString());
        osw.flush();
        osw.close();
        Log.d("init json","success");
    }
}
