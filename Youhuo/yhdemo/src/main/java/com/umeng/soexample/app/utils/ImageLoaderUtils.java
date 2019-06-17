package com.umeng.soexample.app.utils;

import android.content.Context;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

public class ImageLoaderUtils {
    private AppComponent appComponent;
    private Context context;

    public ImageLoaderUtils(Context context) {
        this.context = context;
    }

    public ImageLoader getImageLoader() {
        if(appComponent == null && context != null) {
            appComponent = ArmsUtils.obtainAppComponentFromContext(context);
        }
        return appComponent.imageLoader();
    }
}
