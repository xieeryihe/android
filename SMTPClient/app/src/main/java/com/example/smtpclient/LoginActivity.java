package com.example.smtpclient;

import static com.example.smtpclient.MainActivity.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    private EditText mEditUsername,mEditPassword;//password是授权码
    private Button mButtonLogin;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditUsername = findViewById(R.id.edittext_username);
        mEditPassword = findViewById(R.id.edittext_password);
        mButtonLogin = findViewById(R.id.button_login);

        mEditUsername.setText(default_username);
        mEditPassword.setText(default_authorizationCode);//初始值设置为默认，不用自己麻烦地写来写去了

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEditUsername.getText().toString();
                String authorizationCode = mEditPassword.getText().toString();
                Intent intent;
                Toast toast = Toast.makeText(LoginActivity.this,null,Toast.LENGTH_SHORT);

                if (login(username,authorizationCode)){
                    //login没问题，就是
                    //如果一次就成功了，就
                    addUserInfo(username,authorizationCode);
                    createEmailJsonFile(username);
                    toast.setText("登录成功");
                    toast.show();
                    intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                } else {
                    if(tryLogin(username,authorizationCode)){
                        //如果尝试登录成功了，就再登录一次就行
                        login(username,authorizationCode);
                        addUserInfo(username,authorizationCode);
                        createEmailJsonFile(username);

                        toast.setText("登录成功");
                        toast.show();
                        intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);

                    } else { // 要是尝试登录失败，说明用户名和密码就是错的
                        toast.setText("登录失败，请检查用户名或授权码");
                        toast.show();
                        mEditUsername.setText("");//清空输入信息
                        mEditPassword.setText("");
                    }

                }

            }
        });
    }

    private void addUserInfo(String username, String auth) {
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("if_login",true);//登录成功
        edit.putString("username",username);
        edit.putString("authorizationCode",auth);
        edit.apply();
    }

    //登录函数，在User.json文件中查找目标信息，如果有的话，就成功，否则失败。进入后面的逻辑处理
    private boolean login(String username,String auth){
        int len;
        StringBuilder allData = new StringBuilder();
        InputStreamReader input = null;
        char[] buffer = new char[1024];
        boolean flag = false;//默认登录失败

        //解析读取到的文本，如果有匹配的，就一定能登录
        try {
            //开始读取Users.json文件信息
            input =new InputStreamReader(new FileInputStream(gUsersJsonFile), StandardCharsets.UTF_8);
            while((len =input.read(buffer))>0){
                allData.append(buffer,0,len);
            }

            //构造json对象
            JSONObject jsonObject = new JSONObject(allData.substring(allData.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取
            JSONArray jsonArray = jsonObject.getJSONArray(username);
            for (int i = 0;i<jsonArray.length();i++){
                if (Objects.equals(auth, jsonArray.getString(i))){
                    gSharedEditor.putString("username",username);
                    gSharedEditor.putString("authorizationCode",auth);
                    gUsername = username;
                    gAuthorizationCode = auth;
                    flag = true;//登录成功
                }
            }
        } catch (JSONException | IOException e){
            Log.d("登录阶段","登录失败");
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //如果走到这一步还没有return，就说明要么文件中没有该用户，要么该用户的该授权码没有记录。
        //需要登入腾讯邮箱测试
        return flag;
    }
    //调用Client的尝试登录函数，查看是否能通过QQ邮箱的验证
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean tryLogin(String user, String auth){

        //设置初始信息
        SSLClient sslClient = new SSLClient();
        sslClient.setUsername(user);
        sslClient.setAuthorizationCode(auth);

        //定义变量
        int len;
        char[] buffer = new char[1024];
        InputStreamReader input = null;
        OutputStreamWriter osw = null;
        StringBuilder allData = new StringBuilder();
        JSONArray jsonArray;

        //尝试登录，测试用户名和验证码的可行性
        try {
            if(!sslClient.tryLogin()){ //如果验证失败，就返回false
                Log.d("尝试登录阶段","验证失败");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("尝试登录阶段","验证成功");
        //System.out.println();//刷新缓冲区，因为一个
        //System.out.flush();
        try {
            input =new InputStreamReader(new FileInputStream(gUsersJsonFile), StandardCharsets.UTF_8);
            while((len =input.read(buffer))>0){
                allData.append(buffer,0,len);
            }
        } catch (IOException e){
            e.printStackTrace();
        }


        //否则验证成功，写入文件
        try {
            //否则，验证通过，写入User.json
            input =new InputStreamReader(new FileInputStream(gUsersJsonFile), StandardCharsets.UTF_8);
            osw = new OutputStreamWriter(new FileOutputStream(gUsersJsonFile), StandardCharsets.UTF_8);
            while((len =input.read(buffer))>0){
                allData.append(buffer,0,len);
            }

            JSONObject jsonObject = new JSONObject(allData.substring(allData.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取
            try {
                jsonArray = jsonObject.getJSONArray(user);
                Log.d("验证成功，用户存在","用户记入新的授权码");
                jsonArray.put(auth);

                //如果能找到该用户，那就说明该用户的该授权码没有记录
            } catch (JSONException e){
                //如果走到catch，就说明没有该用户对应的用户名
                Log.d("验证成功，用户不存在","记入新的用户名和授权码");
                JSONArray authArray = new JSONArray();
                authArray.put(auth);//创建新的授权码列表
                jsonObject.put(user,authArray);//加入新的用户信息
            } finally { //最终无论如何，都得写回数据一定要注意
                osw.write(jsonObject.toString());
                osw.flush();//清空缓冲区，强制输出数据
            }

        } catch (IOException | JSONException e){
            System.out.println(allData.toString());
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void createEmailJsonFile(String username){
        //创建存储已发送邮件和草稿箱的邮件
        //创建联系人
        String QQaccount = username.substring(0,username.indexOf('@'));//获取QQ号
        String dirPath = getFilesDir() + "/"+ QQaccount;
        File dir = new File(dirPath);
        File emailJsonFile = new File(dirPath,"/"+ gEmailsFileName);
        String emailsTag = gEmailsFileName.substring(0, gEmailsFileName.indexOf('.'));
        File draftsJsonFile = new File(dirPath,"/"+gDraftsFileName);
        String draftsTag = gDraftsFileName.substring(0,gDraftsFileName.indexOf('.'));
        File contactsJsonFile = new File(dirPath,"/"+gContactsFileName);
        String contactsTag = gContactsFileName.substring(0,gContactsFileName.indexOf('.'));

        if(!dir.exists()){//如果没有用户目录，就创建，并初始化
            dir.mkdir();
            try {
                Log.d("create email data","create");
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(emailJsonFile), StandardCharsets.UTF_8);
                JSONObject jsonObject=new JSONObject();//创建JSONObject对象
                JSONArray jsonArray = new JSONArray();
                jsonObject.put(emailsTag,jsonArray);
                osw.write(jsonObject.toString());
                osw.flush();
                osw.close();
            } catch (IOException | JSONException e) {
                Log.d("create data file","failed");
            }
            //-------------
            try {
                Log.d("create draft data","create");
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(draftsJsonFile), StandardCharsets.UTF_8);
                JSONObject jsonObject=new JSONObject();//创建JSONObject对象
                JSONArray jsonArray = new JSONArray();
                jsonObject.put(draftsTag,jsonArray);
                osw.write(jsonObject.toString());
                osw.flush();
                osw.close();
            } catch (IOException | JSONException e) {
                Log.d("create draft file","failed");
            }
            //-----------------
            try {
                Log.d("create contacts data","create");
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(contactsJsonFile), StandardCharsets.UTF_8);
                JSONObject jsonObject=new JSONObject();//创建JSONObject对象
                JSONArray jsonArray = new JSONArray();
                jsonObject.put(contactsTag,jsonArray);
                osw.write(jsonObject.toString());
                osw.flush();
                osw.close();
            } catch (IOException | JSONException e) {
                Log.d("create draft file","failed");
            }




        }else{
            Log.d("dir exists","don't touch anything");
        }
        //有该目录就不管了。
    }

}

