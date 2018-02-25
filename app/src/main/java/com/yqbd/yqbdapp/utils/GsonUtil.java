package com.yqbd.yqbdapp.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.bean.TaskBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtil {
    public static <T> T getEntity(Object o, Type type){
        T result = null;
        if (o instanceof YQBDBaseResponse){
            Gson gson = new Gson();
            String tmpstr = gson.toJson(((YQBDBaseResponse) o).obj);
            result = gson.fromJson(tmpstr,type);
        }
        return result;
    }

}
