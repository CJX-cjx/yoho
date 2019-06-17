package com.bwie.servicemodule.controler;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bwie.servicemodule.deamon.GlideConfig;
import com.bwie.servicemodule.glideutils.GlideCircleTransform;
import com.bwie.servicemodule.glideutils.CornerTransform;

/**
 * 图片异步加载工具类
 * */
public class GlideControler {

    /**
     * 按照控件宽高加载图片
     * */
    public void loadMatchImg(Context ctx, String path, ImageView img){
        Glide.with(ctx).load(path)
                .placeholder(GlideConfig.HOLDER)
                .error(GlideConfig.ERRO)
                .centerCrop()
                .into(img);
    }
    /**
     * 按照控件宽高缩放加载图片
     * */
    public void loadImg(Context ctx, String path, ImageView img){
        Glide.with(ctx).load(path).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(GlideConfig.HOLDER).error(GlideConfig.ERRO)
                .fitCenter().into(img);
    }
    /**
     * 加载圆角图片
     * */
    public void loadRoundImg(Context ctx, String path, ImageView img){
        CornerTransform ct = new CornerTransform(
                ctx,CornerTransform.dip2px(ctx,10));
        ct.setExceptCorner(false,false,
                false,false);
        Glide.with(ctx).load(path).asBitmap()
                .skipMemoryCache(true).transform(ct)
                .placeholder(GlideConfig.HOLDER).error(GlideConfig.ERRO)
                .into(img);
    }
    /**
     * 加载圆形状图片
     * */
    public void loadCircleImg(Context ctx, String path, ImageView img){
        Glide.with(ctx).load(path).asBitmap().placeholder(GlideConfig.HOLDER)
                .error(GlideConfig.ERRO).centerCrop().into(new GlideCircleTransform(img));
    }
    /**
     * 加载Gif图片
     * */
    public void loadGifImg(Context ctx, String path, ImageView img){
        Glide.with(ctx).load(path).asGif()
                .placeholder(GlideConfig.HOLDER).error(GlideConfig.ERRO)
                .centerCrop().into(img);
    }
}
