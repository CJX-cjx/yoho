package com.umeng.soexample.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.mvc.ui.activity.CategoryGoodsActivity;
import com.umeng.soexample.mvp.ui.activity.BrandActivity;
import com.umeng.soexample.mvp.ui.widget.BannerImageLoader;
import com.umeng.soexample.mvp.ui.widget.LettersView;
import com.umeng.soexample.mvp.ui.widget.RequestDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView_youHuo;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Action;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.BrandEntity;
import com.umeng.soexample.mvp.model.entity.CategoryAllEntity;
import com.umeng.soexample.mvp.model.entity.CategoryGoodsEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.RvBrandCategoryAdapter;
import com.umeng.soexample.mvp.ui.adapter.RvBrandHeadAdapter;
import com.umeng.soexample.mvp.ui.adapter.RvCategoryAllAdapter;
import com.umeng.soexample.mvp.ui.adapter.RvCategoryGoodsAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends BaseFragment<LrePresenter> implements LreContract.LreIView
        , View.OnClickListener, BaseRefreshListener ,LettersView.LettersViewCallBack, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    public final static String TAG = "CategoryFragment:";
    private View view;

    @BindView(R.id.img_menu_Category)
    ImageView imgMenu;

    @BindView(R.id.line_pinlei)
    TextView linePinlei;

    @BindView(R.id.line_brand)
    TextView line_brand;

    @BindView(R.id.tv_pinlei)
    TextView pinlei;

    @BindView(R.id.tv_brand)
    TextView brand;

    @BindView(R.id.tv_Men_category)
    TextView Men;

    @BindView(R.id.tv_Women_category)
    TextView women;

    @BindView(R.id.tv_Kids_category)
    TextView kids;

    @BindView(R.id.tv_BLK_category)
    TextView blk;

    @BindView(R.id.fLayout_category)
    FrameLayout fLayout_category;

    private RecyclerView rvLeft;
    private RecyclerView rvGoods;
    private View pinleiView;
    private RvCategoryAllAdapter rvCategoryAllAdapter;
    private List<TextView> textViews;
    private List<TextView> textViewLines;
    private PullToRefreshLayout ptr;
    private View brandView;
    private String params;
    private View hotView;
    private ImageLoader imageLoader;
    private RvCategoryGoodsAdapter rvCategoryGoodsAdapter;
    private JSONObject object;
    private Banner bannerBrand;
    private RecyclerView rvCategoryBrand;
    private RecyclerView rvCategoryBrandHead;
    private View headBrandView;
    private List<String> bannerLists = new ArrayList<>();
    private RvBrandHeadAdapter rvBrandHeadAdapter;
    private RvBrandCategoryAdapter rvBrandCategoryAdapter;
    private LettersView lettersView;
    private List<BrandEntity.Values> values = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private RequestDialog requestDialog;
    private List<CategoryGoodsEntity.Values> goodsEntities = new ArrayList<>();

    @Override
    public void getData(BaseEntity entity, int type) {
        switch (type) {
            case Constant.TYPE_CATEGORYALLENTITY_CATEGORY :
                CategoryAllEntity categoryAllEntity = (CategoryAllEntity) entity;
                rvCategoryAllAdapter.setNewData(categoryAllEntity.getValues());
                break;
            case Constant.TYPE_CATEGORYGOODSENTITY_CATEGORY :
                CategoryGoodsEntity goodsEntity = (CategoryGoodsEntity) entity;
                goodsEntities.clear();
                goodsEntities.addAll(goodsEntity.getValues());
                rvCategoryGoodsAdapter.setNewData(goodsEntity.getValues());
                break;
            case Constant.TYPE_BRANDENTITY_CATEGORY :
                BrandEntity brandEntity = (BrandEntity) entity;
                values = brandEntity.getValues();
                for(BrandEntity.Values values : brandEntity.getValues()) {
                    bannerLists.add(values.getBrand_icon());
                }
                bannerBrand.setImages(bannerLists).start();
                rvBrandHeadAdapter.setNewData(brandEntity.getValues());
                rvBrandCategoryAdapter.setNewData(brandEntity.getValues());
                requestDialog.dismiss();
                break;
        }
    }

    @Override
    public void refreshCallBack(BaseEntity entity) {
        CategoryGoodsEntity goodsEntity = (CategoryGoodsEntity) entity;
        rvCategoryGoodsAdapter.setNewData(goodsEntity.getValues());
        ptr.finishRefresh();
    }

    @Override
    public void loadMoreCallBack(BaseEntity entity) {

    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build().inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
        object = new JSONObject();
        try {
            object.put("menuid","1");
        } catch (JSONException e) {
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_CATEGORYALLENTITY_CATEGORY);

        object = new JSONObject();
        try {
            object.put("categoryid","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_CATEGORYGOODSENTITY_CATEGORY);

        object = new JSONObject();
        try {
            object.put("menuid","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_BRANDENTITY_CATEGORY);
        requestDialog.show();
    }

    private void init() {
        requestDialog = RequestDialog.getInstance(getContext());
        imageLoader = new ImageLoaderUtils(getContext()).getImageLoader();
        pinleiView = LayoutInflater.from(getContext()).inflate(R.layout.view_pinlei_category, null);
        brandView = LayoutInflater.from(getContext()).inflate(R.layout.view_brand_category, null);
        lettersView = brandView.findViewById(R.id.lettersViewBrand);
        hotView = LayoutInflater.from(getContext()).inflate(R.layout.view_hot_category, null);
        headBrandView = LayoutInflater.from(getContext()).inflate(R.layout.view_head_brandrv_category, null);
        fLayout_category.addView(pinleiView);
        ptr = pinleiView.findViewById(R.id.ptr_category);
        ptr.setHeaderView(new HeadRefreshView_youHuo(getContext()));
        ptr.setRefreshListener(this);
        ptr.setCanLoadMore(false);

        rvLeft = pinleiView.findViewById(R.id.rvLeft_category);
        rvLeft.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvCategoryAllAdapter = new RvCategoryAllAdapter(R.layout.item_rvleft_category);
        textViews = rvCategoryAllAdapter.getTextViews();
        textViewLines = rvCategoryAllAdapter.getTextViewLines();

        rvGoods = pinleiView.findViewById(R.id.rvGoods_category);
        rvGoods.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvCategoryGoodsAdapter = new RvCategoryGoodsAdapter(R.layout.item_rv_categorygoods, imageLoader);
        rvCategoryGoodsAdapter.setHeaderView(hotView);

        bannerBrand = headBrandView.findViewById(R.id.banner_brand_category);
        bannerBrand.setImageLoader(new BannerImageLoader())
                   .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                   .setDelayTime(1500)
                   .setIndicatorGravity(BannerConfig.CENTER);
        rvCategoryBrandHead = headBrandView.findViewById(R.id.rv_categoryBrandHead);
        rvCategoryBrandHead.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvBrandHeadAdapter = new RvBrandHeadAdapter(R.layout.item_rvbrandhead, imageLoader);
        rvCategoryBrand = brandView.findViewById(R.id.rv_brand_category);
        rvCategoryBrand.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        layoutManager = (LinearLayoutManager) rvCategoryBrand.getLayoutManager();
        rvBrandCategoryAdapter = new RvBrandCategoryAdapter(R.layout.item_rvbrand_category);
        rvBrandCategoryAdapter.setHeaderView(headBrandView);
    }

    private void setListener() {
        lettersView.setCallBack(this);
        pinlei.setOnClickListener(this);
        brand.setOnClickListener(this);
        Men.setOnClickListener(this);
        women.setOnClickListener(this);
        kids.setOnClickListener(this);
        blk.setOnClickListener(this);
        rvCategoryAllAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for(int i = 0;i < textViews.size();i++) {
                    textViews.get(i).setBackgroundColor(0xffdddddd);
                    textViews.get(i).setTextColor(0xffaaaaaa);
                    textViewLines.get(i).setVisibility(View.GONE);
                }
                TextView textView = textViews.get(position);
                textView.setTextColor(0xff000000);
                textView.setBackgroundColor(0xffffffff);
                textViewLines.get(position).setVisibility(View.VISIBLE);
            }
        });
        rvCategoryGoodsAdapter.setOnItemClickListener(this);
        rvBrandCategoryAdapter.setOnItemChildClickListener(this);
    }

    private void setAdapter() {
        rvLeft.setAdapter(rvCategoryAllAdapter);
        rvGoods.setAdapter(rvCategoryGoodsAdapter);
        rvCategoryBrandHead.setAdapter(rvBrandHeadAdapter);
        rvCategoryBrand.setAdapter(rvBrandCategoryAdapter);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showMessage(String message) {
        Log.e("cjx",TAG + message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_menu_Category :
                getActivity().sendBroadcast(new Intent(Action.MAINBROADCAST_ACTION));
                break;
            case R.id.tv_pinlei :
                setTab(0xffffffff,0xffeeeeee,View.VISIBLE,View.GONE);
                if(brandView.isShown())
                     fLayout_category.removeView(brandView);
                if(pinleiView.isShown())
                    fLayout_category.removeView(pinleiView);
                fLayout_category.addView(pinleiView);
                break;
            case R.id.tv_brand :
                setTab(0xffeeeeee,0xffffffff,View.GONE,View.VISIBLE);
                if(pinleiView.isShown())
                    fLayout_category.removeView(pinleiView);
                if(brandView.isShown())
                    fLayout_category.removeView(brandView);
                fLayout_category.addView(brandView);
                break;
            case R.id.tv_Men_category :
                setTextColor(0xff000000,0xff888888,0xff888888,0xff888888);
                break;
            case R.id.tv_Women_category :
                setTextColor(0xff888888,0xff000000,0xff888888,0xff888888);
                break;
            case R.id.tv_Kids_category :
                setTextColor(0xff888888,0xff888888,0xff000000,0xff888888);
                break;
            case R.id.tv_BLK_category :
                setTextColor(0xff888888,0xff888888,0xff888888,0xff000000);
                break;
        }
    }

    private void setTextColor(int MenColor,int WomenColor,int KidsColor,int BLKColor) {
        Men.setTextColor(MenColor);
        women.setTextColor(WomenColor);
        kids.setTextColor(KidsColor);
        blk.setTextColor(BLKColor);
    }

    private void setTab(int pinleiColor,int brandColor,int pinleiVisibility,int brandVisibility) {
        pinlei.setTextColor(pinleiColor);
        brand.setTextColor(brandColor);
        linePinlei.setVisibility(pinleiVisibility);
        line_brand.setVisibility(brandVisibility);
    }

    @Override
    public void refresh() {
        object = new JSONObject();
        try {
            object.put("categoryid","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.refresh(params,Constant.TYPE_CATEGORYGOODSENTITY_CATEGORY);
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void scrollLettersView(String letter) {
        for(int i = 0;i < values.size();i++) {
            if(letter.equals(values.get(i).getBrand_letter()))
                layoutManager.scrollToPositionWithOffset(i,1);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String categoryId = goodsEntities.get(position).getCategory_id();
        String goodsName = goodsEntities.get(position).getName();
        Intent intent = new Intent(getActivity(),CategoryGoodsActivity.class);
        intent.putExtra("categoryId",categoryId);
        intent.putExtra("goodsName",goodsName);
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tvBody_brandRv:
                Intent intent = new Intent(getActivity(),BrandActivity.class);
                startActivity(intent);
                break;
        }
    }
}
