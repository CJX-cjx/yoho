package com.umeng.soexample.mvp.ui.adapter;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.soexample.mvp.model.entity.BrandEntity;

public class RvBrandCategoryAdapter extends BaseQuickAdapter<BrandEntity.Values,BaseViewHolder> {
    public RvBrandCategoryAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, BrandEntity.Values item) {
        helper.setText(R.id.tvTltle_brandRv,item.getBrand_letter());
        helper.setText(R.id.tvBody_brandRv,item.getBrand_name());
        helper.addOnClickListener(R.id.tvBody_brandRv);
    }
}
