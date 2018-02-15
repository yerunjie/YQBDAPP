package com.yqbd.yqbdapp.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.login.api.LoginApi;
import com.yqbd.yqbdapp.login.request.UserLoginRequest;
import com.yqbd.yqbdapp.main.activity.MainActivity;

public class UserLoginActivity extends YQBDBaseActivity {
    @BindView(R.id.et_user_account)
    EditText et_user_account;
    @BindView(R.id.et_user_password)
    EditText et_user_password;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_user_login);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountNumberString = et_user_account.getText().toString();
                String userPasswordString = et_user_password.getText().toString();
                UserLoginRequest request = new UserLoginRequest();
                request.setAccountNumber(accountNumberString);
                request.setUserPassword(userPasswordString);
                addRequest(getService(LoginApi.class).doLogin(request), new YQBDBaseCallBack() {
                    @Override
                    public void onSuccess200(Object o) {
                        makeToast("登陆成功");
                        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

}
