package com.bwie.servicemodule.glideutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Glide 圆形图片 Transform
 */

public class GlideCircleTransform extends BitmapImageViewTarget {

    ImageView img;
    Context ctx;

    public GlideCircleTransform(ImageView view) {
        super(view);
        img = view;
        ctx = view.getContext();
    }

    @Override
    protected void setResource(Bitmap resource) {
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(ctx.getResources(), resource);
        circularBitmapDrawable.setCircular(true);
        img.setImageDrawable(circularBitmapDrawable);
    }
}