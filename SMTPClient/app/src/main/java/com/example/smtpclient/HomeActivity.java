package com.example.smtpclient;

import static com.example.smtpclient.MainActivity.gSharedEditor;
import static com.example.smtpclient.MainActivity.gUsername;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextTitleQQ,mTextWriteEmail,mTextInbox,mTextLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mTextTitleQQ = findViewById(R.id.textview_title);
        mTextWriteEmail = findViewById(R.id.textview_write_email);
        mTextInbox = findViewById(R.id.textview_inbox);
        mTextLogout = findViewById(R.id.textview_logout);

        mTextTitleQQ.setText(gUsername);
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
                Intent intent = new Intent(HomeActivity.this, SentboxActivity.class);
                startActivity(intent);
            }
        });
        mTextLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gSharedEditor.putBoolean("if_login",false);
                gSharedEditor.putString("username","");
                gSharedEditor.putString("authorizationCode","");
                gSharedEditor.apply();
                Toast toast = Toast.makeText(HomeActivity.this,"已登出...",Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}