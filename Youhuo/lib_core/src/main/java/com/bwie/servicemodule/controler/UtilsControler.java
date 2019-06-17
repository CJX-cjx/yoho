package com.bwie.servicemodule.controler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

public class UtilsControler {

    /**
     * dp转px
     * */
     public int dp2px(float dp){
         return SizeUtils.dp2px(dp);
     }

    /**
     * sp转px
     * */
    public int sp2px(float sp){
        return SizeUtils.sp2px(sp);
    }

    /**
     * 截屏
     * */
    public Bitmap screenShot(Activity activity){
        return ScreenUtils.screenShot(activity);
    }

    /**
     * view转bitmap
     * */
     public Bitmap view2Bitmap(View view){
         return ImageUtils.view2Bitmap(view);
     }
}
