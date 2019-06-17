package com.umeng.soexample.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

public class VpHeadCommunityAdapter extends PagerAdapter {
    private List<String> imgList = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private ImageLoader loader;

    public VpHeadCommunityAdapter(Context context) {
        loader = new ImageLoaderUtils(context).getImageLoader();
        imgList.add("https://b279.photo.store.qq.com/psb?/V13z4upL11DU8W/TQfvX9ciNvVpNZxCFxYNIUvzi1GhIAcYO00KevEXD2c!/m/dBcBAAAAAAAAnull&bo=hAMgAwAAAAARB5Q!&rf=photolist&t=5");
        imgList.add("https://b279.photo.store.qq.com/psb?/V13z4upL11DU8W/TQfvX9ciNvVpNZxCFxYNIUvzi1GhIAcYO00KevEXD2c!/m/dBcBAAAAAAAAnull&bo=hAMgAwAAAAARB5Q!&rf=photolist&t=5");
        imgList.add("https://b279.photo.store.qq.com/psb?/V13z4upL11DU8W/TQfvX9ciNvVpNZxCFxYNIUvzi1GhIAcYO00KevEXD2c!/m/dBcBAAAAAAAAnull&bo=hAMgAwAAAAARB5Q!&rf=photolist&t=5");
        imgList.add("https://b279.photo.store.qq.com/psb?/V13z4upL11DU8W/TQfvX9ciNvVpNZxCFxYNIUvzi1GhIAcYO00KevEXD2c!/m/dBcBAAAAAAAAnull&bo=hAMgAwAAAAARB5Q!&rf=photolist&t=5");
        imgList.add("https://b279.photo.store.qq.com/psb?/V13z4upL11DU8W/TQfvX9ciNvVpNZxCFxYNIUvzi1GhIAcYO00KevEXD2c!/m/dBcBAAAAAAAAnull&bo=hAMgAwAAAAARB5Q!&rf=photolist&t=5");
        imgList.add("https://b279.photo.store.qq.com/psb?/V13z4upL11DU8W/TQfvX9ciNvVpNZxCFxYNIUvzi1GhIAcYO00KevEXD2c!/m/dBcBAAAAAAAAnull&bo=hAMgAwAAAAARB5Q!&rf=photolist&t=5");
        ImageView imageView1 = new ImageView(context);
        ImageView imageView2 = new ImageView(context);
        ImageView imageView3 = new ImageView(context);
        ImageView imageView4 = new ImageView(context);
        ImageView imageView5 = new ImageView(context);
        ImageView imageView6 = new ImageView(context);
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);
        imageViews.add(imageView5);
        imageViews.add(imageView6);
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        loader.loadImage(container.getContext(),ImageConfigImpl.builder()
        .url(imgList.get(position))
        .placeholder(R.drawable.share_list_placeholder_s_background)
        .errorPic(R.drawable.tt_default_image_error)
        .imageView(imageViews.get(position))
        .build());
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(imageViews.get(position));
    }
}
