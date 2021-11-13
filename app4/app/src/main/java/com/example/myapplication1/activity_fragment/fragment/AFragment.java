package com.example.myapplication1.activity_fragment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplication1.R;


public class AFragment extends Fragment {
    private TextView mTvTitle;
    private Button mBtnChange,mBtnReset,mBtnMessage;
    private BFragment bFragment;
    private IOnMessageClick listener;

    public static AFragment newInstance(String title){//通过静态方法创建对象
        AFragment fragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;//这样就可以创建一个带好参数的AFragment对象
    }

    public interface IOnMessageClick{
        void onClick(String text);
    }

    @Override
    public void onAttach(Context context) {
        Log.d("--onAttach context--",context.toString());
        super.onAttach(context);
        try {   //防止强转失败（比如ContainerActivity中未实现IOnMessageClick接口）
            listener = (IOnMessageClick) context;//强转成接口类型
        } catch (ClassCastException e){
            throw new ClassCastException("Activity必须实现IOnClickMessage接口 ");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建view时
        View view = inflater.inflate(R.layout.fragment_a,container,false);
        Log.d("AFragment","------onCreateView------");
        //return super.onCreateView(inflater, container, savedInstanceState);//这个是重写函数默认返回的
        return view;//这个就相当于OnCreate中的setContentView(R.layout.activity_container);的作用
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //当view创建完成后
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mBtnChange = view.findViewById(R.id.btn_change);
        mBtnReset = view.findViewById(R.id.btn_reset);
        mBtnMessage = view.findViewById(R.id.btn_message);
        mBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bFragment==null){
                    bFragment = new BFragment();
                }
                Fragment fragment = getFragmentManager().findFragmentByTag("a");//这里不用特别定义为AFragment，定义为一般的Fragment即可。
                if(fragment!=null){
                    getFragmentManager().beginTransaction().hide(fragment).add(R.id.fl_container,bFragment).addToBackStack(null).commitAllowingStateLoss();
                    //如果有a界面，就先隐藏a，然后add显示b，并将b加入栈
                } else { //否则没有a界面
                    Log.d("AFragment","进入else语句");
                    getFragmentManager().beginTransaction().replace(R.id.fl_container,bFragment).addToBackStack(null).commitAllowingStateLoss();
                    //相当于切换到bFragment界面的同时，将b界面加入到栈中
                    //经过测试，xs，根本就进不去else
                }
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvTitle.setText("我是新文字");
            }
        });
        if (getArguments()!=null){
            mTvTitle.setText(getArguments().getString("title"));//通过"title"这个key来获取参数
            //再通过setText来控制TextView展示的文本
        }
        mBtnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((ContainerActivity)getActivity()).setData("修改后的string内容。");
                listener.onClick("你好");
            }
        });
    }
}
