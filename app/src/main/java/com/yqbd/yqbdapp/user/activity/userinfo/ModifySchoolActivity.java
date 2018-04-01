package com.yqbd.yqbdapp.user.activity.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.user.activity.MyInfoActivity;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.user.request.UpdateUserInfoRequest;

public class ModifySchoolActivity extends YQBDBaseActivity {

    @BindView(R.id.input_school)
    EditText editText;

    UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_school);
        ButterKnife.bind(this);
        userInfoBean = (UserInfoBean) getIntent().getParcelableExtra("userInfo");
        initView();
    }

    private void initView(){
        setTitleId(R.string.user_modify_title);
        setRightTextId(R.string.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText() ==  null || editText.getText().toString().isEmpty()){
                    Toast.makeText(ModifySchoolActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                } else {
                    userInfoBean.setSchool(editText.getText().toString());
                    UpdateUserInfoRequest request = new UpdateUserInfoRequest();
                    request.setUserInfoBean(userInfoBean);
                    addRequest(getService(UserApi.class).updateUserInfo(request), new YQBDBaseCallBack() {
                        @Override
                        public void onSuccess200(Object o) {
                            Intent intent = new Intent(ModifySchoolActivity.this, MyInfoActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }

}
