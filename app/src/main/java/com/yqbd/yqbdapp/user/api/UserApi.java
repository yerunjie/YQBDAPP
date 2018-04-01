package com.yqbd.yqbdapp.user.api;

import com.lemon.support.request.SimpleCall;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.SingleResultBean;
import com.yqbd.yqbdapp.user.request.TaskRequest;
import com.yqbd.yqbdapp.user.request.UncollectTaskRequest;
import com.yqbd.yqbdapp.user.request.UpdateUserInfoRequest;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    SimpleCall<YQBDBaseResponse<SingleResultBean>> isTake(@Query("taskId") int taskId, @Query("userId") int userId);

    @POST("user/getUserInfoByUserID")
    SimpleCall<YQBDBaseResponse> getUserInfo();

    @POST("user/updateUserInfo")
    SimpleCall<YQBDBaseResponse> updateUserInfo(@Body UpdateUserInfoRequest request);

    @POST("task/publishTask")
    SimpleCall<YQBDBaseResponse> publishTask(@Body TaskRequest request);

    @POST("task/isCollected")
    SimpleCall<YQBDBaseResponse<SingleResultBean>> isCollected(@Query("taskId") int taskId, @Query("userId") int userId);

    @POST("task/collect")
    SimpleCall<YQBDBaseResponse<SingleResultBean>> collect(@Query("taskId") int taskId, @Query("userId") int userId);

    @GET("task/tasks")
    SimpleCall<YQBDBaseResponse> getTasks(@Query("page_size") int pageSize, @Query("page_number") int pageNumber);
}
