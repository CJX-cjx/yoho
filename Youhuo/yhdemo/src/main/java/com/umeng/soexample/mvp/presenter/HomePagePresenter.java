package com.umeng.soexample.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.HomePageContract;
import com.umeng.soexample.mvp.model.entity.BannerEntity;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.GoodsEntity;
import com.umeng.soexample.mvp.model.entity.TuijianEntity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class HomePagePresenter extends BasePresenter<HomePageContract.HomePageIModel,HomePageContract.HomePageIView> {
    private int page = 1;
    private String id = "1";
    private JSONObject tuijian;
    private JSONObject goods;

    @Inject
    public HomePagePresenter(HomePageContract.HomePageIModel IModel,HomePageContract.HomePageIView IView) {
        super(IModel,IView);
    }

    public void requestAll() {
        tuijian = new JSONObject();
        goods = new JSONObject();
        try {
            tuijian.put("menu","1");
            goods.put("category",id);
            goods.put("page","" + page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String tuijianParams = tuijian.toString();
        String goodsParams = goods.toString();
        mModel.getAllData(tuijianParams,goodsParams).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if(baseEntity instanceof BannerEntity)
                            mRootView.getData(baseEntity,Constant.TYPE_BANNERENTITY_HOMEPAGE);
                        else if(baseEntity instanceof TuijianEntity)
                            mRootView.getData(baseEntity,Constant.TYPE_TUIJIANENTITY_HOMEPAGE);
                        else if(baseEntity instanceof GoodsEntity)
                            mRootView.getData(baseEntity,Constant.TYPE_GOODSENTITY_HOMEPAGE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.showMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mRootView.success();
                    }
                });
    }

    public void requestGoods() {
        JSONObject object = new JSONObject();
        try {
            object.put("category",id);
            object.put("page",page + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = object.toString();
        mModel.refresh(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GoodsEntity>() {
                    @Override
                    public void accept(GoodsEntity goodsEntity) throws Exception {
                        mRootView.refreshCallBack(goodsEntity);
                    }
                });
    }

    public void loadMoreGoods() {
        JSONObject object = new JSONObject();
        try {
            object.put("category",id);
            object.put("page",page + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = object.toString();
        mModel.loadMore(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GoodsEntity>() {
                    @Override
                    public void accept(GoodsEntity goodsEntity) throws Exception {
                        mRootView.loadMoreCallBack(goodsEntity);
                    }
                });
    }

    public void refresh(String id) {
        this.id = id;
        page = 1;
        requestGoods();
    }

    public void loadMore() {
        page++;
        loadMoreGoods();
    }
}
