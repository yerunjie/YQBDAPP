package com.yqbd.yqbdapp.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.bean.TaskBean;
import com.yqbd.yqbdapp.main.fragments.datetimepicker.DatePickerFragment;
import com.yqbd.yqbdapp.main.fragments.datetimepicker.IDataCallBack;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.user.request.TaskRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostTaskActivity extends YQBDBaseActivity implements IDataCallBack {

    @BindView(R.id.task_title)
    EditText taskTitle;

    @BindView(R.id.task_description)
    EditText taskDescription;

    @BindView(R.id.task_address)
    EditText taskAddress;

    @BindView(R.id.pay)
    EditText pay;

    @BindView(R.id.max_people_number)
    EditText maxPeopleNubmer;

    @BindView(R.id.deadline)
    Button deadlineButton;

    @BindView(R.id.deadline_time)
    TextView deadlineTime;

    @BindView(R.id.task_publish)
    Button taskButton;

    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_task);
        ButterKnife.bind(this);
        setTitle("发布任务");
        deadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                //调用show方法弹出对话框
                // 第一个参数为FragmentManager对象
                // 第二个为调用该方法的fragment的标签
                datePickerFragment.show(getSupportFragmentManager(), "date_picker");
            }
        });
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskBean taskBean = new TaskBean();
                taskBean.setTaskTitle(taskTitle.getText().toString());
                taskBean.setTaskDescription(taskDescription.getText().toString());
                taskBean.setTaskAddress(taskAddress.getText().toString());
                taskBean.setPay(Double.valueOf(pay.getText().toString()));
                taskBean.setMaxPeopleNumber(Integer.valueOf(maxPeopleNubmer.getText().toString()));
                taskBean.setDeadline(date.getTime());
                TaskRequest taskRequest = new TaskRequest();
                taskRequest.setTaskBean(taskBean);
                addRequest(getService(UserApi.class).publishTask(taskRequest), new YQBDBaseCallBack() {
                    @Override
                    public void onSuccess200(Object o) {
                        makeToast("成功");
                    }
                });
            }
        });


    }

    @Override
    public void getData(Date date) {
        this.date = date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        deadlineTime.setText(formatter.format(date));
    }

}
