package com.bwie.servicemodule.http;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private Retrofit retrofit;
    private static NetworkManager manager;

    public NetworkManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl("http://api.yunzhancn.cn/api/");
        retrofitBuilder.client(builder.build());

        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit = retrofitBuilder.build();
    }

    public static synchronized NetworkManager getInstance() {
        if(manager == null) {
            synchronized (NetworkManager.class) {
                if(manager == null)
                    manager = new NetworkManager();
            }
        }
        return manager;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
