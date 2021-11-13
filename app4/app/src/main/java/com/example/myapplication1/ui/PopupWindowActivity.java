package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;

public class PopupWindowActivity extends AppCompatActivity {
    private Button mBtnPop;
    private PopupWindow mPop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        mBtnPop = findViewById(R.id.btn_pop);
        mBtnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View v = getLayoutInflater().inflate(R.layout.layout_pop,null);//用inflate来加载布局文件
                mPop = new PopupWindow(v,mBtnPop.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);//参数分别为view，宽，高
                mPop.setOutsideTouchable(true);//点击外围区域，下拉框就会消失
                mPop.setFocusable(true);//点一下显示，再点一下消失
                //mPop.setAnimationStyle();//可以设置动画，传入的是int参数animationStyle，即Style文件
                mPop.showAsDropDown(mBtnPop);//让这个选择框出现在Button的下面

                TextView textView = v.findViewById(R.id.tv_good);//注意，这里一定是v，而不是外部onClick的view参数，
                // view是整个activity的，而我们要的v只是当前inflate产生的布局文件，一定要注意
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPop.dismiss();//点击了“好”选项，先让mPop消失，
                        ToastUtil.showMsg(PopupWindowActivity.this,"好！");
                    }
                });

            }
        });
    }
}
