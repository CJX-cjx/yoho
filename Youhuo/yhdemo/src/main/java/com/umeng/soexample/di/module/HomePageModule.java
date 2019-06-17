package com.umeng.soexample.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.umeng.soexample.mvp.contract.HomePageContract;
import com.umeng.soexample.mvp.model.HomePage_model;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class HomePageModule {

    private HomePageContract.HomePageIView iView;

    public HomePageModule(HomePageContract.HomePageIView iView) {this.iView = iView;}

    @ActivityScope
    @Provides
    HomePageContract.HomePageIView getView() {
        return iView;
    }

    @ActivityScope
    @Provides
    HomePageContract.HomePageIModel getModel(HomePage_model homePage_model) {return homePage_model;}
}
