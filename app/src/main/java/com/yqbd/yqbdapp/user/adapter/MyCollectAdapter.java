package com.yqbd.yqbdapp.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.bean.TaskBean;
import com.yqbd.yqbdapp.common.Constants;

import java.util.ArrayList;
import java.util.List;

public class MyCollectAdapter extends RecyclerView.Adapter<MyCollectAdapter.ViewHolder> {

    private List<TaskBean> mData;
    private boolean[] isSelect;
    private boolean inEditMode;
    private Context context;

    public void setInEditMode(boolean inEditMode) {
        this.inEditMode = inEditMode;
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedItems(){
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<mData.size(); i++) {
            if (isSelect[i]){
                result.add(mData.get(i).getTaskId());
            }
        }
        return result;
    }

    public void selectAll(){
        for (int i = 0; i < isSelect.length; i++) {
            isSelect[i] = true;
        }
        notifyDataSetChanged();
    }

    public void selectNone(){
        for (int i = 0; i < isSelect.length; i++) {
            isSelect[i] = false;
        }
        notifyDataSetChanged();
    }

    public MyCollectAdapter(List<TaskBean> data, Context context) {
        this.mData = data;
        isSelect = new boolean[data.size()];
        selectNone();
        inEditMode = false;
        this.context = context;
    }

    public void updateData(ArrayList<TaskBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_collect_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // 绑定数据
        TaskBean item = mData.get(position);
        holder.title.setText(item.getTaskTitle());
        holder.deadline.setText("截止时间：" + item.getDeadline());
        holder.pay.setText("￥" + item.getPay());
        holder.address.setText(item.getTaskAddress());
        Constants.TASK_STATUS statusConfig = Constants.TASK_STATUS.getConfig(item.getTaskStatus());
        holder.status.setText(statusConfig.getName());
        holder.status.setBackgroundResource(statusConfig.getBgId());
        holder.status.setTextColor(context.getResources().getColor(statusConfig.getColorId()));
        //holder.img.setImageResource();

        if (inEditMode) {
            holder.checkBox.setVisibility(View.VISIBLE);
            setCheckBoxImg(holder.checkBox, isSelect[position]);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSelect[position] = !isSelect[position];
                    setCheckBoxImg((ImageView) v, isSelect[position]);
                }
            });
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

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

    private void setCheckBoxImg(ImageView v, boolean isChecked){
        if (isChecked) {
            v.setImageResource(R.drawable.ic_checked);
        } else {
            v.setImageResource(R.drawable.ic_unchecked);
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
        TextView address;
        TextView pay;
        ImageView img;
        ImageView checkBox;
        TextView status;

        public ViewHolder(View rootView) {
            super(rootView);
            itemView = rootView;
            title = itemView.findViewById(R.id.my_collect_title);
            deadline = itemView.findViewById(R.id.my_collect_deadline);
            pay = itemView.findViewById(R.id.my_collect_pay);
            img = itemView.findViewById(R.id.my_collect_img);
            checkBox = itemView.findViewById(R.id.my_collect_check_box);
            address = itemView.findViewById(R.id.my_collect_address);
            status = itemView.findViewById(R.id.my_collect_status);
        }
    }

}
