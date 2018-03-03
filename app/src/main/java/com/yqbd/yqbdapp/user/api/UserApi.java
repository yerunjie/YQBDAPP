package com.yqbd.yqbdapp.user.api;

import com.lemon.support.request.SimpleCall;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.user.request.UncollectTaskRequest;
import com.yqbd.yqbdapp.user.request.UpdateUserInfoRequest;
import com.yqbd.yqbdapp.user.request.UserTakeRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("task/getAcceptTasks")
    SimpleCall<YQBDBaseResponse> getAcceptTasks();

    @POST("task/getAllTasks")
    SimpleCall<YQBDBaseResponse> getAllTasks();

    @POST("task/getCollectedTasks")
    SimpleCall<YQBDBaseResponse> getCollectedTasks();

    @POST("task/uncollectedTasks")
    SimpleCall<YQBDBaseResponse> uncollectedTasks(@Body UncollectTaskRequest request);

    @POST("task/take")
    SimpleCall<YQBDBaseResponse> takeTasks(@Query("taskId") int taskId, @Query("userId") int userId);

    @POST("task/isTake")
    SimpleCall<YQBDBaseResponse> isTake(@Query("taskId") int taskId, @Query("userId") int userId);

    @POST("user/getUserInfoByUserID")
    SimpleCall<YQBDBaseResponse> getUserInfo();

    @POST("user/updateUserInfo")
    SimpleCall<YQBDBaseResponse> updateUserInfo(@Body UpdateUserInfoRequest request);


}
