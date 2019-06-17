package com.umeng.soexample.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

public class MakeCircleImageUtils {
    private ImageView imageView;
    private Context context;
    public MakeCircleImageUtils(ImageView imageView) {
        this.imageView = imageView;
        context = imageView.getContext();
    }

    public void set(Bitmap bitmap) {
        if(context != null && bitmap != null) {
            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
            drawable.setCircular(true);
            imageView.setImageDrawable(drawable);
        }
        Log.e("cjx","null");
    }
}
