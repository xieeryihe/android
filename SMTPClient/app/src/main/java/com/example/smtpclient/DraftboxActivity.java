package com.example.smtpclient;

import static com.example.smtpclient.MainActivity.gDraftsFileName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smtpclient.tools.BoxLinearAdapter;

import java.util.ArrayList;


public class DraftboxActivity extends AppCompatActivity {
    private RecyclerView mRecyclerDraftbox;
    private ArrayList<SSLClient> mSSLClients;
    public DraftboxActivity(){
        mSSLClients = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draftbox);


        SentboxActivity sentboxActivity = new SentboxActivity();//还必须得实例化一个类
        mSSLClients = sentboxActivity.getSSLClients(gDraftsFileName);//先设置所有SSLClient列表，
        //System.out.println(mSSLClients.get(0).getSubject());

        mRecyclerDraftbox = findViewById(R.id.recycler_sentbox);
        mRecyclerDraftbox.setLayoutManager(new LinearLayoutManager(DraftboxActivity.this));
        mRecyclerDraftbox.setAdapter(new DraftboxActivity.DraftboxLinearAdapter(DraftboxActivity.this, mSSLClients));

    }

    class DraftboxLinearAdapter extends BoxLinearAdapter {
        private Context mContext;
        private ArrayList<SSLClient> sslClients;
        public DraftboxLinearAdapter(Context context) {
            super(context);
            mContext = context;//注意，这句一定要要，因为后面设置点击事件的context是这个类的mContext//下同
        }

        public DraftboxLinearAdapter(Context context, ArrayList<SSLClient> sslClients) {
            super(context, sslClients);
            mContext = context;
            this.sslClients = sslClients;
        }

        @Override
        public void onBindViewHolder(@NonNull BoxLinearViewHolder holder, @SuppressLint("RecyclerView") int position) {
            super.onBindViewHolder(holder, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "click..."+position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DraftboxActivity.this,EmailActivity.class);//跳转到查看完整邮件的界面
                    SSLClient tempSSLClient = sslClients.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SSLClient",tempSSLClient);//Activity之间传递参数
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }
}