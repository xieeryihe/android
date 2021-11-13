package com.example.myapplication1.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;

public class DialogActivity extends AppCompatActivity {
    private Button mBtnDialog1,mBtnDialog2,mBtnDialog3,mBtnDialog4,mBtnDialog5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mBtnDialog1 = findViewById(R.id.btn_dialog1);
        mBtnDialog2 = findViewById(R.id.btn_dialog2);
        mBtnDialog3 = findViewById(R.id.btn_dialog3);
        mBtnDialog4 = findViewById(R.id.btn_dialog4);
        mBtnDialog5 = findViewById(R.id.btn_dialog5);
        OnClick onClick = new OnClick();
        mBtnDialog1.setOnClickListener(onClick);
        mBtnDialog2.setOnClickListener(onClick);
        mBtnDialog3.setOnClickListener(onClick);
        mBtnDialog4.setOnClickListener(onClick);
        mBtnDialog5.setOnClickListener(onClick);
    }
    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_dialog1:
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DialogActivity.this);//Builder设计模式，可以自己去看看
                    builder1.setTitle("请回答");
                    builder1.setMessage("你好吗？");
                    //builder.setTitle("请回答").setMessage("你好吗？")//也可以这样写，迭代调用
                    builder1.setIcon(R.drawable.gear);//可以设置个Icon图标
                    builder1.setPositiveButton("是的", new DialogInterface.OnClickListener() {//PositiveButton，肯定按钮
                        //注意这里的OnClickListener不是之前的View下的OnClickListener，而是DialogInterface下的
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(DialogActivity.this,"收到肯定答复");//ToastUtil是上节课自定义的包
                        }
                    });
                    builder1.setNeutralButton("还好", new DialogInterface.OnClickListener() {//中性答复按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(DialogActivity.this,"收到中性答复");
                        }
                    });
                    builder1.setNegativeButton("不太好", new DialogInterface.OnClickListener() {//消极答复按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(DialogActivity.this,"收到消极答复");
                        }
                    });
                    builder1.show();//注意最后要show
                    break;
                case R.id.btn_dialog2:
                    //相当于一个单选效果，当然，也可以用RadioButton来手动加上勾选框框的效果
                    final String[] array2 = new String[]{"男","女"};
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(DialogActivity.this);
                    builder2.setTitle("你的性别");
                    builder2.setItems(array2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //注意，上面传入的参数i就是setItems的参数array中的索引
                            ToastUtil.showMsg(DialogActivity.this,array2[i]);
                        }
                    });
                    builder2.show();
                    break;
                case R.id.btn_dialog3:
                    //单选
                    final String[] array3 = new String[]{"男","女"};
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(DialogActivity.this);
                    builder3.setTitle("你的性别");
                    builder3.setSingleChoiceItems(array3, -1, new DialogInterface.OnClickListener() {
                        //setSingleChoiceItems有多个函数重载，本函数的参数分别是待选数组，默认索引，监听器
                        //设置成-1就可以默认什么都不选（其实只要越界array3就可以）
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(DialogActivity.this,array3[i]);
                            dialogInterface.dismiss();//点击选项一次，就该选择栏就会消失
                        }
                    });
                    builder3.setCancelable(false);//点击选择界面之外的灰色区域，选项栏不会消失
                    builder3.show();
                    break;
                case R.id.btn_dialog4:
                    //多选
                    final String[] array4 = new String[]{"唱歌","跳舞","打篮球"};
                    boolean[] isSelected4 = new boolean[]{false,false,false};//默认是否勾选的数组
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(DialogActivity.this);
                    builder4.setTitle("你的爱好");
                    builder4.setMultiChoiceItems(array4, isSelected4, new DialogInterface.OnMultiChoiceClickListener() {
                        //注意，监听器又变了
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            //i还是之前传入的array4的索引，b是是否选中
                            ToastUtil.showMsg(DialogActivity.this,array4[i]+":"+b);
                        }
                    });
                    builder4.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(DialogActivity.this,"确定");
                        }
                    });
                    builder4.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(DialogActivity.this,"取消");
                        }
                    });
                    builder4.show();
                    break;
                case R.id.btn_dialog5:
                    final AlertDialog.Builder builder5 = new AlertDialog.Builder(DialogActivity.this);
                    builder5.setTitle("请登录");
                    LayoutInflater inflater = LayoutInflater.from(DialogActivity.this);
                    final View view5 = inflater.inflate(R.layout.layout_dialog, null);
                    builder5.setView(view5);
                    final EditText etUsername = view5.findViewById(R.id.et_username);
                    final EditText etPassword = view5.findViewById(R.id.et_password);
                    final Button btnLogin = view5.findViewById(R.id.btn_login);

                    final AlertDialog alertDialog = builder5.show();
                    //由于builder没有dismiss方法来使得对话框消失，故采用AlertDialog中的方法
                    //show方法的定义是：public AlertDialog show() {}
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("Username", String.valueOf(etUsername.getText()));//用getText拿到值，然后再用String.valueOf包装一下
                            Log.d("Password", String.valueOf(etPassword.getText()));//不包装会报错
                            //当然，也可以像EditTextActivity中那样设置对输入框变化的监听，这里不做演示
                            alertDialog.dismiss();//设置点击后builder5界面消失
                        }
                    });

                    break;
            }
        }
    }
}
