package com.yqbd.yqbdapp.user.request;

import android.content.Intent;
import com.yqbd.yqbdapp.base.YQBDBaseRequest;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserTakeRequest extends YQBDBaseRequest {
    private int taskId;
    private int userId;
}
