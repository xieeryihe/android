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

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder> {
    private Context mContext;
    private ArrayList<SSLClient> sslClients;
    public LinearAdapter(Context context){//写一个本Adapter的构造函数
        this.mContext = context;
    }
    public LinearAdapter(Context context,ArrayList<SSLClient> sslClients){
        this.mContext = context;
        this.sslClients = sslClients;
    }


    @NonNull
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_recycle_element,parent,false));
        //其中layout_linear_item就是layout文件夹下的layout_linear_item.xml文件的文件名（注意不是id），传入的是布局文件
    }

    @Override
    public void onBindViewHolder(@NonNull LinearAdapter.LinearViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.toAddress.setText(sslClients.get(position).getToAddress());
        holder.subject.setText(sslClients.get(position).getSubject());
        holder.content.setText(sslClients.get(position).getContent());
        holder.date.setText(sslClients.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {//给itemView设置点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "click..."+position, Toast.LENGTH_SHORT).show();
                //上面那行是在这个适配器中实现点击事件
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {//长按触发事件
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext,"Long touch"+position,Toast.LENGTH_SHORT).show();
                return true;
                //返回true，表示已经完整地处理了这个事件，并不希望其他的回调方法再次进行处理；
                //返回false，表示并没有完全处理完该事件，更希望其他方法继续对其进行处理。
            }
        });
    }


    @Override
    public int getItemCount() {
        return sslClients.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView toAddress,subject,content,date;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout =itemView.findViewById(R.id.recycler_element);
            toAddress = itemView.findViewById(R.id.textview_ele_toAddress);
            subject = itemView.findViewById(R.id.textview_ele_subject);
            content = itemView.findViewById(R.id.textview_ele_content);
            date = itemView.findViewById(R.id.textview_ele_date);
        }
    }
}
