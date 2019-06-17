package com.umeng.soexample.mvp.ui.widget;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.umeng.soexample.R;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.umeng.soexample.mvp.model.api.Api;
import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {
    private AppComponent appComponent;
    private com.jess.arms.http.imageloader.ImageLoader imageLoader;
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if(appComponent == null) {
            appComponent = ArmsUtils.obtainAppComponentFromContext(context);
            imageLoader = appComponent.imageLoader();
        }
        String url = Api.IMG_DOMAIN + path;
        imageLoader.loadImage(context,ImageConfigImpl.builder()
                .url(url)
                .placeholder(R.drawable.share_list_placeholder_s_background)
                .errorPic(R.drawable.tt_default_image_error)
                .imageView(imageView)
                .build());
    }
}
