package com.umeng.soexample.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.GoodsEntity;

import io.reactivex.Observable;

public interface HomePageContract {
    interface HomePageIView extends IView,BaseRefreshListener {
        void success();

        void getData(BaseEntity baseEntity,int type);

        void refreshCallBack(BaseEntity baseEntity);

        void loadMoreCallBack(BaseEntity baseEntity);
    }

    interface HomePageIModel extends IModel {
        Observable<BaseEntity> getAllData(String... strings);

        Observable<GoodsEntity> refresh(String params);

        Observable<GoodsEntity> loadMore(String params);
    }


}
