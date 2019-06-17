package com.bwie.servicemodule.controler;

import android.app.Activity;

import com.bwie.servicemodule.title.Eyes;

/**
 * 顶部状态栏颜色设置
 * */
public class StatuesBarControler {
    /**
     * 状态栏颜色设置
     * */
    public static void setStatusBarColor(Activity activity, int statusColor){
        Eyes.setStatusBarColor(activity,statusColor);
    }
    /**
     * 隐藏状态栏
     * */
    public static void translucentStatusBar(Activity activity) {
        Eyes.translucentStatusBar(activity, false);
    }
}
