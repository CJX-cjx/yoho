package com.umeng.soexample.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.BrandActivityEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.Rv_brand_activityAdapter;
import com.umeng.soexample.mvp.ui.widget.BannerImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BrandActivity extends BaseActivity<LrePresenter> implements LreContract.LreIView, View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.back_brandActivity)
    ImageView back;

    @BindView(R.id.rv_brandActivity)
    RecyclerView rv;

    private View headView;
    private ImageView iconDianpu;
    private TextView nameDianpu;
    private Banner banner;
    private JSONObject object;
    private String params;
    private List<BrandActivityEntity.Values> values;
    private List<String> imgsUrl = new ArrayList<>();
    private Rv_brand_activityAdapter rv_brand_activityAdapter;
    private ImageLoader loader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this)).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_brand;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
        request();
    }

    private void init() {
        loader = new ImageLoaderUtils(this).getImageLoader();
        headView = LayoutInflater.from(this).inflate(R.layout.head_brand_activity, null);
        iconDianpu = headView.findViewById(R.id.icon_dianpu);
        nameDianpu = headView.findViewById(R.id.name_dianpu);
        banner = headView.findViewById(R.id.banner_head_brand_activity);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new BannerImageLoader())
                .setIndicatorGravity(BannerConfig.CENTER)
                .setDelayTime(1500);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv_brand_activityAdapter = new Rv_brand_activityAdapter(R.layout.item_rv_brand_activity, loader);
        rv_brand_activityAdapter.addHeaderView(headView);
    }

    private void setListener() {
        back.setOnClickListener(this);
        rv_brand_activityAdapter.setOnItemClickListener(this);
    }

    private void setAdapter() {
        rv.setAdapter(rv_brand_activityAdapter);
    }

    private void request() {
        object = new JSONObject();
        try {
            object.put("brand","1");
            object.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_BRANDENTITY_ACTIVITY);
    }

    @Override
    public void getData(BaseEntity entity, int type) {
        if(type == Constant.TYPE_BRANDENTITY_ACTIVITY) {
            BrandActivityEntity brandActivityEntity = (BrandActivityEntity) entity;
            values = brandActivityEntity.getValues();
            for(BrandActivityEntity.Banner data : brandActivityEntity.getBanner()) {
                imgsUrl.add(data.getRecommend_url());
            }
            banner.setImages(imgsUrl).start();
            rv_brand_activityAdapter.setNewData(values.get(0).getGoods());
            loader.loadImage(this,ImageConfigImpl.builder()
            .url(Api.IMG_DOMAIN + values.get(0).getBrand_icon())
            .placeholder(R.drawable.share_list_placeholder_s_background)
            .errorPic(R.drawable.tt_default_image_error)
            .imageView(iconDianpu).build());
            nameDianpu.setText(values.get(0).getBrand_name());
        }
    }

    @Override
    public void refreshCallBack(BaseEntity entity) {

    }

    @Override
    public void loadMoreCallBack(BaseEntity entity) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_brandActivity:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this,GoodsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods_brandActivity",  values.get(0).getGoods().get(position));
        bundle.putSerializable("data", (Serializable) values.get(0).getGoods());
        intent.putExtra("where","BrandActivity");
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }
}
