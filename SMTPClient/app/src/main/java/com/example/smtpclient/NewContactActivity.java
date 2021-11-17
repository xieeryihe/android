package com.example.smtpclient;

import static com.example.smtpclient.MainActivity.gContactsFileName;
import static com.example.smtpclient.MainActivity.gUsername;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smtpclient.tools.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class NewContactActivity extends AppCompatActivity {
    private Button mButtonNewContact;
    private EditText mEditNickname,mEditUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        mButtonNewContact = findViewById(R.id.button_create_contact);
        mEditNickname = findViewById(R.id.edittext_contact_nickname);
        mEditUsername = findViewById(R.id.edittext_contact_username);
        mButtonNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = mEditNickname.getText().toString();
                String username = mEditUsername.getText().toString();
                Contact contact = new Contact();
                contact.setNickname(nickname);
                contact.setUsername(username);
                Intent intent = new Intent(NewContactActivity.this,EmailActivity.class);
                startActivity(intent);
                //saveContact(contact,gContactsFileName);
            }
        });
    }
    /*
    public void saveContact(Contact contact,String targetFileName){
        String QQaccount = gUsername.substring(0,gUsername.indexOf('@'));//获取QQ号
        String dirPath = getFilesDir() + "/"+ QQaccount;
        String tagName = targetFileName.substring(0,targetFileName.indexOf('.'));//截取文件名作为标签索引
        File jsonFile = new File(dirPath,"/" + targetFileName);
        char[] buffer = new char[1024];
        StringBuilder allData = new StringBuilder();
        InputStreamReader input;
        int len;
        try {
            input = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            while((len =input.read(buffer))>0){
                allData.append(buffer,0,len);
            }
            JSONObject jsonObject = new JSONObject(allData.substring(allData.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取
            Log.d("jsonObject",jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray(tagName);
            JSONObject addObject = new JSONObject();
            //添加相关信息
            addObject.put("nickname",contact.getNickname());
            addObject.put("username",contact.getUsername());
            //添加完毕，装进数组
            jsonArray.put(addObject);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonFile), StandardCharsets.UTF_8);
            osw.write(jsonObject.toString());
            osw.flush();//清空缓冲区，强制输出数据
            osw.close();//关闭输出流
            input.close();
        } catch (IOException | JSONException e ) {
            e.printStackTrace();
        }
    }
    */

}