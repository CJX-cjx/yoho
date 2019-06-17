package com.umeng.soexample.mvp.presenter;

import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.ui.fragment.CategoryFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@ActivityScope
public class LrePresenter extends BasePresenter<LreContract.LreIModel,LreContract.LreIView> {
    private int page = 1;

    @Inject
    public LrePresenter(LreContract.LreIModel iModel,LreContract.LreIView iView) {
        super(iModel,iView);
    }

    public void requestData(String params, int type) {
        mModel.lreRequestData(params,type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        isError(baseEntity);
                        mRootView.getData(baseEntity,type);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cjx",TAG+"(onError):" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void refresh(String params,int type) {
        page = 1;
        mModel.lreRefresh(getParams(params),type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity entity) {
                        isError(entity);
                        mRootView.refreshCallBack(entity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cjx",CategoryFragment.TAG + "refresh:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadMore(String params,int type) {
        page++;
        mModel.lreLoadMore(getParams(params),type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity entity) {
                        isError(entity);
                        mRootView.loadMoreCallBack(entity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cjx",CategoryFragment.TAG + "loadMore:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void isError(BaseEntity entity) {
        if(entity == null) {
            mRootView.showMessage("请求失败");
            return;
        }
    }

    private String getParams(String params) {
        JSONObject object = null;
        try {
            object = new JSONObject(params);
            object.put("page",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public void upLoadUserHead(String userid,File file,int type) {
        if(!file.exists())
            return;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("userid",userid);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("uploadedfile",file.getName(),requestBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        mModel.UpLoadUserHead(parts,Constant.TYPE_UPLOADUSERHEAD_ACTIVITY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        isError(baseEntity);
                        mRootView.getData(baseEntity,type);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cjx","上传失败:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
