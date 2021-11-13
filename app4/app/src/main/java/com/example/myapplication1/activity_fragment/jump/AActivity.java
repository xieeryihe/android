package com.example.myapplication1.activity_fragment.jump;

import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.R;
import com.example.myapplication1.util.ToastUtil;

public class AActivity extends AppCompatActivity {
    private Button mBtnJumpB;
    private Button mBtnJumpA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Log.d("AActivity","------OnCreate------");
        Log.d("AActivity","Taskid:"+getTaskId()+" ,hash:"+hashCode());//用哈希码来看看是不是新的Activity
        logTaskName();
        mBtnJumpB = findViewById(R.id.jump_to_B);
        mBtnJumpA = findViewById(R.id.jump_to_A);
        mBtnJumpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显式1
//                Intent intent = new Intent(AActivity.this,BActivity.class);
//                startActivity(intent);

                //显式2
//                Intent intent = new Intent();
//                intent.setClass(AActivity.this,BActivity.class);
//                startActivity(intent);

                //显式3
//                Intent intent = new Intent();
//                intent.setClassName(AActivity.this,"com.example.myapplication1.activity_fragment.jump.BActivity");//这个是引用路径
//                startActivity(intent);

                //显式4
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName(AActivity.this,"com.example.myapplication1.activity_fragment.jump.BActivity"));
//                startActivity(intent);

                //隐式
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.JUMP");//填入的字符串是Manifest文件中action的name属性
//                startActivity(intent);

                //通常用第一种方式即可

                Intent intent = new Intent(AActivity.this,BActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name","你好");
                bundle.putInt("number",101);
                intent.putExtras(bundle);//putExtra还需要另一个参数Key，putExtras直接放一个Bundle类型即可
                //startActivity(intent);//这个也可以启动，但是用下一个方法启动activity可以得到B返回给A的信息
                startActivityForResult(intent,8080);//从B返回A的时候用这个方法，从而A能获得B的数据
                //第二个参数是requestCode，返回的状态码，类似于端口号
            }
        });
        mBtnJumpA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AActivity.this,AActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //requestCode是A启动时，用startActivityForResult传入的参数，resultCode是B返回时的状态码（用finish方法结束的）
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtil.showMsg(AActivity.this,data.getExtras().getString("title"));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        //在这个函数里面也打印一遍
        super.onNewIntent(intent);
        Log.d("AActivity","------OnNewIntent------");
        Log.d("AActivity","Taskid:"+getTaskId()+" ,hash:"+hashCode());//用哈希码来看看是不是新的Activity
        logTaskName();
    }

    private void logTaskName(){
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Log.d("AActivity",info.taskAffinity);//Affinity表示“关系、依赖”，第二个参数可以理解为当前任务栈的名称
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
