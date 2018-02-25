package com.yqbd.yqbdapp.user.request;

import com.yqbd.yqbdapp.base.YQBDBaseRequest;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateUserInfoRequest extends YQBDBaseRequest {
    private UserInfoBean userInfoBean;
}
