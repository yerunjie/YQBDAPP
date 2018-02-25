package com.yqbd.yqbdapp.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.landptf.view.ButtonExtendM;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseFragment;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.user.activity.MyCollectActivity;
import com.yqbd.yqbdapp.user.activity.MyInfoActivity;
import com.yqbd.yqbdapp.user.activity.MyTaskActivity;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.utils.GsonUtil;

import java.lang.reflect.Type;


public class UserCenterFragment extends YQBDBaseFragment implements ButtonExtendM.OnClickListener {

    private Unbinder unbinder;

    @BindView(R.id.btn_my_collect)
    ButtonExtendM btnMyCollect;
    @BindView(R.id.btn_my_info)
    ButtonExtendM btnMyInfo;
    @BindView(R.id.btn_my_task)
    ButtonExtendM btnMyTask;
    @BindView(R.id.user_center_nickname)
    TextView nicknameTextView;
    @BindView(R.id.user_center_account)
    TextView accountTextView;
    @BindView(R.id.user_center_portrait)
    ImageView portraitView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        btnMyTask.setOnClickListener(this);
        btnMyInfo.setOnClickListener(this);
        btnMyCollect.setOnClickListener(this);
        addRequest(getService(UserApi.class).getUserInfo(), new YQBDBaseCallBack() {
            @Override
            public void onSuccess200(Object o) {
                UserInfoBean userInfoBean = GsonUtil.getEntity(o, new com.google.common.reflect.TypeToken<UserInfoBean>() {}.getType());;
                if (userInfoBean.getHeadPortrait() != null && !userInfoBean.getHeadPortrait().isEmpty()){
                    //todo set portrait
                }
                nicknameTextView.setText(userInfoBean.getNickName());
                accountTextView.setText(userInfoBean.getAccountNumber());
            }

        });
    }

    public static UserCenterFragment newInstance(){
        UserCenterFragment userCenterFragment = new UserCenterFragment();
        return userCenterFragment;
    }

    @Override
    public void onClick(View v) {
        //todo -add some args
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_my_task:
                intent = new Intent(getActivity(), MyTaskActivity.class);
                break;
            case R.id.btn_my_info:
                intent = new Intent(getActivity(), MyInfoActivity.class);
                break;
            case R.id.btn_my_collect:
                intent = new Intent(getActivity(), MyCollectActivity.class);
                break;
            default:
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
