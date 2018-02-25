package com.yqbd.yqbdapp.main.api;

import com.lemon.support.request.SimpleCall;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.login.request.UserLoginRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yerunjie on 18/1/14.
 */

public interface MainApi {
    //登录
    @POST("user/getUserInfoByUserID")
    SimpleCall<YQBDBaseResponse<UserInfoBean>> getUserInfo();


}
