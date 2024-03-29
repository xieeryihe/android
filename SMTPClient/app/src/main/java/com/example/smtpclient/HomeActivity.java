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
    private TextView
            mTextTitleQQ, mTextWriteEmail, mTextSentbox, mTextDraftbox, mTextContacts, mTextLogout,
            mTextHelp, mTextDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mTextTitleQQ = findViewById(R.id.textview_title);
        mTextWriteEmail = findViewById(R.id.textview_write_email);
        mTextSentbox = findViewById(R.id.textview_sentbox);
        mTextDraftbox = findViewById(R.id.textview_draftbox);
        mTextContacts = findViewById(R.id.textview_contacts);
        mTextLogout = findViewById(R.id.textview_logout);
        mTextHelp = findViewById(R.id.textview_help);
        mTextDocument = findViewById(R.id.textview_document);

        mTextTitleQQ.setText(gUsername);
        mTextWriteEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,EmailActivity.class);
                startActivity(intent);
            }
        });
        mTextSentbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SentboxActivity.class);
                startActivity(intent);
            }
        });

        mTextDraftbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DraftboxActivity.class);
                startActivity(intent);
            }
        });

        mTextContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ContactsActivity.class);
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
        
        mTextHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });

        mTextDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,DocumentActivity.class);
                startActivity(intent);
            }
        });

    }
}