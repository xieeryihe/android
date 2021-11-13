package com.example.smtpclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smtpclient.tools.Info;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinear = findViewById(R.id.background);

        //初始化一些文件路径的东西
        Info.gPathname = getFilesDir().getAbsolutePath();
        Info.gJsonFile = new File(Info.gPathname ,"/"+Info.gUserJsonFileName);
        //创建用户信息文件，如果第一次创建文件，就要初始化该文件的用户信息
        createUserJsonFile();

        mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    private void createUserJsonFile(){
        Log.d("file path",Info.gPathname);
        if (!Info.gJsonFile.exists()){//如果文件不存在，就打开并创建，初始化
            Log.d("detect file","file doesn't exist");
            try {
                Log.d("Open file","should have created");
                initUserFile();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("create json","target file exist.");
        }
    }

    private void initUserFile() throws IOException, JSONException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(Info.gJsonFile), StandardCharsets.UTF_8);
        JSONObject jsonObject=new JSONObject();//创建JSONObject对象
        //JSONArray jsonArray = new JSONArray();
        //jsonArray.put(Info.default_authorizationCode);//注意：一个人的授权码可以有多个
        //jsonObject.put(Info.default_username,jsonArray);
        //修改了login逻辑之后，不用写入默认用户信息了
        osw.write(jsonObject.toString());
        osw.flush();
        osw.close();
        Log.d("init json","success");
    }
}
