package com.umeng.soexample.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.umeng.soexample.mvp.model.entity.OrderEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

public class OrderActivity extends BaseActivity<LrePresenter> implements LreContract.LreIView {
    @BindView(R.id.back_order)
    ImageView back;

    @BindView(R.id.goodsImg_order)
    ImageView goodsImg;

    @BindView(R.id.orderNumber)
    TextView orderNumber;

    @BindView(R.id.goodsName_order)
    TextView goodsName;

    @BindView(R.id.goodsColor_order)
    TextView goodsColor;
    private JSONObject object;
    private String params;
    private List<OrderEntity.Values> values;
    private ImageLoader loader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        request();
    }

    private void init() {
        loader = new ImageLoaderUtils(this).getImageLoader();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void request() {
        object = new JSONObject();
        try {
            object.put("userid","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_ORDER_ACTIVITY);
    }

    @Override
    public void getData(BaseEntity entity, int type) {
        if(type == Constant.TYPE_ORDER_ACTIVITY) {
            OrderEntity orderEntity = (OrderEntity) entity;
            values = orderEntity.getValues();
            orderNumber.setText("订单编号:" + orderEntity.getOrdernum());
            goodsName.setText(values.get(0).getShop_name());
            goodsColor.setText("颜色:" + values.get(0).getShop_color());
            loader.loadImage(this,ImageConfigImpl.builder()
            .url(Api.IMG_DOMAIN + values.get(0).getCar_path())
            .placeholder(R.drawable.share_list_placeholder_s_background)
            .errorPic(R.drawable.tt_default_image_error)
            .imageView(goodsImg)
            .build());
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
}
