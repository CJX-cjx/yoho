package com.umeng.soexample.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.soexample.mvp.model.entity.MenuEntity;

import java.util.List;

public class MenuAdapter extends BaseQuickAdapter<MenuEntity.Values,BaseViewHolder> {


    public MenuAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuEntity.Values item) {
        helper.setText(R.id.tv_item_slide,item.getMenu_name());
    }
}
