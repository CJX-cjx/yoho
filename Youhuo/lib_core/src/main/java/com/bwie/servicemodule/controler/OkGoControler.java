package com.bwie.servicemodule.controler;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;

public class OkGoControler {

    private WeakReference<HttpListener> listener;

    public void setListener(HttpListener listener) {
        this.listener=new WeakReference<HttpListener>(listener);
    }

    //发起get请求
    public void getUtils(String url){
        OkGo.<String>get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (listener==null)
                    return;
                String urlStr = response.getRawCall().request().url().toString();
                String result;
                if (response!=null) {
                    result = response.body();
                    listener.get().onSuccess(urlStr,result);
                }else {
                    result = "请求出错";
                    listener.get().onErro(urlStr,result);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if (listener==null)
                    return;
                String urlStr = response.getRawCall().request().url().toString();
                String result;
                if (response!=null) {
                    result = response.body();
                    listener.get().onSuccess(urlStr,result);
                }else {
                    result = "请求出错";
                    listener.get().onErro(urlStr,result);
                }
            }
        });
    }
    //发起post请求
    public void postUtils(String url, HttpParams params){
        OkGo.<String>post(url).params(params).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (listener==null)
                    return;
                String urlStr = response.getRawCall().request().url().toString();
                String result;
                if (response!=null) {
                    result = response.body();
                    listener.get().onSuccess(urlStr,result);
                }else {
                    result = "请求出错";
                    listener.get().onErro(urlStr,result);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if (listener==null)
                    return;
                String urlStr = response.getRawCall().request().url().toString();
                String result;
                if (response!=null) {
                    result = response.body();
                    listener.get().onSuccess(urlStr,result);
                }else {
                    result = "请求出错";
                    listener.get().onErro(urlStr,result);
                }
            }
        });
    }
    //图片上传
    public void bitmapUtils(String url,File imgFile){
        OkGo.<String>post(url).headers("Connection","close").
                headers("Content-Type", "multipart/form-data")
                .params("uploadedfile", imgFile).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (listener==null)
                    return;
                String urlStr = response.getRawCall().request().url().toString();
                String result;
                if (response!=null) {
                    result = response.body();
                    listener.get().onSuccess(urlStr,result);
                }else {
                    result = "请求出错";
                    listener.get().onErro(urlStr,result);
                }
            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if (listener==null)
                    return;
                String urlStr = response.getRawCall().request().url().toString();
                String result;
                if (response!=null) {
                    result = response.body();
                    listener.get().onSuccess(urlStr,result);
                }else {
                    result = "请求出错";
                    listener.get().onErro(urlStr,result);
                }
            }
        });
    }

    public interface HttpListener{
        void onSuccess(String url,String result);
        void onErro(String url,String msg);
    }

}
