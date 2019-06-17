package com.umeng.soexample.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.LreModel;

import dagger.Module;
import dagger.Provides;

@Module
public class LreModule {
    private LreContract.LreIView view;

    public LreModule(LreContract.LreIView view) {this.view = view;}

    @ActivityScope
    @Provides
    LreContract.LreIView providersView() {return view;}

    @ActivityScope
    @Provides
    LreContract.LreIModel providersModel(LreModel model) {return model;}
}
