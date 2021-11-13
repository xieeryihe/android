package com.example.myapplication1.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.R;

public class DataStorageActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnSharedPreferences;
    private Button mBtnFile;
    private Button mBtnExternalFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);
        mBtnSharedPreferences = findViewById(R.id.btn_shared_preferences);
        mBtnFile = findViewById(R.id.btn_file);
        mBtnExternalFile = findViewById(R.id.btn_external_file);
        mBtnSharedPreferences.setOnClickListener(this);//老生常谈了，实现OnClickListener接口后，传入this即可
        mBtnFile.setOnClickListener(this);
        mBtnExternalFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_shared_preferences:
                intent = new Intent(DataStorageActivity.this,SharedPreferencesActivity.class);
            case R.id.btn_file:
                intent = new Intent(DataStorageActivity.this,FileActivity.class);
            case R.id.btn_external_file:
                intent = new Intent(DataStorageActivity.this,ExternalFileActivity.class);
        }
        startActivity(intent);
    }
}
