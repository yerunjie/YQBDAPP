package com.yqbd.yqbdapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseApplication;
import com.yqbd.yqbdapp.main.activity.MainActivity;


/**
 * Created by yerunjie on 18/1/14.
 */

public class SplashActivity extends YQBDBaseActivity {
    private static final int SPLASH_TIME = 2000;
    private static final int WHAT_SPLASH = 0;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_SPLASH:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    break;
            }
        }
    };
    private LinearLayout ll_splash_btn_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.splash_activity_splash);
        ll_splash_btn_group = (LinearLayout) findViewById(R.id.ll_splash_btn_group);
        hideTitleBar();
        if (YQBDBaseApplication.isLogin()) {
            mHandler.sendEmptyMessageDelayed(WHAT_SPLASH, SPLASH_TIME);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(SPLASH_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ll_splash_btn_group.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
