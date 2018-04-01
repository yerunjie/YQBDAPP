package com.yqbd.yqbdapp.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseFragment;
import com.yqbd.yqbdapp.bean.TaskBean;
import com.yqbd.yqbdapp.main.adapter.RecommendTaskAdapter;
import com.yqbd.yqbdapp.user.activity.SingleTaskActivity;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yerunjie on 2018/4/1
 *
 * @author yerunjie
 */
public class IndexFragment extends YQBDBaseFragment {
    private List<TaskBean> tasks = new ArrayList<>();
    private List<TaskBean> recommendTasks = new ArrayList<>();
    private RecyclerView taskList;
    private RecommendTaskAdapter adapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        setTitleString("首页");
        taskList = (RecyclerView) getActivity().findViewById(R.id.recommendTaskList);
        linearLayoutManager = new LinearLayoutManager(this.getActivity(), OrientationHelper.HORIZONTAL, true);
        taskList.setLayoutManager(linearLayoutManager);
        adapter = new RecommendTaskAdapter(recommendTasks, getActivity());
        taskList.setAdapter(adapter);
        addRequest(getService(UserApi.class).getTasks(5, 0), new YQBDBaseCallBack() {
            @Override
            public void onSuccess200(Object o) {
                List<TaskBean> tasks = GsonUtil.getEntity(o, new com.google.common.reflect.TypeToken<List<TaskBean>>() {
                }.getType());
                recommendTasks.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setOnItemClickListener(new RecommendTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TaskBean taskBean) {
                Intent intent = new Intent(getActivity(), SingleTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("taskBean", taskBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }
}
