package com.umeng.soexample.mvc.ui.activity;

import android.graphics.Color;
import android.os.SystemClock;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.StatusUtils;
import com.umeng.soexample.mvc.base.BaseActivity;
import com.umeng.soexample.mvp.ui.activity.MainActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        StatusUtils.setStatusBarBackground(this,Color.BLACK);
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SplashActivity.this.start(MainActivity.class);
                    }
                });
            }
        }.start();
    }

    @Override
    protected void setListener() {

    }
}
