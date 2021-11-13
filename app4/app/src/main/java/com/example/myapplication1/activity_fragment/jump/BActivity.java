package com.example.myapplication1.activity_fragment.jump;

    import androidx.appcompat.app.AppCompatActivity;
    import android.app.Activity;
    import android.content.Intent;
    import android.content.pm.ActivityInfo;
    import android.content.pm.PackageManager;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;
    import com.example.myapplication1.R;

public class BActivity extends AppCompatActivity {
    private TextView mTvTitle;
    private Button mBtnBackToA;
    private Button mBtnJumpToB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Log.d("BActivity","------OnCreate------");
        Log.d("BActivity","Taskid:"+getTaskId()+" ,hash:"+hashCode());//用哈希码来看看是不是新的Activity
        logTaskName();
        mTvTitle = findViewById(R.id.tv_title);
        mBtnBackToA = findViewById(R.id.back_to_A);
        mBtnJumpToB = findViewById(R.id.jump_to_B);
        Bundle bundle = getIntent().getExtras();//获取从A拿到/返回的数据
        String name = bundle.getString("name");
        int number = bundle.getInt("number");
        mTvTitle.setText(name+","+number);
        mBtnBackToA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putString("title","回到A页面");
                intent.putExtras(bundle1);
                setResult(Activity.RESULT_OK,intent);
                finish();//直接结束当前页面
            }
        });
        mBtnJumpToB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BActivity.this,BActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("BActivity","------OnNewIntent------");
        Log.d("BActivity","Taskid:"+getTaskId()+" ,hash:"+hashCode());//用哈希码来看看是不是新的Activity
        logTaskName();
    }
    private void logTaskName(){
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Log.d("BActivity",info.taskAffinity);//Affinity表示“关系、依赖”，第二个参数可以理解为当前任务栈的名称
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
