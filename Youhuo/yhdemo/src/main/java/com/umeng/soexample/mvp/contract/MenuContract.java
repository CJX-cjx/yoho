package com.umeng.soexample.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.MenuEntity;
import com.umeng.soexample.mvp.model.entity.UpdateEntity;

import io.reactivex.Observable;

public interface MenuContract {

    interface MenuView extends IView {
        void success(MenuEntity menuEntity);
        void getData(UpdateEntity updateEntity);
    }

    interface MenuModel extends IModel {
        Observable<MenuEntity> getMenu();
        Observable<UpdateEntity> postData(String params);
    }
}
