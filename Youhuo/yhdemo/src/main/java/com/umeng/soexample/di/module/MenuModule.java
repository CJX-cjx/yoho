package com.umeng.soexample.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.umeng.soexample.mvp.contract.MenuContract;
import com.umeng.soexample.mvp.model.Menu_model;

import dagger.Module;
import dagger.Provides;

@Module
public class MenuModule {
    private MenuContract.MenuView view;

    public MenuModule(MenuContract.MenuView menuView) {
        view = menuView;
    }

    @ActivityScope
    @Provides
    MenuContract.MenuView  getMenuView() {
        return view;
    }

    @ActivityScope
    @Provides
    MenuContract.MenuModel getMenuModel(Menu_model model) {
        return model;
    }
}
