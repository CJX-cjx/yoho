package com.umeng.soexample.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.CarGoodsEntity;

import java.util.List;

public class RvRecommend_cardGoodsAdapter extends BaseQuickAdapter<CarGoodsEntity.RecommendGoodsBean,BaseViewHolder> {
    private ImageLoader loader;

    public RvRecommend_cardGoodsAdapter(int layoutResId, @Nullable List<CarGoodsEntity.RecommendGoodsBean> data, ImageLoader loader) {
        super(layoutResId, data);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, CarGoodsEntity.RecommendGoodsBean item) {
        ImageView imageView = helper.getView(R.id.imgGoods_rvRecommend_cardGoods);
        loader.loadImage(mContext,ImageConfigImpl.builder()
        .url(Api.IMG_DOMAIN + item.getGoods_img_path())
        .placeholder(R.drawable.share_list_placeholder_s_background)
        .errorPic(R.drawable.tt_default_image_error)
        .imageView(imageView)
        .build());
        helper.setText(R.id.goodsIntroduce_rvRecommend_cardGoods,item.getGoods_name());
        helper.setText(R.id.price_rvRecommend_cardGoods,"¥ " + item.getGoods_original_price());
    }
}
