package com.umeng.soexample.mvp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.mvp.model.api.Api;

import java.util.ArrayList;
import java.util.List;


public class Lv_goodsActivityAdapter extends BaseAdapter {
    private List<String> imgsUrl = new ArrayList<>();
    private ImageLoader loader;

    public Lv_goodsActivityAdapter(List<String> imgsUrl, ImageLoader loader) {
        this.imgsUrl.clear();
        this.imgsUrl.addAll(imgsUrl);
        this.loader = loader;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imgsUrl.size();
    }

    @Override
    public Object getItem(int position) {
        return imgsUrl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_goods_activity,null);
        }
        ImageView imageView = convertView.findViewById(R.id.img_lv_goodsActivity);
        loader.loadImage(parent.getContext(),ImageConfigImpl.builder()
        .url(imgsUrl.get(position))
        .placeholder(R.drawable.share_list_placeholder_s_background)
        .errorPic(R.drawable.tt_default_image_error)
        .imageView(imageView)
        .build());
        return convertView;
    }
}
