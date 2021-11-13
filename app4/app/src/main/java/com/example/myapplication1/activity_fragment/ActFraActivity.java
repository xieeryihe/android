package com.example.myapplication1.activity_fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.R;
import com.example.myapplication1.activity_fragment.fragment.ContainerActivity;
import com.example.myapplication1.activity_fragment.jump.AActivity;

public class ActFraActivity extends AppCompatActivity {
    private Button mBtn_life_cycle;
    private Button mBtn_jump;
    private Button mBtn_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_fra);
        mBtn_life_cycle = findViewById(R.id.btn_life_cycle);
        mBtn_jump = findViewById(R.id.btn_jump);
        mBtn_fragment = findViewById(R.id.btn_fragment);
        OnClick onClick = new OnClick();
        mBtn_life_cycle.setOnClickListener(onClick);
        mBtn_jump.setOnClickListener(onClick);
        mBtn_fragment.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.btn_life_cycle:
                    intent = new Intent(ActFraActivity.this, LifeCycleActivity.class);
                    break;
                case R.id.btn_jump:
                    intent = new Intent(ActFraActivity.this, AActivity.class);
                    break;
                case R.id.btn_fragment:
                    intent = new Intent(ActFraActivity.this, ContainerActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
