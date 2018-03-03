package com.yqbd.yqbdapp.main.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yqbd.yqbdapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.landptf.view.ButtonExtendM;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseFragment;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.TaskBean;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.user.activity.MyCollectActivity;
import com.yqbd.yqbdapp.user.activity.MyInfoActivity;
import com.yqbd.yqbdapp.user.activity.MyTaskActivity;
import com.yqbd.yqbdapp.user.activity.SingleTaskActivity;
import com.yqbd.yqbdapp.user.adapter.MyTaskAdapter;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.utils.GsonUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.io.Serializable;


public class TaskListFragment extends YQBDBaseFragment implements ButtonExtendM.OnClickListener {

    private Unbinder unbinder;

    @BindView(R.id.recycle_view_task_list)
    RecyclerView recyclerViewTaskList;

    private MyTaskAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        recyclerViewTaskList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerViewTaskList.setItemAnimator(new DefaultItemAnimator());

        addRequest(getService(UserApi.class).getAllTasks(), new YQBDBaseCallBack() {
            @Override
            public void onSuccess200(Object o) {
                List<TaskBean> tasks = GsonUtil.getEntity(o, new com.google.common.reflect.TypeToken<List<TaskBean>>() {}.getType());
                adapter = new MyTaskAdapter(tasks);
                recyclerViewTaskList.setAdapter(adapter);
                adapter.setOnItemClickListener(new MyTaskAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, TaskBean taskBean) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), SingleTaskActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("taskBean", taskBean);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

        });
    }

    public static TaskListFragment newInstance(){
        TaskListFragment taskListFragment = new TaskListFragment();
        return taskListFragment;
    }

    @Override
    public void onClick(View v) {
        //todo -add some args
        Intent intent = null;
        switch (v.getId()){

        }
        if (intent != null){
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
