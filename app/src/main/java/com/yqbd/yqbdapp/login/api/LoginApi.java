package com.yqbd.yqbdapp.login.api;

import com.lemon.support.request.SimpleCall;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.login.request.UserLoginRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yerunjie on 17/11/22.
 */

public interface LoginApi {
    //登录
    @POST("user/login")
    SimpleCall<YQBDBaseResponse> doLogin(@Body UserLoginRequest request);

}
