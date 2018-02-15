package com.yqbd.yqbdapp.base;

import android.content.Context;
import android.text.TextUtils;
import com.lemon.support.request.SimpleCallBack;
import com.lemon.support.util.ToastUtils;
import com.yqbd.yqbdapp.R;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yerunjie on 18/1/13.
 */

public abstract class YQBDBaseCallBack<T> implements SimpleCallBack<T> {
    private Context mContext = YQBDBaseApplication.sAppContext;

    @Override
    public void onSuccess(T t) {
        if (t instanceof YQBDBaseResponse) {
            YQBDBaseResponse m = (YQBDBaseResponse) t;
            if (((YQBDBaseResponse) t).isOk()) {// 请求处理成功
                onSuccess200(t);
            } else if ("-1".equals(m.returnCode)) {// todo 登录超时
                ToastUtils.show(mContext, R.string.app_request_unlogin);
            } else {
                ToastUtils.show(mContext, m.errorMessage);
            }
        } else if (t instanceof JSONObject) {
            JSONObject jo = (JSONObject) t;
            String status = "";
            String msg = "";
            try {
                status = jo.getString("status");
                msg = jo.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ("success".equals(status)) {
                onSuccess200(t);
            } else if (!TextUtils.isEmpty(msg)) {
                ToastUtils.show(mContext, msg);
            }
        }
        onFinish(t);
    }

    @Override
    public void onFailure(Object o) {
        ToastUtils.show(mContext, R.string.app_request_fail);
    }

    @Override
    public void onError(Exception e) {
        ToastUtils.show(mContext, R.string.app_request_error);
    }

    /**
     * override for custom case when http success
     *
     * @param t
     */
    public void onFinish(T t) {

    }

    public abstract void onSuccess200(T t);
}
