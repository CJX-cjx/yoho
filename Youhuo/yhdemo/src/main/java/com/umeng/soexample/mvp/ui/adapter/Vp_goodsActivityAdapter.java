package com.umeng.soexample.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.mvp.model.api.Api;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class Vp_goodsActivityAdapter extends PagerAdapter {
    private List<String> imgsUrl = new ArrayList<>();
    private ImageLoader loader;
    public Vp_goodsActivityAdapter(List<String> imgsUrl, ImageLoader loader) {
        this.imgsUrl.clear();
        this.imgsUrl.addAll(imgsUrl);
        this.loader = loader;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imgsUrl.size() + 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.item_vp_goods_activity, container, false);
        if(position == imgsUrl.size()) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(R.drawable.change_tab);
            return imageView;
        }else {
            PhotoView photoView = new PhotoView(container.getContext());
            loader.loadImage(container.getContext(), ImageConfigImpl.builder()
                    .url(imgsUrl.get(position))
                    .placeholder(R.drawable.share_list_placeholder_s_background)
                    .errorPic(R.drawable.tt_default_image_error)
                    .imageView(photoView)
                    .build());
            container.addView(photoView);
            return photoView;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
