package com.example.myapplication1.ui.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.R;

public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.LinearViewHolder> {
    private Context mContext;
    private OnItemClickListener mListener;//该成员变量接口是用于外面设置点击事件用的
    public StaggeredGridAdapter(Context context){//写一个本Adapter的构造函数
        this.mContext = context;
    }
    public StaggeredGridAdapter(Context context, OnItemClickListener listener){//重载的用于接受mListener传参的构造
        this.mContext = context;
        this.mListener = listener;

    }

    @NonNull
    @Override
    public StaggeredGridAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_staggered_grid_item,parent,false));
        //其中layout_linear_item就是layout文件夹下的layout_staggered_grid_item.xml文件的文件名（注意不是id），传入的是布局文件
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredGridAdapter.LinearViewHolder holder, final int position) {
        if(position%2==0){ //交错图片
            holder.imageView.setImageResource(R.drawable.xmm1);
        }
        else{
            holder.imageView.setImageResource(R.drawable.xmm2);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {//给itemView设置点击事件
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {//长按触发事件
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext,"Long touch:"+position,Toast.LENGTH_SHORT).show();
                return true;
                //返回true，表示已经完整地处理了这个事件，并不希望其他的回调方法再次进行处理；
                //返回false，表示并没有完全处理完该事件，更希望其他方法继续对其进行处理。
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);//用的是itemView的构造方法
            imageView = itemView.findViewById(R.id.iv);
            // 但是layout文件夹下，还有另一个同名标签，在activity_check_box.xml文件中，那么是怎么分辨的呢，
            // 靠的就是上一行，给textView赋值的时候，注意用的是itemView视图下的findViewById方法，就是在这个视图下来找，
            // 可以在本文件中看到，上面onCreateViewHolder的返回值，用的是layout_linear_item文件，如此便能区分了
        }
    }

    //定义接口，用于点击事件
    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
