package com.umeng.soexample.mvp.model.api;




import com.umeng.soexample.mvp.model.entity.AddressListEntity;
import com.umeng.soexample.mvp.model.entity.BannerEntity;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.BrandActivityEntity;
import com.umeng.soexample.mvp.model.entity.BrandEntity;
import com.umeng.soexample.mvp.model.entity.CarGoodsEntity;
import com.umeng.soexample.mvp.model.entity.CategoryAllEntity;
import com.umeng.soexample.mvp.model.entity.CategoryGoodsEntity;
import com.umeng.soexample.mvp.model.entity.CommunityEntity;
import com.umeng.soexample.mvp.model.entity.GoodsActivityEntity;
import com.umeng.soexample.mvp.model.entity.GoodsEntity;
import com.umeng.soexample.mvp.model.entity.LoginEntity;
import com.umeng.soexample.mvp.model.entity.LookLookCategoryEntity;
import com.umeng.soexample.mvp.model.entity.LookLookListEntity;
import com.umeng.soexample.mvp.model.entity.LookShowEntity;
import com.umeng.soexample.mvp.model.entity.MenuEntity;
import com.umeng.soexample.mvp.model.entity.OrderEntity;
import com.umeng.soexample.mvp.model.entity.ShoesDealEntity;
import com.umeng.soexample.mvp.model.entity.TuijianEntity;
import com.umeng.soexample.mvp.model.entity.UpdateEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by MVPArmsTemplate on 03/12/2019 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {
    String APP_DOMAIN = "http://169.254.58.4/yoho/";
    String IMG_DOMAIN = "http://169.254.58.4/yoho";

    @GET("home_menu.php")
    Observable<MenuEntity> getMenu();

    @GET("Home_banner.php")
    Observable<BannerEntity> getBannerData();

    @FormUrlEncoded
    @POST("home_recommend.php")
    Observable<TuijianEntity> getTuijianData(@Field("request") String request);

    @FormUrlEncoded
    @POST("home_goods.php")
    Observable<GoodsEntity> getGoodsData(@Field("request") String request);

    @FormUrlEncoded
    @POST("category_all.php")
    Observable<CategoryAllEntity> postCategotyAllCategory(@Field("request") String params);

    @FormUrlEncoded
    @POST("category_goods.php")
    Observable<CategoryGoodsEntity> postCategoryGoods(@Field("request") String params);

    @FormUrlEncoded
    @POST("Brand_list.php")
    Observable<BrandEntity> postCategoryBrand(@Field("request") String params);

    @FormUrlEncoded
    @POST("shoes_list.php")
    Observable<ShoesDealEntity> postShoesDeal(@Field("request") String params);

    @FormUrlEncoded
    @POST("see_category.php")
    Observable<LookLookCategoryEntity> postLookLookCategory(@Field("request") String params);

    @FormUrlEncoded
    @POST("see_list.php")
    Observable<LookLookListEntity> postLookLookList(@Field("request") String params);

    @FormUrlEncoded
    @POST("show.php")
    Observable<LookShowEntity> postLookShow(@Field("request") String params);

    @FormUrlEncoded
    @POST("community.php")
    Observable<CommunityEntity> postCommunity(@Field("request") String params);

    @FormUrlEncoded
    @POST("goods_list.php")
    Observable<GoodsActivityEntity> postGoodsActivity(@Field("request") String params);

    @FormUrlEncoded
    @POST("brand_goods.php")
    Observable<BrandActivityEntity> postBrandActivity(@Field("request") String params);

    @FormUrlEncoded
    @POST("login.php")
    Observable<LoginEntity> postLogin(@Field("request") String params);

    @FormUrlEncoded
    @POST("register.php")
    Observable<LoginEntity> postRegister(@Field("request") String params);

    @FormUrlEncoded
    @POST("add_car.php")
    Observable<BaseEntity> postAddCar(@Field("request") String params);

    @FormUrlEncoded
    @POST("car_list.php")
    Observable<CarGoodsEntity> postCarGoods(@Field("request") String params);

    @FormUrlEncoded
    @POST("create_order.php")
    Observable<OrderEntity> postOrderList(@Field("request") String params);

    @FormUrlEncoded
    @POST("address_list.php")
    Observable<AddressListEntity> postAddressList(@Field("request") String params);

    @FormUrlEncoded
    @POST("add_address.php")
    Observable<BaseEntity> postAddNewAddressList(@Field("request") String params);

    @FormUrlEncoded
    @POST("upgrade.php")
    Observable<UpdateEntity> postUpdateProduct(@Field("request") String params);

    @FormUrlEncoded
    @POST("sel_user.php")
    Observable<LoginEntity> postUserInfo(@Field("request") String params);

    @FormUrlEncoded
    @POST("update_user.php")
    Observable<BaseEntity> postUpdateUserInfo(@Field("request") String params);

    @Multipart
    @POST("upload_head.php")
    Observable<BaseEntity> postUpLoadUserHead(@Part List<MultipartBody.Part> parts);
}
