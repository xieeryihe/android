package com.example.smtpclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smtpclient.tools.Info;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextTitleQQ,mTextWriteEmail,mTextInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mTextTitleQQ = findViewById(R.id.textview_title);
        mTextWriteEmail = findViewById(R.id.textview_write_email);
        mTextInbox = findViewById(R.id.textview_inbox);

        mTextTitleQQ.setText(Info.gUsername);
        mTextWriteEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,EmailActivity.class);
                startActivity(intent);
            }
        });
        mTextInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,InboxActivity.class);
                startActivity(intent);
            }
        });

    }
}