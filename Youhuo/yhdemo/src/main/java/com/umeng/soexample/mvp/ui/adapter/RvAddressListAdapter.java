package com.umeng.soexample.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.soexample.R;
import com.umeng.soexample.mvp.model.entity.AddressListEntity;

public class RvAddressListAdapter extends BaseQuickAdapter<AddressListEntity.Values,BaseViewHolder> {

    public RvAddressListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressListEntity.Values item) {
        helper.setText(R.id.nameAddress,item.getUser_name());
        helper.setText(R.id.signAddress,item.getAddress_tag());
        helper.setText(R.id.phoneAddress,item.getPhone());
        helper.setText(R.id.areaAddress,item.getAddress_area());
        helper.setText(R.id.detailAddress,item.getAddress_detail());
    }
}
