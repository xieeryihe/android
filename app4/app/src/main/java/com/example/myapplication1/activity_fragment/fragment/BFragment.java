package com.example.myapplication1.activity_fragment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication1.R;

public class BFragment extends Fragment {
    private TextView mTvTitle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建view时
        View view = inflater.inflate(R.layout.fragment_b,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);//这个是重写函数默认返回的
        return view;//这个就相当于OnCreate中的setContentView(R.layout.activity_container);的作用
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //当view创建完成后
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
    }
}
