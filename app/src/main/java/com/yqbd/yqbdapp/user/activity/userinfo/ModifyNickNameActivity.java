package com.yqbd.yqbdapp.user.activity.userinfo;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.user.activity.MyInfoActivity;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.user.request.UpdateUserInfoRequest;

public class ModifyNickNameActivity extends YQBDBaseActivity {

    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.input_nickname)
    EditText editText;
    @BindView(R.id.tb_title_bar)
    Toolbar toolbar;

    UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nickname);
        ButterKnife.bind(this);
        userInfoBean = (UserInfoBean) getIntent().getParcelableExtra("userInfo");
        initView();
    }

    private void initView(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText() ==  null || editText.getText().toString().isEmpty()){
                    Toast.makeText(ModifyNickNameActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                } else {
                    userInfoBean.setNickName(editText.getText().toString());
                    UpdateUserInfoRequest request = new UpdateUserInfoRequest();
                    request.setUserInfoBean(userInfoBean);
                    addRequest(getService(UserApi.class).updateUserInfo(request), new YQBDBaseCallBack() {
                        @Override
                        public void onSuccess200(Object o) {
                            Intent intent = new Intent(ModifyNickNameActivity.this, MyInfoActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }

}
