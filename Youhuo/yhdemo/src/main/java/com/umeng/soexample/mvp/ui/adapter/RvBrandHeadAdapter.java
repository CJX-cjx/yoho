package com.umeng.soexample.mvp.ui.adapter;

import android.util.Log;
import android.widget.ImageView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.BrandEntity;

public class RvBrandHeadAdapter extends BaseQuickAdapter<BrandEntity.Values,BaseViewHolder> {
    private ImageLoader loader;
    public RvBrandHeadAdapter(int layoutResId, ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandEntity.Values item) {
        helper.setText(R.id.tvBrandHead,item.getBrand_name());
        ImageView imageView = helper.getView(R.id.img_rvBrandHead);
        loader.loadImage(helper.itemView.getContext(),ImageConfigImpl
                    .builder()
                    .url(Api.IMG_DOMAIN + item.getBrand_icon())
                    .placeholder(R.drawable.share_list_placeholder_s_background)
                    .errorPic(R.drawable.tt_default_image_error)
                    .imageView(imageView)
                    .build());
    }
}
