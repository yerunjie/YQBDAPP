package com.yqbd.yqbdapp.user.api;

import com.lemon.support.request.SimpleCall;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.user.request.UncollectTaskRequest;
import com.yqbd.yqbdapp.user.request.UpdateUserInfoRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("task/getAcceptTasks")
    SimpleCall<YQBDBaseResponse> getAcceptTasks();


    @POST("task/getCollectedTasks")
    SimpleCall<YQBDBaseResponse> getCollectedTasks();

    @POST("task/uncollectedTasks")
    SimpleCall<YQBDBaseResponse> uncollectedTasks(@Body UncollectTaskRequest request);

    @POST("user/getUserInfoByUserID")
    SimpleCall<YQBDBaseResponse> getUserInfo();

    @POST("user/updateUserInfo")
    SimpleCall<YQBDBaseResponse> updateUserInfo(@Body UpdateUserInfoRequest request);
}
