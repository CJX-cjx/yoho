package com.umeng.soexample.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.BrandActivityEntity;

public class Rv_brand_activityAdapter extends BaseQuickAdapter<BrandActivityEntity.Values.Goods,BaseViewHolder> {
    private ImageLoader loader;
    public Rv_brand_activityAdapter(int layoutResId, ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandActivityEntity.Values.Goods item) {
        helper.setText(R.id.tv_goodsName_brandActivity,item.getGoods_name());
        helper.setText(R.id.tvPrice_brandActivity,"¥ " + item.getGoods_original_price());
        ImageView imageView = helper.getView(R.id.img_rv_brand_activity);
        loader.loadImage(mContext,ImageConfigImpl.builder()
                    .url(Api.IMG_DOMAIN + item.getGoods_img_path())
                    .placeholder(R.drawable.share_list_placeholder_s_background)
                    .errorPic(R.drawable.tt_default_image_error)
                    .imageView(imageView)
                    .build());
    }
}
