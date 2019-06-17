package com.umeng.soexample.mvp.ui.adapter;

import android.widget.ImageView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.ShoesDealEntity;

public class RvShoesDealHeadAdapter extends BaseQuickAdapter<ShoesDealEntity.Brand,BaseViewHolder> {
    private ImageLoader loader;
    public RvShoesDealHeadAdapter(int layoutResId,ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoesDealEntity.Brand item) {
        helper.setText(R.id.tvRv_shoesDealHead,item.getBrand_name());
        ImageView imageView = helper.getView(R.id.img_rv_shoesDealHead);
        loader.loadImage(helper.itemView.getContext(),ImageConfigImpl
                .builder()
                .url(Api.IMG_DOMAIN + item.getBrand_icon())
                .placeholder(R.drawable.share_list_placeholder_s_background)
                .errorPic(R.drawable.tt_default_image_error)
                .imageView(imageView)
                .build());
    }
}
