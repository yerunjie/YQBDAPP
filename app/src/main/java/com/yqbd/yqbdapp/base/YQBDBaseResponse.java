package com.yqbd.yqbdapp.base;

/**
 * Created by yerunjie on 2018/1/13
 *
 * @author yerunjie
 */
public class YQBDBaseResponse<T> {
    public String returnCode;
    public String errorMessage;
    public T obj;

    public boolean isOk() {
        return !returnCode.contains("E");
    }

    public boolean isExpired(){
        // TODO: 2018/1/20 还不知道返回码是什么
        return returnCode.equals("0");
    }
}
