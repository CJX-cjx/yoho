package com.jwenfeng.library.pulltorefresh.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jwenfeng.library.R;

public class HeadRefreshView_youHuo extends FrameLayout implements HeadView {
    private View view;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    public HeadRefreshView_youHuo(Context context) {
        this(context,null);
    }

    public HeadRefreshView_youHuo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_header_youhuo, null);
        addView(view);
        imageView = view.findViewById(R.id.img_youhuo);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
    }

    @Override
    public void begin() {

    }

    @Override
    public void progress(float progress, float all) {

    }

    @Override
    public void finishing(float progress, float all) {
        animationDrawable.stop();
    }

    @Override
    public void loading() {
        animationDrawable.start();
    }

    @Override
    public void normal() {

    }

    @Override
    public View getView() {
        return this;
    }
}
