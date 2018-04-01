package com.yqbd.yqbdapp.user.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lemon.support.util.AsyncBitmapLoader;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.bean.TaskBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.Serializable;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder>implements View.OnClickListener {
    private AsyncBitmapLoader asyncBitmapLoader = AsyncBitmapLoader.asyncBitmapLoader;
    private List<TaskBean> mData;

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, mData.get((int) v.getTag()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, TaskBean taskBean);
    }
    public MyTaskAdapter(List<TaskBean> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<TaskBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_task_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        TaskBean item = mData.get(position);
        holder.title.setText(item.getTaskTitle());
        holder.deadline.setText("截止时间：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(item.getDeadline()))).toString());
        holder.pay.setText("￥" + item.getPay());
        //holder.img.setImageResource();
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        Bitmap bitmap = asyncBitmapLoader.loadBitmap(holder.img, item.getSimpleDrawingAddress(), holder.img.getLayoutParams().width, holder.img.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
            }
        });
        if (bitmap != null) {
            holder.img.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView title;
        TextView deadline;
        TextView pay;
        ImageView img;

        public ViewHolder(View rootView) {
            super(rootView);
            itemView = rootView;
            title = itemView.findViewById(R.id.my_task_title);
            deadline = itemView.findViewById(R.id.my_task_deadline);
            pay = itemView.findViewById(R.id.my_task_pay);
            img = itemView.findViewById(R.id.my_task_img);
        }
    }

}