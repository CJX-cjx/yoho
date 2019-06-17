package com.umeng.soexample.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.umeng.soexample.mvp.contract.HomePageContract;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.BannerEntity;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.GoodsEntity;
import com.umeng.soexample.mvp.model.entity.TuijianEntity;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class HomePage_model extends BaseModel implements HomePageContract.HomePageIModel {

    @Inject
    public HomePage_model(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseEntity> getAllData(String... strings) {
        Observable<BannerEntity> bannerData = mRepositoryManager.obtainRetrofitService(Api.class).getBannerData();
        Observable<TuijianEntity> tuijianData = mRepositoryManager.obtainRetrofitService(Api.class).getTuijianData(strings[0]);
        Observable<GoodsEntity> goodsData = mRepositoryManager.obtainRetrofitService(Api.class).getGoodsData(strings[1]);
        return Observable.merge(bannerData,tuijianData,goodsData);
    }

    @Override
    public Observable<GoodsEntity> refresh(String params) {
        return mRepositoryManager.obtainRetrofitService(Api.class).getGoodsData(params);
    }

    @Override
    public Observable<GoodsEntity> loadMore(String params) {
        return mRepositoryManager.obtainRetrofitService(Api.class).getGoodsData(params);
    }
}
