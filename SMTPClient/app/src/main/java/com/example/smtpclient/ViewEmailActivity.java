package com.example.smtpclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewEmailActivity extends AppCompatActivity {
    private TextView mTextFrom,mTextTo,mTextSubject,mTextDate,mTextContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_email);

        mTextFrom = findViewById(R.id.textview_from);
        mTextTo = findViewById(R.id.textview_to);
        mTextSubject = findViewById(R.id.textview_subject);
        mTextDate = findViewById(R.id.textview_date);
        mTextContent = findViewById(R.id.textview_content);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        SSLClient sslClient = (SSLClient) bundle.getSerializable("SSLClient");

        mTextFrom.setText(sslClient.getFromAddress());
        StringBuilder toAddress = new StringBuilder();
        ArrayList<String> toList = sslClient.getToAddressList();
        for (int i = 0; i<toList.size();i++){
            toAddress.append(toList.get(i)+"\n");
        }
        mTextTo.setText(toAddress.substring(0,toAddress.length()-1));//去掉最后一个换行符
        mTextSubject.setText(sslClient.getSubject());
        mTextDate.setText(sslClient.getDate());
        mTextContent.setText(sslClient.getContent());


    }
}