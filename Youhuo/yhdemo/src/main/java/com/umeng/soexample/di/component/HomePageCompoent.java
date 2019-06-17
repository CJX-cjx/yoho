package com.umeng.soexample.di.component;

import com.umeng.soexample.di.module.HomePageModule;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.umeng.soexample.mvp.ui.fragment.HomePageFragment;

import dagger.Component;

@ActivityScope
@Component(modules = HomePageModule.class,dependencies = AppComponent.class)
public interface HomePageCompoent {
    void inJect(HomePageFragment homePageFragment);
}
