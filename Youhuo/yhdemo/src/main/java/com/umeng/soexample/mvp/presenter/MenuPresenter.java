package com.umeng.soexample.mvp.presenter;

import android.annotation.SuppressLint;
import android.util.Log;


import com.jess.arms.mvp.BasePresenter;
import com.umeng.soexample.mvp.contract.MenuContract;
import com.umeng.soexample.mvp.model.entity.MenuEntity;
import com.umeng.soexample.mvp.model.entity.UpdateEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MenuPresenter extends BasePresenter<MenuContract.MenuModel,MenuContract.MenuView> {

    @Inject
    public MenuPresenter(MenuContract.MenuModel menuModel, MenuContract.MenuView menuView) {
        super(menuModel,menuView);
    }

    @SuppressLint("CheckResult")
    public void getMenu() {
        mModel.getMenu().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MenuEntity>() {
                    @Override
                    public void accept(MenuEntity menuEntity) throws Exception {
                        List<MenuEntity.Values> values = menuEntity.getValues();
                        mRootView.success(menuEntity);
                    }
                });
    }

    public void update(String params) {
        Log.e("cjx","update:" + params);
        mModel.postData(params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpdateEntity updateEntity) {
                        if(updateEntity != null)
                            mRootView.getData(updateEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cjx","error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
