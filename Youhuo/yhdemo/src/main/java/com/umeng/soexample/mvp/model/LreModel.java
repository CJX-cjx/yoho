package com.umeng.soexample.mvp.model;

import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.AddressListEntity;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.BrandActivityEntity;
import com.umeng.soexample.mvp.model.entity.BrandEntity;
import com.umeng.soexample.mvp.model.entity.CarGoodsEntity;
import com.umeng.soexample.mvp.model.entity.CategoryAllEntity;
import com.umeng.soexample.mvp.model.entity.CategoryGoodsEntity;
import com.umeng.soexample.mvp.model.entity.CommunityEntity;
import com.umeng.soexample.mvp.model.entity.GoodsActivityEntity;
import com.umeng.soexample.mvp.model.entity.LoginEntity;
import com.umeng.soexample.mvp.model.entity.LookLookCategoryEntity;
import com.umeng.soexample.mvp.model.entity.LookLookListEntity;
import com.umeng.soexample.mvp.model.entity.LookShowEntity;
import com.umeng.soexample.mvp.model.entity.OrderEntity;
import com.umeng.soexample.mvp.model.entity.ShoesDealEntity;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

@ActivityScope
public class LreModel extends BaseModel implements LreContract.LreIModel {

    @Inject
    public LreModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseEntity> lreRequestData(String params, int type) {
        Observable<BaseEntity> observable = null;
        switch (type) {
            case Constant.TYPE_CATEGORYALLENTITY_CATEGORY :
                Observable<CategoryAllEntity> o = mRepositoryManager.obtainRetrofitService(Api.class).postCategotyAllCategory(params);
//              observable = Observable.fromArray(o).flatMap((Function)Functions.identity(), false, 1);
                observable = Observable.mergeArray(o);
                break;
            case Constant.TYPE_CATEGORYGOODSENTITY_CATEGORY :
                Observable<CategoryGoodsEntity> o1 = mRepositoryManager.obtainRetrofitService(Api.class).postCategoryGoods(params);
                observable = Observable.mergeArray(o1);
                break;
            case Constant.TYPE_BRANDENTITY_CATEGORY :
                Observable<BrandEntity> o2 = mRepositoryManager.obtainRetrofitService(Api.class).postCategoryBrand(params);
                observable = Observable.mergeArray(o2);
                break;
            case Constant.TYPE_SHOESDEALENTITY_SHOESDEAL :
                Observable<ShoesDealEntity> o3 = mRepositoryManager.obtainRetrofitService(Api.class).postShoesDeal(params);
                observable = Observable.mergeArray(o3);
                break;
            case Constant.TYPE_CATEGORYENTITY_LOOKLOOK :
                Observable<LookLookCategoryEntity> o4 = mRepositoryManager.obtainRetrofitService(Api.class).postLookLookCategory(params);
                observable = Observable.mergeArray(o4);
                break;
            case Constant.TYPE_LISTENTITY_LOOKLOOK :
                Observable<LookLookListEntity> o5 = mRepositoryManager.obtainRetrofitService(Api.class).postLookLookList(params);
                observable = Observable.mergeArray(o5);
                break;
            case Constant.TYPE_SHOWENTITY_LOOKSHOW :
                Observable<LookShowEntity> o6 = mRepositoryManager.obtainRetrofitService(Api.class).postLookShow(params);
                observable = Observable.mergeArray(o6);
                break;
            case Constant.TYPE_COMMUNITYENTITY_COMMUNITY :
                Observable<CommunityEntity> o7 = mRepositoryManager.obtainRetrofitService(Api.class).postCommunity(params);
                observable = Observable.mergeArray(o7);
                break;
            case Constant.TYPE_GOODSENTITY_COMMUNITYGOODSACTIVITY :
                Observable<GoodsActivityEntity> o8 = mRepositoryManager.obtainRetrofitService(Api.class).postGoodsActivity(params);
                observable = Observable.mergeArray(o8);
                break;
            case Constant.TYPE_BRANDENTITY_ACTIVITY :
                Observable<BrandActivityEntity> o9 = mRepositoryManager.obtainRetrofitService(Api.class).postBrandActivity(params);
                observable = Observable.mergeArray(o9);
                break;
            case Constant.TYPE_LOGIN_ACTIVITY :
                Observable<LoginEntity> o10 = mRepositoryManager.obtainRetrofitService(Api.class).postLogin(params);
                observable = Observable.mergeArray(o10);
                break;
            case Constant.TYPE_REGISTER_ACTIVITY :
                Observable<LoginEntity> o11 = mRepositoryManager.obtainRetrofitService(Api.class).postRegister(params);
                observable = Observable.mergeArray(o11);
                break;
            case Constant.TYPE_ADDCAR_GOODSACTIVITY :
                Observable<BaseEntity> o12 = mRepositoryManager.obtainRetrofitService(Api.class).postAddCar(params);
                observable = Observable.mergeArray(o12);
                break;
            case Constant.TYPE_CARGOODS_ACTIVITY :
                Observable<CarGoodsEntity> o13 = mRepositoryManager.obtainRetrofitService(Api.class).postCarGoods(params);
                observable = Observable.mergeArray(o13);
                break;
            case Constant.TYPE_ORDER_ACTIVITY :
                Observable<OrderEntity> o14 = mRepositoryManager.obtainRetrofitService(Api.class).postOrderList(params);
                observable = Observable.mergeArray(o14);
                break;
            case Constant.TYPE_ADDRESSLIST_ACTIVITY :
                Observable<AddressListEntity> o15 = mRepositoryManager.obtainRetrofitService(Api.class).postAddressList(params);
                observable = Observable.mergeArray(o15);
                break;
            case Constant.TYPE_ADDNEWADDRESS_ACTIVITY :
                Observable<BaseEntity> o16 = mRepositoryManager.obtainRetrofitService(Api.class).postAddNewAddressList(params);
                observable = Observable.mergeArray(o16);
                break;
            case Constant.TYPE_PERSONAL_ACTIVITY :
                Observable<LoginEntity> o17 = mRepositoryManager.obtainRetrofitService(Api.class).postUserInfo(params);
                observable = Observable.mergeArray(o17);
                break;
            case Constant.TYPE_UPDATEPERSONAL_ACTIVITY :
                Observable<BaseEntity> o18 = mRepositoryManager.obtainRetrofitService(Api.class).postUpdateUserInfo(params);
                observable = Observable.mergeArray(o18);
                break;
        }
        return observable;
    }

    @Override
    public Observable<BaseEntity> lreRefresh(String params, int type) {
        Observable<BaseEntity> observable = null;
        switch (type) {
            case Constant.TYPE_CATEGORYGOODSENTITY_CATEGORY :
                Observable<CategoryGoodsEntity> o = mRepositoryManager.obtainRetrofitService(Api.class).postCategoryGoods(params);
                observable = Observable.mergeArray(o);
                break;
            case Constant.TYPE_SHOESDEALENTITY_SHOESDEAL :
                Observable<ShoesDealEntity> o1 = mRepositoryManager.obtainRetrofitService(Api.class).postShoesDeal(params);
                observable = Observable.mergeArray(o1);
                break;
            case Constant.TYPE_LISTENTITY_LOOKLOOK :
                Observable<LookLookListEntity> o2 = mRepositoryManager.obtainRetrofitService(Api.class).postLookLookList(params);
                observable = Observable.mergeArray(o2);
                break;
            case Constant.TYPE_SHOWENTITY_LOOKSHOW :
                Observable<LookShowEntity> o3 = mRepositoryManager.obtainRetrofitService(Api.class).postLookShow(params);
                observable = Observable.mergeArray(o3);
                break;
            case Constant.TYPE_COMMUNITYENTITY_COMMUNITY :
                Observable<CommunityEntity> o4 = mRepositoryManager.obtainRetrofitService(Api.class).postCommunity(params);
                observable = Observable.mergeArray(o4);
                break;
        }
        return observable;
    }

    @Override
    public Observable<BaseEntity> lreLoadMore(String params, int type) {
        Observable<BaseEntity> observable = null;
        switch (type) {
            case Constant.TYPE_SHOESDEALENTITY_SHOESDEAL:
                Observable<ShoesDealEntity> o = mRepositoryManager.obtainRetrofitService(Api.class).postShoesDeal(params);
                observable = Observable.mergeArray(o);
                break;
            case Constant.TYPE_LISTENTITY_LOOKLOOK :
                Observable<LookLookListEntity> o1 = mRepositoryManager.obtainRetrofitService(Api.class).postLookLookList(params);
                observable = Observable.mergeArray(o1);
                break;
            case Constant.TYPE_SHOWENTITY_LOOKSHOW :
                Observable<LookShowEntity> o2 = mRepositoryManager.obtainRetrofitService(Api.class).postLookShow(params);
                observable = Observable.mergeArray(o2);
                break;
            case Constant.TYPE_COMMUNITYENTITY_COMMUNITY :
                Observable<CommunityEntity> o3 = mRepositoryManager.obtainRetrofitService(Api.class).postCommunity(params);
                observable = Observable.mergeArray(o3);
                break;
        }
        return observable;
    }

    @Override
    public Observable<BaseEntity> UpLoadUserHead( List<MultipartBody.Part> parts, int type) {
        Observable<BaseEntity> observable = null;
        observable = mRepositoryManager.obtainRetrofitService(Api.class).postUpLoadUserHead(parts);
        return observable;
    }
}
