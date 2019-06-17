package com.umeng.soexample.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.umeng.soexample.mvp.model.entity.BaseEntity;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


public interface LreContract {
    interface LreIView extends IView {
        void getData(BaseEntity entity, int type);
        void refreshCallBack(BaseEntity entity);
        void loadMoreCallBack(BaseEntity entity);
    }

    interface LreIModel extends IModel {
        Observable<BaseEntity> lreRequestData(String params,int type);
        Observable<BaseEntity> lreRefresh(String params,int type);
        Observable<BaseEntity> lreLoadMore(String params,int type);
        Observable<BaseEntity> UpLoadUserHead(List<MultipartBody.Part> parts, int type);
    }
}
