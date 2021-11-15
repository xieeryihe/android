package com.example.smtpclient;

import static com.example.smtpclient.MainActivity.gEmailsFileName;
import static com.example.smtpclient.MainActivity.gFilesPath;
import static com.example.smtpclient.MainActivity.gUsername;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.smtpclient.tools.BoxLinearAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class SentboxActivity extends AppCompatActivity {
    private RecyclerView mRecyclerSentbox;
    private ArrayList<SSLClient> mSSLClients;
    public SentboxActivity(){
        mSSLClients = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentbox);

        mSSLClients = getSSLClients(gEmailsFileName);//先设置所有SSLClient列表，
        mRecyclerSentbox = findViewById(R.id.recycler_sentbox);
        mRecyclerSentbox.setLayoutManager(new LinearLayoutManager(SentboxActivity.this));
        //调用的setLayoutManager属性是布局管理器，传的参数是布局管理器实例，这里是线性布局管理器.
        mRecyclerSentbox.setAdapter(new SentboxLinearAdapter(SentboxActivity.this, mSSLClients));


    }
    public ArrayList<SSLClient> getSSLClients(String targetFileName){
        //得到所有的邮件的全部信息，到myClients数组中
        ArrayList<SSLClient> tempSSLClients = new ArrayList<>();
        String QQaccount = gUsername.substring(0,gUsername.indexOf('@'));//获取QQ号
        String dirPath = gFilesPath + "/"+ QQaccount;
        File jsonFile = new File(dirPath,"/" + targetFileName);
        String tagName = targetFileName.substring(0,targetFileName.indexOf("."));
        char[] buffer = new char[1024];
        StringBuilder allData = new StringBuilder();
        InputStreamReader input = null;
        int len;
        try {
            input = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            while((len =input.read(buffer))>0){
                allData.append(buffer,0,len);
            }

            //System.out.println(allData.toString());

            JSONObject jsonObject = new JSONObject(allData.substring(allData.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取
            Log.d("jsonObject",jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray(tagName);
            for (int i = 0 ;i<jsonArray.length();i++){
                SSLClient tempClient = new SSLClient();
                JSONObject tempJsonObject;
                JSONArray tempJsonArray;
                ArrayList<String> tempToList = new ArrayList<>();

                String toString = "";//收件人的字符串

                tempJsonObject = jsonArray.getJSONObject(i);
                tempJsonArray = tempJsonObject.getJSONArray("to");//注意，收件人是一个列表
                for (int j = 0;j<tempJsonArray.length();j++){
                    tempToList.add(tempJsonArray.getString(j));
                    toString = tempJsonArray.getString(j)+";";
                }

                //System.out.println(toString);

                //System.out.println(tempToList.toString());
                tempClient.setDate(tempJsonObject.getString("date"));
                tempClient.setFromAddress(tempJsonObject.getString("from"));
                tempClient.setToAddressList(tempToList);//setToAddressList
                tempClient.setToAddress(toString);//
                tempClient.setSubject(tempJsonObject.getString("subject"));
                tempClient.setContent(tempJsonObject.getString("content"));

                tempSSLClients.add(tempClient);
            }
        } catch (IOException | JSONException e ) {
            Log.d("setSSLClients","error");
            //e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempSSLClients;
    }


    //这里对适配器再继承，主要是重写每个条目的监听器逻辑
    class SentboxLinearAdapter extends BoxLinearAdapter {
        private Context mContext;
        private ArrayList<SSLClient> sslClients;
        public SentboxLinearAdapter(Context context) {
            super(context);
            mContext = context;//注意，这句一定要要，因为后面设置点击事件的context是这个类的mContext//下同
        }

        public SentboxLinearAdapter(Context context, ArrayList<SSLClient> sslClients) {
            super(context, sslClients);
            mContext = context;
            this.sslClients = sslClients;
        }

        @Override
        public void onBindViewHolder(@NonNull BoxLinearViewHolder holder, @SuppressLint("RecyclerView") int position) {
            super.onBindViewHolder(holder, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "click..."+position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SentboxActivity.this,ViewEmailActivity.class);//跳转到查看完整邮件的界面
                    SSLClient tempSSLClient = sslClients.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SSLClient",tempSSLClient);//Activity之间传递参数
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }
}