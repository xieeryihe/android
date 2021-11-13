package com.example.smtpclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.smtpclient.tools.Info;
import com.example.smtpclient.tools.LinearAdapter;

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
import java.util.ArrayList;


public class InboxActivity extends AppCompatActivity {
    private RecyclerView mRecyclerInbox;
    private ArrayList<SSLClient> sslClients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        setSSLClients();//先设置所有SSLClient列表，
        /*
        for (int i = 0 ;i<sslClients.size();i++){
            System.out.println(sslClients.get(i).getSubject());
        }
        */
        mRecyclerInbox = findViewById(R.id.recycler_inbox);

        mRecyclerInbox.setLayoutManager(new LinearLayoutManager(InboxActivity.this));

        //调用的setLayoutManager属性是布局管理器，传的参数是布局管理器实例，这里是线性布局管理器.

        mRecyclerInbox.setAdapter(new LinearAdapter(InboxActivity.this,sslClients));



    }
    private void setSSLClients(){
        //得到所有的邮件的全部信息，到myClients数组中
        String QQaccount = Info.gUsername.substring(0,Info.gUsername.indexOf('@'));//获取QQ号
        String dirPath = getFilesDir() + "/"+ QQaccount;
        String emailDataName = "emailData.json";
        File emailJsonFile = new File(dirPath,"/" + emailDataName);
        char[] buffer = new char[1024];
        StringBuilder allData = new StringBuilder();
        InputStreamReader input = null;
        int len;
        try {
            input = new InputStreamReader(new FileInputStream(emailJsonFile), StandardCharsets.UTF_8);
            while((len =input.read(buffer))>0){
                allData.append(buffer,0,len);
            }
            System.out.println(allData.toString());

            JSONObject jsonObject = new JSONObject(allData.substring(allData.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取
            Log.d("jsonObject",jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("emails");
            for (int i = 0 ;i<jsonArray.length();i++){
                SSLClient tempClient = new SSLClient();
                JSONObject tempJsonObject = null;
                JSONArray tempJsonArray = null;

                tempJsonObject = jsonArray.getJSONObject(i);
                tempJsonArray = tempJsonObject.getJSONArray("to");//注意，收件人是一个列表

                tempClient.setDate(tempJsonObject.getString("date"));
                tempClient.setFromAddress(tempJsonObject.getString("from"));
                //setToAddress
                tempClient.setSubject(tempJsonObject.getString("subject"));
                tempClient.setContent(tempJsonObject.getString("content"));

                //这里专门处理
                sslClients.add(tempClient);
            }

        } catch (IOException | JSONException e ) {
            Log.d("setSSLClients","error");
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}