package com.yqbd.yqbdapp.base;

import android.app.Application;
import com.lemon.support.request.RequestManager;
import okhttp3.Cookie;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;

/**
 * Created by yerunjie
 */

public class YQBDBaseApplication extends Application {
    public static YQBDBaseApplication sAppContext;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static boolean firstGetHighSpeed = true;
    public static Timer checkVpnStatusTimer;
    public final static boolean debug = true;
    public final static int second = 1000;
    public final static int minute = second * 60;
    public final static int hour = minute * 60;
    public final static int day = hour * 24;

    public void onCreate() {
        super.onCreate();
        sAppContext = this;
    }

    public static boolean isLogin() {
        List<Cookie> cookies = RequestManager.create(sAppContext).getCookieJar().getCookieStore().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.name().equals("X-TOKEN")) {
                return true;
            }
        }
        return false;
    }

}
