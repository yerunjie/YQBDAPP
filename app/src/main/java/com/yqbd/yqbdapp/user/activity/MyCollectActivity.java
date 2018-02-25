package com.yqbd.yqbdapp.user.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.TaskBean;
import com.yqbd.yqbdapp.bean.UserCollectKey;
import com.yqbd.yqbdapp.common.Constants;
import com.yqbd.yqbdapp.user.adapter.MyCollectAdapter;
import com.yqbd.yqbdapp.user.adapter.MyTaskAdapter;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.user.request.UncollectTaskRequest;
import com.yqbd.yqbdapp.utils.GsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyCollectActivity  extends YQBDBaseActivity implements View.OnClickListener {

    @BindView(R.id.recycle_view_my_collect)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_collect_delete)
    TextView btnDelete;
    @BindView(R.id.my_collect_select_all)
    TextView btnSelectAll;
    @BindView(R.id.my_collect_bottom_dialog)
    LinearLayout myCollectBottomDialog;
    @BindView(R.id.my_collect_editor)
    TextView btnEditor;
    @BindView(R.id.tb_title_bar)
    Toolbar toolbar;

    private MyCollectAdapter adapter;
    private boolean inEditMode = false;
    private boolean allSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        btnEditor.setOnClickListener(this);
        btnSelectAll.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        addRequest(getService(UserApi.class).getCollectedTasks(), new YQBDBaseCallBack() {
            @Override
            public void onSuccess200(Object o) {
                createAdapterFromResponse(o);
            }

        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_collect_editor:
                changeEditMode();
                break;
            case R.id.my_collect_select_all:
                if (inEditMode){
                    if (allSelected){
                        adapter.selectNone();
                        btnSelectAll.setText("全选");
                        allSelected = false;
                    } else {
                        adapter.selectAll();
                        btnSelectAll.setText("反选");
                        allSelected = true;
                    }
                }
                break;
            case R.id.my_collect_delete:
                unCollect();
                break;
            default:break;
        }
    }

    private void unCollect(){
        List<Integer> unCollectedList = adapter.getSelectedItems();

        if (unCollectedList.size() > 0){
            UncollectTaskRequest request = new UncollectTaskRequest();
            request.setUserCollectList(unCollectedList);
            addRequest(getService(UserApi.class).uncollectedTasks(request), new YQBDBaseCallBack() {
                @Override
                public void onSuccess200(Object o) {
                    createAdapterFromResponse(o);
                }
            });
        }

        changeEditMode();
    }

    private void changeEditMode(){
        inEditMode = !inEditMode;
        if (inEditMode){
            btnEditor.setText("取消");
            myCollectBottomDialog.setVisibility(View.VISIBLE);
        } else {
            btnEditor.setText("编辑");
            adapter.selectNone();
            myCollectBottomDialog.setVisibility(View.GONE);
        }

        adapter.setInEditMode(inEditMode);
    }

    private void createAdapterFromResponse(Object o){
        if (o instanceof YQBDBaseResponse){
            List<TaskBean> tasks = GsonUtil.getEntity(o, new com.google.common.reflect.TypeToken<List<TaskBean>>() {}.getType());;
            adapter = new MyCollectAdapter(tasks,MyCollectActivity.this);
            mRecyclerView.setAdapter(adapter);
        }
    }

}
