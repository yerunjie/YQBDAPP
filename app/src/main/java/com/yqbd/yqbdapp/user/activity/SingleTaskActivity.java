package com.yqbd.yqbdapp.user.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.SingleResultBean;
import com.yqbd.yqbdapp.bean.TaskBean;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.user.request.UserTakeRequest;
import com.yqbd.yqbdapp.widget.TagListView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleTaskActivity extends YQBDBaseActivity {

    @BindView(R.id.task_button)
    Button taskButton;

    boolean istake = false;

    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_description)
    TextView taskDescription;
    @BindView(R.id.primary_work)
    TextView primaryWork;
    @BindView(R.id.task_address)
    TextView taskAddress;
    @BindView(R.id.pay)
    TextView pay;
    @BindView(R.id.max_people_number)
    TextView maxPeopleNumber;
    @BindView(R.id.primary_contact)
    TextView primaryContact;
    @BindView(R.id.other_company)
    TextView otherCompany;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.complete_time)
    TextView completeTime;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.moments_appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tagview)
    TagListView tagview;
    @BindView(R.id.participant_button)
    Button participantButton;

    @BindView(R.id.topToolBarTitle)
    TextView topToolBarTitle;

    @BindView(R.id.toolBar)
    protected Toolbar toolBar;
    private TaskBean taskBean;
    UserInfoBean userInfoBean;

    private MenuItem collectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        taskBean = (TaskBean) bundle.getSerializable("taskBean");
        setContentView(R.layout.activity_single_task);

        ButterKnife.bind(this);
        initView();
        super.hideTitleBar();

        // initializeTop(true, taskBean.getTaskTitle());
        //taskAction.isUserTaken(bundle.getInt("userId"), taskBean.getTaskId());
        taskTitle.setText(taskBean.getTaskTitle());
        taskDescription.setText(taskBean.getTaskDescription());
        primaryWork.setText(taskBean.getPrimaryWork());
        primaryContact.setText(taskBean.getPrimaryContact());
        taskAddress.setText(taskBean.getTaskAddress());
        remark.setText(taskBean.getRemark());
        otherCompany.setText(taskBean.getOtherCompany());
        maxPeopleNumber.setText(taskBean.getMaxPeopleNumber().toString());
        pay.setText(taskBean.getPay().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        startTime.setText(simpleDateFormat.format(new Date(taskBean.getStartTime())));
        completeTime.setText(simpleDateFormat.format(new Date(taskBean.getCompleteTime())));

        addRequest(getService(UserApi.class).isTake(taskBean.getTaskId(), 0), new YQBDBaseCallBack<YQBDBaseResponse<SingleResultBean>>() {
            @Override
            public void onSuccess200(YQBDBaseResponse<SingleResultBean> o) {
                istake = o.obj.singleResult;
                refreshTakeButton(istake);
            }
        });
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequest(getService(UserApi.class).takeTasks(taskBean.getTaskId(), 0), new YQBDBaseCallBack() {
                    @Override
                    public void onSuccess200(Object o) {
                        istake = !istake;
                        refreshTakeButton(istake);
                    }

                });

                //taskAction.take(taskBean.getTaskId());
            }
        });
    }

    protected void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.moments_appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        toolBar.setTitle(taskBean.getTaskTitle());
        topToolBarTitle.setText("");
        setSupportActionBar(toolBar);
        //activity.getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolBar.setNavigationIcon(R.drawable.return_img);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.top_toolbar_style, menu);
        collectButton = menu.getItem(0);
        addRequest(getService(UserApi.class).isCollected(taskBean.getTaskId(), 0), new YQBDBaseCallBack<YQBDBaseResponse<SingleResultBean>>() {
            @Override
            public void onSuccess200(YQBDBaseResponse<SingleResultBean> o) {
                resetCollectButton(o.obj.singleResult);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODOAuto-generated method stub
        //Toast.makeText(this,item.getTitle()+String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
        //区分被点击的item
        switch (item.getItemId()) {
            case R.id.action_collect:
                addRequest(getService(UserApi.class).collect(taskBean.getTaskId(), 0), new YQBDBaseCallBack<YQBDBaseResponse<SingleResultBean>>() {
                    @Override
                    public void onSuccess200(YQBDBaseResponse<SingleResultBean> o) {
                        resetCollectButton(o.obj.singleResult);
                    }
                });
                break;
            case R.id.action_share:
                makeToast("获得测试资格才可使用");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void resetCollectButton(Boolean result) {
        if (result) {
            collectButton.setIcon(R.drawable.collect);
        } else {
            collectButton.setIcon(R.drawable.collect_white);
        }
    }

    void refreshTakeButton(Boolean istake) {
        if (istake) {
            taskButton.setText("取消申请");
        } else {
            taskButton.setText("申请");
        }
    }
}
