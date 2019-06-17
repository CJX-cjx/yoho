package com.umeng.soexample.mvp.ui.adapter;


import android.widget.ImageView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.CategoryGoodsEntity;

public class RvCategoryGoodsAdapter extends BaseQuickAdapter<CategoryGoodsEntity.Values,BaseViewHolder> {
    private ImageLoader loader;
    public RvCategoryGoodsAdapter(int layoutResId,ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryGoodsEntity.Values item) {
        helper.setText(R.id.tvCateGoryGoods,item.getName());
        ImageView imageView = helper.getView(R.id.img_rvCategoryGoods);
        loader.loadImage(helper.itemView.getContext(),ImageConfigImpl
            .builder()
            .url(Api.IMG_DOMAIN + item.getImage_path())
            .placeholder(R.drawable.share_list_placeholder_s_background)
            .errorPic(R.drawable.tt_default_image_error)
            .imageView(imageView)
            .build());
    }
}
