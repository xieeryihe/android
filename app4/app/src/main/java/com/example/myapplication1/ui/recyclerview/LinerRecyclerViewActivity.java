package com.example.myapplication1.ui.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication1.R;

public class LinerRecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liner_recycler_view);
        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(LinerRecyclerViewActivity.this));//设置布局管理器
        //调用的setLayoutManager属性是布局管理器，传的参数是布局管理器实例，这里是线性布局管理器，后面还可以有网格/不规则网格布局管理器 等。

        mRvMain.addItemDecoration(new MyDecoration());//addItemDecoration这个方法可以用来实现分隔线，但不是专门实现分隔线的
        //ItemDecoration是一个抽象类，里面有三个要重写的方法，其中onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull State state)
        // 意思是在item元素绘制前，我先绘制好画布，然后再在上面绘制item，另一个onDrawOver方法则是相反，先绘制item，再绘制画布。
        //还有一个方法getItemOffsets有很多参数，意思是在item的周边绘制

        //mRvMain.setAdapter(new LinearAdapter(LinerRecyclerViewActivity.this));//注意：LinearAdapter是自己写的类
        //上下两行代码是两种写法，都可以。感觉还是在适配器里面写监听器好一些
        mRvMain.setAdapter(new LinearAdapter(LinerRecyclerViewActivity.this, new LinearAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(LinerRecyclerViewActivity.this,"click..."+pos,Toast.LENGTH_SHORT).show();
            }
        }));//用自定义内部接口的方法实现点击事件
    }

    //下面这个是用来画分割线的类
    //为什么要写在这个主页面，而不是适配器java中呢，因为调用的addItemDecoration方法本来就是给主界面绘图的，而不是给单个元素绘图的
    //虽然实现的效果是给每个元素绘制，但是本质上不是一层画布。
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //写下面这行，要先在values中创建dimens.xml资源文件，方便调用。
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
            //上面这行的四个参数，分别是左上右下四个方位，这里是只有下面有线。
            //画的线的颜色就是activity_liner_recycler_view.xml布局文件中的背景色
            //更多内容可以查阅addItemDecoration方法的说明
            // outRect是绘制矩形，四个顶点坐标
        }
    }
}
