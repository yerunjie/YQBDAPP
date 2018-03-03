package com.yqbd.yqbdapp.user.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lemon.support.Base.BaseActivity;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.SingleResultBean;
import com.yqbd.yqbdapp.bean.TaskBean;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.user.adapter.MyTaskAdapter;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.user.request.UserTakeRequest;
import com.yqbd.yqbdapp.utils.GsonUtil;
import com.yqbd.yqbdapp.widget.TagListView;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        addRequest(getService(UserApi.class).getUserInfo(), new YQBDBaseCallBack() {
            @Override
            public void onSuccess200(Object o) {
                userInfoBean = GsonUtil.getEntity(o, new com.google.common.reflect.TypeToken<UserInfoBean>() {
                }.getType());
                addRequest(getService(UserApi.class).isTake(taskBean.getTaskId(), userInfoBean.getUserId()), new YQBDBaseCallBack() {
                    @Override
                    public void onSuccess200(Object o) {

                        SingleResultBean singleResultBean = GsonUtil.getEntity(o, new com.google.common.reflect.TypeToken<SingleResultBean>() {
                        }.getType());
                       istake = singleResultBean.singleResult;
                       refreshTakeButton(istake);
                    }
                });
            }
        });


        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTakeRequest userTakeRequest = new UserTakeRequest();
                userTakeRequest.setTaskId(taskBean.getTaskId());
                userTakeRequest.setUserId(userInfoBean.getUserId());
                addRequest(getService(UserApi.class).takeTasks(taskBean.getTaskId(), userInfoBean.getUserId()), new YQBDBaseCallBack() {
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
    void  refreshTakeButton(Boolean istake){
        if(istake){
            taskButton.setText("取消申请");
        }
        else
        {
            taskButton.setText("申请");
        }
    };
}
