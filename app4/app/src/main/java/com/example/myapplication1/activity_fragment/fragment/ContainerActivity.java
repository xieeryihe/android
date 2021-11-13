package com.example.myapplication1.activity_fragment.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication1.R;

public class ContainerActivity extends AppCompatActivity implements AFragment.IOnMessageClick {
    private AFragment aFragment;
    private TextView mTvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        mTvTitle = findViewById(R.id.tv_title);
        //实例化AFragment
        //aFragment = new AFragment();
        aFragment = AFragment.newInstance("这是参数展示。");
        //把AFragment实例放到Activity中
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,aFragment,"a").commitAllowingStateLoss();
        //add方法可以把fragment放到activity的指定容器（位置）中去。除此之外，还有replace，remove等方法
        //最后调用的和commit类似的有很多方法，如单纯的一个commit()，不过这个方法可以防止一些因为返回问题而导致的错误
        //第三个参数tag是aFragment的标签，用于AFragment.java中找对应页面用

    }
    public void setData(String text){
        mTvTitle.setText(text);
    }

    @Override
    public void onClick(String text) {
        mTvTitle.setText(text);
    }
}
