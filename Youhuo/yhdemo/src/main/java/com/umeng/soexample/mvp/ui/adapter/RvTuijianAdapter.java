package com.umeng.soexample.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.TuijianEntity;

public class RvTuijianAdapter extends BaseQuickAdapter<TuijianEntity.Recommend_values,BaseViewHolder> {
    private ImageLoaderUtils utils;

    public RvTuijianAdapter(int layoutResId,ImageLoaderUtils utils) {
        super(layoutResId);
        this.utils = utils;
    }

    @Override
    protected void convert(BaseViewHolder helper, TuijianEntity.Recommend_values item) {
        helper.setText(R.id.tv_tuijian,item.getRecommend_name());
        ImageView imageView = helper.getView(R.id.img_rvTuijian);
        if(utils != null) {
            utils.getImageLoader().loadImage(helper.itemView.getContext(), ImageConfigImpl
                    .builder()
                    .url(Api.IMG_DOMAIN + item.getRecommend_url())
                    .placeholder(R.drawable.share_list_placeholder_s_background)
                    .errorPic(R.drawable.tt_default_image_error)
                    .imageView(imageView)
                    .build());
        }
    }
}
