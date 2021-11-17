package com.example.smtpclient.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smtpclient.R;
import com.example.smtpclient.SSLClient;

import java.util.ArrayList;
//关于已发送箱子，草稿箱等箱子的线性适配器
public class BoxLinearAdapter extends RecyclerView.Adapter<BoxLinearAdapter.BoxLinearViewHolder> {
    private Context mContext;
    private ArrayList<SSLClient> sslClients;
    public BoxLinearAdapter(Context context){//写一个本Adapter的构造函数
        this.mContext = context;
    }
    public BoxLinearAdapter(Context context, ArrayList<SSLClient> sslClients){
        this.mContext = context;
        this.sslClients = sslClients;
    }


    @NonNull
    @Override
    public BoxLinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoxLinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_recycle_element,parent,false));
        //其中layout_linear_item就是layout文件夹下的layout_linear_item.xml文件的文件名（注意不是id），传入的是布局文件
    }

    @Override
    public void onBindViewHolder(@NonNull BoxLinearViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.toAddress.setText(sslClients.get(position).getToAddress());
        holder.subject.setText(sslClients.get(position).getSubject());
        holder.content.setText(sslClients.get(position).getContent());
        holder.date.setText(sslClients.get(position).getDate());
        //将单个元素的点击事件延迟到具体的实现类中
    }


    @Override
    public int getItemCount() {
        return sslClients.size();
    }

    public class BoxLinearViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView toAddress,subject,content,date;

        public BoxLinearViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout =itemView.findViewById(R.id.recycler_element);
            toAddress = itemView.findViewById(R.id.textview_ele_toAddress);
            subject = itemView.findViewById(R.id.textview_ele_subject);
            content = itemView.findViewById(R.id.textview_ele_content);
            date = itemView.findViewById(R.id.textview_ele_date);
        }
    }
}
