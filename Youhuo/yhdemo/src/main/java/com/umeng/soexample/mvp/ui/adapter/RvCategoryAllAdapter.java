package com.umeng.soexample.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.soexample.mvp.model.entity.CategoryAllEntity;

import java.util.ArrayList;
import java.util.List;

public class RvCategoryAllAdapter extends BaseQuickAdapter<CategoryAllEntity.Values,BaseViewHolder> {
    private List<TextView> textViewLines = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();
    private boolean isFirst = true;

    public RvCategoryAllAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryAllEntity.Values item) {
        TextView textline = helper.getView(R.id.line_rvLeft_category);
        textViewLines.add(textline);
        TextView text = helper.getView(R.id.tvContent_category);
        textViews.add(text);
        helper.setText(R.id.tvContent_category,item.getCategory_name());
        if(isFirst) {
            text.setBackgroundColor(0xffffffff);
            textline.setVisibility(View.VISIBLE);
            isFirst = false;
        }
    }

    public List<TextView> getTextViews() {
        return textViews;
    }

    public List<TextView> getTextViewLines() {
        return textViewLines;
    }
}
