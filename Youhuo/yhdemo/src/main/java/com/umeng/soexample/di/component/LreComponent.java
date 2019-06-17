package com.umeng.soexample.di.component;

import com.umeng.soexample.di.module.LreModule;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.umeng.soexample.mvp.ui.activity.AddNewAddressActivity;
import com.umeng.soexample.mvp.ui.activity.AddressActivity;
import com.umeng.soexample.mvp.ui.activity.BrandActivity;
import com.umeng.soexample.mvp.ui.activity.GoodsActivity;
import com.umeng.soexample.mvp.ui.activity.GoodsCarActivity;
import com.umeng.soexample.mvp.ui.activity.LoginActivity;
import com.umeng.soexample.mvp.ui.activity.OrderActivity;
import com.umeng.soexample.mvp.ui.activity.PersonalActivity;
import com.umeng.soexample.mvp.ui.activity.RegisterActivity;
import com.umeng.soexample.mvp.ui.fragment.CategoryFragment;
import com.umeng.soexample.mvp.ui.fragment.CommunityFragment;
import com.umeng.soexample.mvp.ui.fragment.GoodsActivityFragment;
import com.umeng.soexample.mvp.ui.fragment.LookLookCategoryFragment;
import com.umeng.soexample.mvp.ui.fragment.LookLookListFragment;
import com.umeng.soexample.mvp.ui.fragment.LookShowFragment;
import com.umeng.soexample.mvp.ui.fragment.MyFragment;
import com.umeng.soexample.mvp.ui.fragment.ShoesDealFragment;

import dagger.Component;

@ActivityScope
@Component(modules = {LreModule.class},dependencies = AppComponent.class)
public interface LreComponent {
    void inject(CategoryFragment categoty);

    void inject(ShoesDealFragment shoesDealFragment);

    void inject(LookLookCategoryFragment lookLookCategoryFragment);

    void inject(LookLookListFragment lookLookListFragment);

    void inject(CommunityFragment communityFragment);

    void inject(LookShowFragment lookShowFragment);

    void inject(MyFragment myFragment);

    void inject(GoodsActivityFragment goodsActivityFragment);

    void inject(BrandActivity brandActivity);

    void inject(GoodsActivity goodsActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(GoodsCarActivity goodsCarActivity);

    void inject(OrderActivity orderActivity);

    void inject(AddressActivity addressActivity);

    void inject(AddNewAddressActivity addNewAddressActivity);

    void inject(PersonalActivity personalActivity);
}
