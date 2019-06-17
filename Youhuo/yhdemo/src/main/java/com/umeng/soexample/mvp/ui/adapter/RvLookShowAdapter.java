package com.umeng.soexample.mvp.ui.adapter;

import android.widget.ImageView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.LookShowEntity;

public class RvLookShowAdapter extends BaseQuickAdapter<LookShowEntity.Values,BaseViewHolder> {
    private ImageLoader loader;
    public RvLookShowAdapter(int layoutResId,ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, LookShowEntity.Values item) {
        helper.setText(R.id.nickName_lookShow,item.getNick_name());
        helper.setText(R.id.goodsName,item.getGoods_name());
        helper.setText(R.id.priceLookShow,item.getGoods_discount_price());
        helper.setText(R.id.ZanNumLookShow,item.getFabulous());

        ImageView nickIcon = helper.getView(R.id.cIv_look_show);
        ImageView imgShow = helper.getView(R.id.imgShow);
        ImageView imgGoods = helper.getView(R.id.goodsImgShow);

        loader.loadImage(mContext,ImageConfigImpl.builder()
                                                .url(Api.IMG_DOMAIN + item.getUser_head())
                                                .placeholder(R.drawable.share_list_placeholder_s_background)
                                                .errorPic(R.drawable.tt_default_image_error)
                                                .imageView(nickIcon)
                                                .build());

        loader.loadImage(mContext,ImageConfigImpl.builder()
                .url(Api.IMG_DOMAIN + item.getImg_path())
                .placeholder(R.drawable.share_list_placeholder_s_background)
                .errorPic(R.drawable.tt_default_image_error)
                .imageView(imgShow)
                .build());

        loader.loadImage(mContext,ImageConfigImpl.builder()
                .url(Api.IMG_DOMAIN + item.getGoods_img_path())
                .placeholder(R.drawable.share_list_placeholder_s_background)
                .errorPic(R.drawable.tt_default_image_error)
                .imageView(imgGoods)
                .build());

    }
}
