package com.umeng.soexample.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.CategoryGoodsEntity;
import com.umeng.soexample.mvp.model.entity.GoodsActivityEntity;

public class RvGoodsActivityAdapter extends BaseQuickAdapter<GoodsActivityEntity.Values,BaseViewHolder> {
    private ImageLoader loader;
    public RvGoodsActivityAdapter(int layoutResId,ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsActivityEntity.Values item) {
        helper.setText(R.id.tv_goodsActivity,item.getGoods_name());
        helper.setText(R.id.tvPriceActivity,item.getGoods_discount_price());

        ImageView imageView = helper.getView(R.id.img_goodsActivity);
        loader.loadImage(mContext,ImageConfigImpl.builder()
                        .url(Api.IMG_DOMAIN + item.getGoods_img_path())
                        .placeholder(R.drawable.share_list_placeholder_s_background)
                        .errorPic(R.drawable.tt_default_image_error)
                        .imageView(imageView)
                        .build());
    }
}
