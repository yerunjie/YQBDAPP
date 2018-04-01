package com.yqbd.yqbdapp.user.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.TaskBean;
import com.yqbd.yqbdapp.user.adapter.MyTaskAdapter;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.utils.GsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyTaskActivity extends YQBDBaseActivity {

    @BindView(R.id.recycle_view_my_task)
    RecyclerView mRecyclerView;

    @BindView(R.id.tb_title_bar)
    Toolbar toolbar;

    private MyTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        ButterKnife.bind(this);
        initView();
        setTitleString("我的任务");
    }

    private void initView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        addRequest(getService(UserApi.class).getAcceptTasks(), new YQBDBaseCallBack() {
            @Override
            public void onSuccess200(Object o) {
                List<TaskBean> tasks = GsonUtil.getEntity(o, new com.google.common.reflect.TypeToken<List<TaskBean>>() {}.getType());
                adapter = new MyTaskAdapter(tasks);
                mRecyclerView.setAdapter(adapter);
            }

        });
    }
}
