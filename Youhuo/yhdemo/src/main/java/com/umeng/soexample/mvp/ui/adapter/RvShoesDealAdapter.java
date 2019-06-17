package com.umeng.soexample.mvp.ui.adapter;

import android.widget.ImageView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.ShoesDealEntity;

public class RvShoesDealAdapter extends BaseQuickAdapter<ShoesDealEntity.Values,BaseViewHolder> {
    private ImageLoader loader;
    public RvShoesDealAdapter(int layoutResId,ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoesDealEntity.Values item) {
        helper.setText(R.id.tv_rvShoesDeal,"¥" + item.getGoods_original_price());
        helper.setText(R.id.tv_nameRvShoesDeal,item.getGoods_name());
        ImageView imageView = helper.getView(R.id.img_rvShoesDeal);
        loader.loadImage(helper.itemView.getContext(),ImageConfigImpl.builder()
                .url(Api.IMG_DOMAIN + item.getGoods_img_path())
                .placeholder(R.drawable.share_list_placeholder_s_background)
                .errorPic(R.drawable.tt_default_image_error)
                .imageView(imageView)
                .build());
    }
}
