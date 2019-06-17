package com.umeng.soexample.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.umeng.soexample.mvp.contract.MenuContract;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.MenuEntity;
import com.umeng.soexample.mvp.model.entity.UpdateEntity;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class Menu_model extends BaseModel implements MenuContract.MenuModel {

    @Inject
    public Menu_model(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<MenuEntity> getMenu() {
        return mRepositoryManager.obtainRetrofitService(Api.class).getMenu();
    }

    @Override
    public Observable<UpdateEntity> postData(String params) {
        return mRepositoryManager.obtainRetrofitService(Api.class).postUpdateProduct(params);
    }
}
