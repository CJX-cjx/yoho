package com.umeng.soexample.app.utils;

import android.content.Context;

public class DpUtils {

    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context,float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(spValue * scale + 0.5f);
    }

    public static int px2sp(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
}
