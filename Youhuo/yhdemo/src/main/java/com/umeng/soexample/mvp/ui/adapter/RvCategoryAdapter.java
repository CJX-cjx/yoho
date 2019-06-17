package com.umeng.soexample.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.soexample.mvp.model.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class RvCategoryAdapter extends BaseQuickAdapter<CategoryEntity,BaseViewHolder> {
    private List<TextView> textViews = new ArrayList<>();
    private List<TextView> textLines = new ArrayList<>();
    private boolean isFirst = true;

    public RvCategoryAdapter(int layoutResId, @Nullable List<CategoryEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryEntity item) {
        TextView textView = helper.getView(R.id.tv_category);
        textViews.add(textView);
        helper.setText(R.id.tv_category,item.getName());
        TextView line = helper.getView(R.id.line_category);
        if(isFirst) {
            textView.setTextColor(0xff000000);
            line.setVisibility(View.VISIBLE);
            isFirst = false;
        }
        textLines.add(line);
    }

    public List<TextView> getTextViews() {
        return textViews;
    }

    public List<TextView> getTextLines() {
        return textLines;
    }
}
