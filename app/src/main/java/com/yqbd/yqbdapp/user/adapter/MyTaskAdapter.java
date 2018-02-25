package com.yqbd.yqbdapp.user.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.bean.TaskBean;

import java.util.ArrayList;
import java.util.List;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {

    private List<TaskBean> mData;


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
        holder.deadline.setText("截止时间：" + item.getDeadline());
        holder.pay.setText("￥" + item.getPay());
        //holder.img.setImageResource();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
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