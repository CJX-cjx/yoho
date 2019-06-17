package com.umeng.soexample.mvp.ui.widget;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class VpTransform implements ViewPager.PageTransformer{
    private static final float MIN_SCALE = 0.7f;

    @Override
    public void transformPage(View page, float position) {
//        Log.e("cjx", "position:" + "$$$$$" + position);
        if (position >= -0.5 && position <= 1.5) {

            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (1 - MIN_SCALE);
            scaleFactor = Math.max(MIN_SCALE, scaleFactor);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
//            Log.e("cjx", "scaleFactor:" + scaleFactor);
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
