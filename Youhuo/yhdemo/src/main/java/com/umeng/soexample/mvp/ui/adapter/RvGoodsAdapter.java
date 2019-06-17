package com.umeng.soexample.mvp.ui.adapter;

import android.widget.ImageView;

import com.umeng.soexample.R;
import com.blankj.utilcode.util.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.GoodsEntity;

public class RvGoodsAdapter extends BaseQuickAdapter<GoodsEntity.Values,BaseViewHolder> {
    private ImageLoaderUtils utils;

    public RvGoodsAdapter(int layoutResId, ImageLoaderUtils utils) {
        super(layoutResId);
        this.utils = utils;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsEntity.Values item) {
        helper.setText(R.id.tv_introduce_rvGoods,item.getGoods_name());
        helper.setText(R.id.tvPrice_rvGoods,"¥ " + item.getGoods_discount_price());
        helper.setText(R.id.tvOriginalPrice_rvGoods,"¥ " + item.getGoods_original_price());
        ImageView imageView = helper.getView(R.id.img_rvGoods);

        if(utils != null) {
            utils.getImageLoader().loadImage(helper.itemView.getContext(),ImageConfigImpl
                    .builder()
                    .url(Api.IMG_DOMAIN + item.getGoods_img_path())
                    .placeholder(R.drawable.share_list_placeholder_s_background)
                    .errorPic(R.drawable.tt_default_image_error)
                    .imageView(imageView)
                    .build());
        }
    }
}
