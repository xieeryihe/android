package com.example.smtpclient;

import static com.example.smtpclient.MainActivity.gAuthorizationCode;
import static com.example.smtpclient.MainActivity.gDraftsFileName;
import static com.example.smtpclient.MainActivity.gEmailsFileName;
import static com.example.smtpclient.MainActivity.gUsername;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailActivity extends AppCompatActivity {
    private Button mBtnCancel,mBtnSend,mBtnSave;
    private TextView mTextSender;
    private EditText mEditReceiver, mEditSubject, mEditContent;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        //不写下面两句会报错android.os.NetworkOnMainThreadException
        //因为在Android 4.0以上，网络连接不能放在主线程上，不然就会报错
        //一般请求量小的话，写下面两句，让网络请求可以在主线程运行。如果请求量大，就得放到线程中去进行
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mBtnCancel = findViewById(R.id.cancel_btn);
        mBtnSend = findViewById(R.id.send_btn);
        mBtnSave = findViewById(R.id.saveDraft_btn);
        mTextSender = findViewById(R.id.textview_from);
        mEditSubject = findViewById(R.id.edittext_subject);
        mEditReceiver = findViewById(R.id.edittext_to);
        mEditContent = findViewById(R.id.edittext_content);

        mTextSender.setText(gUsername);
        mEditReceiver.setText("653670584@qq.com \n 2640182777@qq.com");//暂时设置默认

        SSLClient sslClient = new SSLClient();//临时变量

        //传来的参数，如果没有传的参数，就打印log
        try {
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            SSLClient passClient = (SSLClient) bundle.getSerializable("SSLClient");
            //System.out.println(passClient.getSubject());
            mTextSender.setText(passClient.getFromAddress());
            mEditReceiver.setText(passClient.getToListString());
            mEditSubject.setText(passClient.getSubject());
            mEditContent.setText(passClient.getContent());
        } catch (NullPointerException e){
            Log.d("bundle","no data from draftbox...");
        }


        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = mTextSender.getText().toString();
                String receivers = mEditReceiver.getText().toString();
                String subject = mEditSubject.getText().toString();
                String content = mEditContent.getText().toString();

                sslClient.setUsername(gUsername);
                sslClient.setAuthorizationCode(gAuthorizationCode);
                sslClient.setToAddressList(formatAddrList(receivers));
                sslClient.setDate(getDateString());
                sslClient.setFromAddress(from);
                //注意，没有设置toAddress的逻辑
                sslClient.setSubject(subject);
                sslClient.setContent(content);

                saveEmail(sslClient,gDraftsFileName);

                Toast toast = Toast.makeText(EmailActivity.this,"存草稿成功",Toast.LENGTH_SHORT);
                toast.show();

                mEditSubject.setText("");
                mEditContent.setText("");
            }
        });
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String from = mTextSender.getText().toString();
                String receivers = mEditReceiver.getText().toString();
                String subject = mEditSubject.getText().toString();
                String content = mEditContent.getText().toString();
                Toast toast =Toast.makeText(getApplicationContext(),null,Toast.LENGTH_SHORT);
                if (receivers.equals("")){
                    toast.setText("必须输入receiver！");
                } else {
                    //设置登录拿到的信息
                    sslClient.setUsername(gUsername);
                    sslClient.setAuthorizationCode(gAuthorizationCode);
                    //设置收件人列表
                    sslClient.setToAddressList(formatAddrList(receivers));
                    //设置data段的MIME格式信息
                    sslClient.setDate(getDateString());
                    sslClient.setFromAddress(from);//其实from和gUsername是一样的，这里对应Email Data段的格式
                    //myClient.setToAddress("");//收件人在MyClient中就写好了，依据的是收件人list，这里不用设置。
                    sslClient.setSubject(subject);
                    sslClient.setContent(content);

                    try {
                        if(sslClient.runClient()){
                            toast.setText("发送成功！");
                            saveEmail(sslClient, gEmailsFileName);
                        }
                        else {
                            toast.setText("发送失败");
                        }
                    } catch (IOException| KeyManagementException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    mEditSubject.setText("");
                    mEditContent.setText("");
                }
                toast.show();
            }
        });

    }

    public ArrayList<String> formatAddrList(String receivers){
        //解析输入框的receivers字符串，获得字符串列表
        ArrayList<String> toList = new ArrayList<>();
        String pattern = "(\\d+)(@qq.com)";//创建正则匹配模式串
        Pattern r = Pattern.compile(pattern);// 创建 Pattern 对象
        Matcher m = r.matcher(receivers);// 现在创建 matcher 对象
        while (m.find()) {
            toList.add(m.group(0));
            //System.out.println("Found value: " + m.group(0) );//group(0)是一个特殊的组，总是表示匹配pattern的完整串
        }
        return toList;
    }

    public String getDateString(){
        StringBuilder timeAll = new StringBuilder();
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar calendar=Calendar.getInstance();
        String weekday = weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1];
        timeAll.append(weekday);
        timeAll.append(", ");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        timeAll.append(dateFormat.format(new Date()));
        return timeAll.toString();
    }


    //传入MyClient参数，保存邮件信息。其实要的是MyClient中的信息
    private void saveEmail(SSLClient client,String targetFileName){
        //targetFileName = "emailData.json";
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
            addObject.put("date",client.getDate());
            addObject.put("from",client.getFromAddress());
            JSONArray jsonToAddressList = new JSONArray(client.getToAddressList());
            addObject.put("to",jsonToAddressList);//注意这里是列表
            addObject.put("subject",client.getSubject());
            addObject.put("content",client.getContent());
            //添加完毕，装进数组
            Log.d("newObject",addObject.toString());
            jsonArray.put(addObject);
            //Log.d("current",jsonObject.toString());//OK
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonFile), StandardCharsets.UTF_8);
            osw.write(jsonObject.toString());
            osw.flush();//清空缓冲区，强制输出数据
            osw.close();//关闭输出流
            input.close();
        } catch (IOException | JSONException e ) {
            e.printStackTrace();
        }
    }


}
