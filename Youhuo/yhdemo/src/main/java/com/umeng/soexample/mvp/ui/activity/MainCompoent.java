package com.umeng.soexample.mvp.ui.activity;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.umeng.soexample.di.module.MenuModule;

import dagger.Component;

@ActivityScope
@Component(modules = MenuModule.class,dependencies = AppComponent.class)
public interface MainCompoent {
    void inject(MainActivity mainActivity);
}
