package com.umeng.soexample.mvp.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.mvc.MyApp;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.CarGoodsEntity;
import com.umeng.soexample.mvp.pay.demo.util.OrderInfoUtil2_0;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.RvGoods_cardGoodsAdapter;
import com.umeng.soexample.mvp.ui.adapter.RvRecommend_cardGoodsAdapter;
import com.umeng.soexample.mvp.ui.widget.MyHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.umeng.soexample.mvp.ui.widget.MyHandler.SDK_PAY_FLAG;

public class GoodsCarActivity extends BaseActivity<LrePresenter> implements LreContract.LreIView, View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.back_carGoods)
    ImageView back;

    @BindView(R.id.rvGoods_carGoods)
    RecyclerView rvGoods;

    @BindView(R.id.rvRecommend_carGoods)
    RecyclerView rvRecommend;

    @BindView(R.id.countPrice)
    TextView countPrice;

    @BindView(R.id.buy_carGoods)
    Button buy;

    private List<CarGoodsEntity.RecommendGoodsBean> recommendGoodsBeans = new ArrayList<>();
    private List<CarGoodsEntity.ValuesBean> valuesBeans = new ArrayList<>();
    private DividerItemDecoration itemDecoration;
    private ImageLoader loader;
    private RvGoods_cardGoodsAdapter rvGoods_cardGoodsAdapter;
    private int price;
    private int count;
    private RvRecommend_cardGoodsAdapter rvRecommend_cardGoodsAdapter;
    private MyHandler myHandler;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        return R.layout.activity_goods_car;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
    }

    private void init() {
        myHandler = MyHandler.getInstance(getApplicationContext());
        loader = new ImageLoaderUtils(this).getImageLoader();
        recommendGoodsBeans = (List<CarGoodsEntity.RecommendGoodsBean>) getIntent().getSerializableExtra("recommend");
        valuesBeans = (List<CarGoodsEntity.ValuesBean>) getIntent().getSerializableExtra("values");
        itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.itemdecoration_rv_card_goods));
        rvGoods.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvGoods.addItemDecoration(itemDecoration);
        rvGoods_cardGoodsAdapter = new RvGoods_cardGoodsAdapter(R.layout.item_rv_goods_cargoods,valuesBeans ,loader);

        rvRecommend.setLayoutManager(new GridLayoutManager(this,2));
        rvRecommend_cardGoodsAdapter = new RvRecommend_cardGoodsAdapter(R.layout.item_rv_recommend_card_goods, recommendGoodsBeans, loader);
    }

    private void setListener() {
        back.setOnClickListener(this);
        buy.setOnClickListener(this);
        rvGoods_cardGoodsAdapter.setOnItemChildClickListener(this);
    }

    private void setAdapter() {
        rvGoods.setAdapter(rvGoods_cardGoodsAdapter);
        rvRecommend.setAdapter(rvRecommend_cardGoodsAdapter);
    }

    @Override
    public void getData(BaseEntity entity, int type) {

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
            case R.id.back_carGoods:
                finish();
                break;
            case R.id.buy_carGoods:
                makeOrder();
                break;
        }
    }

    private void makeOrder() {
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(MyApp.appId, true);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, MyApp.primaryKey, true);
        final String orderInfo = orderParam + "&" + sign;
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(GoodsCarActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
//                Log.e("cjx", "result:" + result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                myHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean isZero = true;
        if(valuesBeans.get(position).getShop_select().equals("1")) {
            count++;
            valuesBeans.get(position).setShop_select("2");
            price += Integer.parseInt(valuesBeans.get(position).getShop_price());
        }else if(valuesBeans.get(position).getShop_select().equals("2")) {
            count--;
            valuesBeans.get(position).setShop_select("1");
            price -= Integer.parseInt(valuesBeans.get(position).getShop_price());
        }
        for(int i = 0;i < valuesBeans.size();i++) {
            if(valuesBeans.get(i).getShop_select().equals("2")) {
                isZero = false;
                break;
            }
        }
        countPrice.setText("总计:" + price + ".00(" + count +"件)");
        if(isZero)
            countPrice.setText("总计:0.00(0件)");
        rvGoods_cardGoodsAdapter.notifyDataSetChanged();
    }
}
