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

public class RvGoods_cardGoodsAdapter extends BaseQuickAdapter<CarGoodsEntity.ValuesBean,BaseViewHolder> {
    private ImageLoader loader;

    public RvGoods_cardGoodsAdapter(int layoutResId, @Nullable List<CarGoodsEntity.ValuesBean> data, ImageLoader loader) {
        super(layoutResId, data);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, CarGoodsEntity.ValuesBean item) {
        ImageView isSelected = helper.getView(R.id.isSelected_rvGoods_carGoods);
        helper.addOnClickListener(R.id.isSelected_rvGoods_carGoods);
        ImageView goodsImg = helper.getView(R.id.imgGoods_rvGoods_carGoods);
        if(item.getShop_select().equals("1"))
            isSelected.setImageResource(R.drawable.enter_pwd_not_select);
        else if(item.getShop_select().equals("2"))
            isSelected.setImageResource(R.drawable.enter_pwd_selected);
        loader.loadImage(mContext,ImageConfigImpl.builder()
        .url(Api.IMG_DOMAIN + item.getCar_path())
        .placeholder(R.drawable.share_list_placeholder_s_background)
        .errorPic(R.drawable.tt_default_image_error)
        .imageView(goodsImg)
        .build());
        helper.setText(R.id.goodsName_rvGoods_carGoods,item.getShop_name());
        helper.setText(R.id.num_rvGoods_cardGoods,"x " + item.getShop_num());
        helper.setText(R.id.color_rvGoods_cardGoods,item.getShop_color());
        helper.setText(R.id.size_rvGoods_cardGoods,item.getShop_size());
        helper.setText(R.id.price_rvGoods_cardGoods,"¥ " + item.getShop_price());

    }
}
