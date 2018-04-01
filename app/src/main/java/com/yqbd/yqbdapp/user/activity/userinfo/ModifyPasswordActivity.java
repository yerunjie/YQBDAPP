package com.yqbd.yqbdapp.user.activity.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class ModifyPasswordActivity extends YQBDBaseActivity {

    @BindView(R.id.input_oldpassword)
    EditText oldPassword;
    @BindView(R.id.input_newpassword)
    EditText newPassword;
    @BindView(R.id.input_repeatpassword)
    EditText repeatPassword;

    UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        userInfoBean = (UserInfoBean) getIntent().getParcelableExtra("userInfo");
        initView();
    }

    private void initView(){
        setTitleId(R.string.user_modify_title);
        setRightTextId(R.string.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(oldPassword.getText())
                        || TextUtils.isEmpty(newPassword.getText())
                        || TextUtils.isEmpty(repeatPassword.getText()) ){
                    Toast.makeText(ModifyPasswordActivity.this, "请完整输入信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                String oldP = oldPassword.getText().toString();
                if (!oldP.equals(userInfoBean.getPassword())){
                    Toast.makeText(ModifyPasswordActivity.this, "原始密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                String newP = newPassword.getText().toString();
                String repeatP = repeatPassword.getText().toString();
                if (!newP.equals(repeatP)){
                    Toast.makeText(ModifyPasswordActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    userInfoBean.setPassword(newP);
                    UpdateUserInfoRequest request = new UpdateUserInfoRequest();
                    request.setUserInfoBean(userInfoBean);
                    addRequest(getService(UserApi.class).updateUserInfo(request), new YQBDBaseCallBack() {
                        @Override
                        public void onSuccess200(Object o) {
                            Intent intent = new Intent(ModifyPasswordActivity.this, MyInfoActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }

}
