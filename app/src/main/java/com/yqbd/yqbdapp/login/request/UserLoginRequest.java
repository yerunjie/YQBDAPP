package com.yqbd.yqbdapp.login.request;

import com.yqbd.yqbdapp.base.YQBDBaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by yerunjie on 2018/2/15
 *
 * @author yerunjie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserLoginRequest extends YQBDBaseRequest {
    private String accountNumber;
    private String userPassword;
}
