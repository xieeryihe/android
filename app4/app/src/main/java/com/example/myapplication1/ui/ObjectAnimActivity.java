package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication1.R;

public class ObjectAnimActivity extends AppCompatActivity {
    private TextView mTvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_anim);
        mTvTest = findViewById(R.id.tv);
        //--------------------第一种方法，通过view的自带属性（TextView继承于View）-------------------
        //mTvTest.animate().rotationYBy(360).setDuration(2000).start();//将目标TextView沿y轴在2s内三维旋转360个单位
        //mTvTest.animate().translationYBy(500).setDuration(2000).start();//将目标TextView沿y轴在2s内平移500 个单位
        //mTvTest.animate().alpha(0).setDuration(2000).start();//2s内透明度变为0
        //上面是通过view自带的方法
        //--------------------第二种方法，通过ValueAnimator---------------------------------
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);//有一个值从0到100开始变化
//        valueAnimator.setDuration(2000);//值变化的在2s内进行
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//设置一个变化监听器
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                Log.d("real",valueAnimator.getAnimatedValue()+"");//valueAnimator实际的值
//                Log.d("percentage",valueAnimator.getAnimatedFraction()+"");//动画的进度0-1
//            }
//        });
//        valueAnimator.start();
        //--------------------第三种方法，通过ObjectAnimator---------------------------------
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTvTest,"translationY",0,500,200);
        //第二个参数是propertyName，有严格的定义，可以自行查阅。从第三个参数开始，就是一系列坐标，比如沿Y从0~500~200
        objectAnimator.setDuration(4000);//设个四秒
        objectAnimator.start();//同样，想监听也可以通过addUpdateListener设置监听

    }
}
