package com.bwie.servicemodule;

import android.app.Application;
import android.os.Looper;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bwie.servicemodule.controler.OkGoControler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.orm.SugarApp;
import com.orm.SugarContext;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ZXY","APP->onCreate()");
        initOkGo();
        Utils.init(this);
        SugarContext.init(this);
    }

    //初始化OkGo方法
    private void initOkGo(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(1, TimeUnit.MINUTES);
        builder.writeTimeout(1,TimeUnit.MINUTES);
        builder.connectTimeout(1,TimeUnit.MINUTES);
        OkGo.getInstance().init(this).setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE);
    }


    // 清除图片磁盘缓存，调用Glide自带方法
    public boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(App.this).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(this).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(this).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 退出程序杀掉进程
     */
    public static void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
