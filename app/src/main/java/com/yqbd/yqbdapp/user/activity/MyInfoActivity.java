package com.yqbd.yqbdapp.user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.user.activity.userinfo.*;
import com.yqbd.yqbdapp.user.api.UserApi;
import com.yqbd.yqbdapp.user.request.UpdateUserInfoRequest;
import com.yqbd.yqbdapp.utils.GsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyInfoActivity extends YQBDBaseActivity implements View.OnClickListener{

    @BindView(R.id.my_info_account)
    TextView accountTextView;

    @BindView(R.id.my_info_realname)
    TextView realnameTextView;

    @BindView(R.id.my_info_nickname)
    TextView nicknameTextView;

    @BindView(R.id.my_info_sex)
    TextView sexTextView;

    @BindView(R.id.my_info_occupation)
    TextView occupationTextView;

    @BindView(R.id.my_info_school)
    TextView schoolTextView;

    @BindView(R.id.my_info_telephone)
    TextView telephoneTextView;

    @BindView(R.id.btn_password)
    ImageButton passwordImageButton;

    @BindView(R.id.btn_nickname)
    ImageButton nicknameImageButton;

    @BindView(R.id.btn_sex)
    ImageButton sexImageButton;

    @BindView(R.id.btn_occupation)
    ImageButton occupationImageButton;

    @BindView(R.id.btn_school)
    ImageButton schoolImageButton;

    @BindView(R.id.btn_telephone)
    ImageButton telephoneImageButton;


    OptionsPickerView sexPickerView;
    private ArrayList<String> optionItems;

    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        optionItems = new ArrayList<>();
        optionItems.add("男");
        optionItems.add("女");
        addRequest(getService(UserApi.class).getUserInfo(), new YQBDBaseCallBack() {
            @Override
            public void onSuccess200(Object o) {
                userInfoBean = GsonUtil.getEntity(o, new com.google.common.reflect.TypeToken<UserInfoBean>() {}.getType());
                accountTextView.setText(userInfoBean.getAccountNumber());
                realnameTextView.setText(userInfoBean.getRealName());
                nicknameTextView.setText(userInfoBean.getNickName());
                sexTextView.setText(userInfoBean.getSex());
                occupationTextView.setText(userInfoBean.getOccupation());
                schoolTextView.setText(userInfoBean.getSchool());
                telephoneTextView.setText(userInfoBean.getTelephone());

            }

        });
        setTitleId(R.string.user_my_info_title);
        showBackIcon();
        initOptionPicker();
        nicknameImageButton.setOnClickListener(this);
        occupationImageButton.setOnClickListener(this);
        passwordImageButton.setOnClickListener(this);
        schoolImageButton.setOnClickListener(this);
        sexImageButton.setOnClickListener(this);
        telephoneImageButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_password:
                intent = new Intent(this, ModifyPasswordActivity.class);
                break;

            case R.id.btn_nickname:
                intent = new Intent(this, ModifyNickNameActivity.class);
                break;

            case R.id.btn_sex:
                if (sexPickerView != null){
                    sexPickerView.show();
                }
                break;

            case R.id.btn_occupation:
                intent = new Intent(this, ModifyOccupationActivity.class);
                break;

            case R.id.btn_school:
                intent = new Intent(this, ModifySchoolActivity.class);
                break;

            case R.id.btn_telephone:
                intent = new Intent(this, ModifyTelephoneActivity.class);
                break;
        }

        if (intent != null && userInfoBean != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable("userInfo", userInfoBean);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    private void initOptionPicker() {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        sexPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                userInfoBean.setSex(optionItems.get(options1));
                UpdateUserInfoRequest request = new UpdateUserInfoRequest();
                request.setUserInfoBean(userInfoBean);
                addRequest(getService(UserApi.class).updateUserInfo(request), new YQBDBaseCallBack() {
                    @Override
                    public void onSuccess200(Object o) {
                        sexTextView.setText(userInfoBean.getSex());
                    }
                });
            }
        })
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setSubmitText("确认")
                .setCancelText("取消")
                .setTitleBgColor(Color.LTGRAY)
                .setTitleColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .build();

        sexPickerView.setPicker(optionItems);
    }
}
