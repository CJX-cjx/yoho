package com.umeng.soexample.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.R;

import com.umeng.soexample.di.component.DaggerHomePageCompoent;
import com.umeng.soexample.mvp.ui.activity.GoodsActivity;
import com.umeng.soexample.mvp.ui.widget.BannerImageLoader;
import com.umeng.soexample.mvp.ui.widget.RequestDialog;
import com.ccmit.zxinglibrary.activity.CaptureActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView;
import com.jwenfeng.library.pulltorefresh.view.LoadMoreView;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.module.HomePageModule;
import com.umeng.soexample.domin.Action;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.HomePageContract;
import com.umeng.soexample.mvp.model.entity.BannerEntity;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.CategoryEntity;
import com.umeng.soexample.mvp.model.entity.GoodsEntity;
import com.umeng.soexample.mvp.model.entity.TuijianEntity;
import com.umeng.soexample.mvp.presenter.HomePagePresenter;
import com.umeng.soexample.mvp.ui.adapter.RvCategoryAdapter;
import com.umeng.soexample.mvp.ui.adapter.RvGoodsAdapter;
import com.umeng.soexample.mvp.ui.adapter.RvTuijianAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.HomePageIView
        , View.OnClickListener, HeadRefreshView.HeadRefreshViewProcessListener
        , HeadRefreshView.HeadRefreshViewFinishListener
        , HeadRefreshView.HeadRefreshViewLoadingListener, BaseQuickAdapter.OnItemClickListener {
    private View view;
    private PullToRefreshLayout ptr;
    private String id = "1";
    private View headView;
    private View bannerView;
    private View tuijianView;
    private Banner banner;
    private RecyclerView rv_tuijian;
    private RecyclerView rv_category;
    private ImageView imgLeft;
    private ImageView imgSaoma;
    private List<String> bannerImages;
    private List<GoodsEntity.Values> goodsData;
    private List<TuijianEntity.Recommend_values> tuijianData;
    private List<CategoryEntity> catecoryData;
    private ImageLoaderUtils utils;
    private List<TextView> textViews;
    private List<TextView> textViews_homepage;
    private List<TextView> textLines;
    private List<TextView> textLines_homepage;
    private boolean isLoading;

    @BindView(R.id.rv_goods_homepage)
    RecyclerView rv_goods;
    @BindView(R.id.rv_category_homepage)
    RecyclerView rvCategory_homepage;
    @BindView(R.id.titleBg_homepage)
    RelativeLayout titleBg;

    private RvGoodsAdapter rvGoodsAdapter;
    private RvTuijianAdapter rvTuijianAdapter;
    private View categoryView;
    private RvCategoryAdapter rvCategoryAdapter;
    private HeadRefreshView headRefreshView;
    private RvCategoryAdapter rvCategoryAdapter_homepage;
    private int bannerHeight;
    private int rvGoodsScrollHeight;
    private int positionHead;
    private RequestDialog requestDialog;
    private List<BannerEntity.Values> values = new ArrayList<>();
    private List<TuijianEntity.Recommend_values> recommend = new ArrayList<>();
    private List<GoodsEntity.Values> values1 = new ArrayList<>();

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomePageCompoent.builder().appComponent(appComponent)
                .homePageModule(new HomePageModule(this))
                .build().inJect(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setAdapter();
        setListener();
        requestDialog.show();
        mPresenter.requestAll();
    }

    private void setAdapter() {
        rv_tuijian.setAdapter(rvTuijianAdapter);
        rv_category.setAdapter(rvCategoryAdapter);
        rvCategory_homepage.setAdapter(rvCategoryAdapter_homepage);
        rv_goods.setAdapter(rvGoodsAdapter);
    }

    private void setListener() {
        ptr.setRefreshListener(this);
        headRefreshView.setHeadRefreshViewProcessListener(this);
        headRefreshView.setFinishListener(this);
        headRefreshView.setLoadingListener(this);
        imgLeft.setOnClickListener(this);
        imgSaoma.setOnClickListener(this);
        rvCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                positionHead = position;
                for(int i = 0;i < textViews.size();i++) {
                    textViews.get(i).setTextColor(0xffaaaaaa);
                    textLines.get(i).setVisibility(View.GONE);
                }
                textViews.get(position).setTextColor(0xff000000);
                textLines.get(position).setVisibility(View.VISIBLE);
                id = Constant.categoryId[position];
                refresh();
            }
        });
        rvCategoryAdapter_homepage.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for(int i = 0;i < textViews_homepage.size();i++) {
                    textViews_homepage.get(i).setTextColor(0xffaaaaaa);
                    textLines_homepage.get(i).setVisibility(View.GONE);
                    textViews.get(i).setTextColor(0xffaaaaaa);
                    textLines.get(i).setVisibility(View.GONE);
                }
                textViews_homepage.get(position).setTextColor(0xff000000);
                textLines_homepage.get(position).setVisibility(View.VISIBLE);
                textViews.get(position).setTextColor(0xff000000);
                textLines.get(position).setVisibility(View.VISIBLE);
                id = Constant.categoryId[position];
                refresh();
                rvCategory_homepage.setVisibility(View.GONE);
                rvGoodsScrollHeight = 0;
            }
        });
        rv_goods.addOnScrollListener(new RvGoodsScrollListener());
        rvGoodsAdapter.setOnItemClickListener(this);
    }

    private void init() {
        requestDialog = RequestDialog.getInstance(getContext());
        utils = new ImageLoaderUtils(getContext());
        tuijianData = new ArrayList<>();
        goodsData = new ArrayList<>();
        catecoryData = new ArrayList<>();
        ptr = view.findViewById(R.id.ptr_homepage);
        headRefreshView = new HeadRefreshView(getContext());
        ptr.setHeaderView(headRefreshView);
        ptr.setFooterView(new LoadMoreView(getContext()));
        headView = view.findViewById(R.id.head_homepage);
        bannerView = LayoutInflater.from(getContext()).inflate(R.layout.view_banner_homepage,null);
        bannerView.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
                            ,View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
        bannerHeight = bannerView.getMeasuredHeight();
        tuijianView = LayoutInflater.from(getContext()).inflate(R.layout.view_rv_tuijian_homepage, null);
        categoryView = LayoutInflater.from(getContext()).inflate(R.layout.view_category_homepage, null);

        imgLeft = headView.findViewById(R.id.menu_head_homepage);
        imgSaoma = headView.findViewById(R.id.saoma_head_homepage);
        banner = bannerView.findViewById(R.id.banner_homepage);
        rv_tuijian = tuijianView.findViewById(R.id.rv_tuijian_homepage);
        rv_category = categoryView.findViewById(R.id.rv_category_homepage);
        rv_category.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        for(int i = 0;i < 10;i++) {
            CategoryEntity entity = new CategoryEntity(Constant.categoryId[i], Constant.categoryName[i]);
            catecoryData.add(entity);
        }
        rvCategoryAdapter = new RvCategoryAdapter(R.layout.item_rv_categoty, catecoryData);
        textViews = rvCategoryAdapter.getTextViews();
        textLines = rvCategoryAdapter.getTextLines();
        rvCategory_homepage.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvCategoryAdapter_homepage = new RvCategoryAdapter(R.layout.item_rv_categoty, catecoryData);

        rv_goods.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvGoodsAdapter = new RvGoodsAdapter(R.layout.item_rvgoods,utils);
        rvGoodsAdapter.addHeaderView(bannerView);
        rvGoodsAdapter.addHeaderView(tuijianView);
        rvGoodsAdapter.addHeaderView(categoryView);
        banner.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setDelayTime(1500)
                .startAutoPlay();
        rv_tuijian.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvTuijianAdapter = new RvTuijianAdapter(R.layout.item_rv_tuijian, utils);
    }

    /**
     * 此方法在fragment的onCreate执行完后才能调用presenter
     * 参数可以传入Message对象,通过switch(msg.what) (msg.obj代表传入的数据)
     * 来区分不同的通信操作
     * */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void success() {

    }

    @Override
    public void getData(BaseEntity baseEntity, int type) {
        if(type == Constant.TYPE_BANNERENTITY_HOMEPAGE) {
            BannerEntity bannerEntity = (BannerEntity) baseEntity;
            values = bannerEntity.getValues();
            bannerImages = new ArrayList<>();
            for(BannerEntity.Values values1 : values) {
                bannerImages.add(values1.getRecommend_url());
            }
            banner.setImages(bannerImages).start();
        }else if(type == Constant.TYPE_TUIJIANENTITY_HOMEPAGE) {
            TuijianEntity tuijianEntity = (TuijianEntity) baseEntity;
            tuijianData.clear();
            recommend = tuijianEntity.getRecommend();
            tuijianData.addAll(recommend);
            rvTuijianAdapter.setNewData(tuijianData);
        }else if(type == Constant.TYPE_GOODSENTITY_HOMEPAGE) {
            GoodsEntity goodsEntity = (GoodsEntity) baseEntity;
            values1 = goodsEntity.getValues();
            for(GoodsEntity.Values values : values1) {
                goodsData.add(values);
            }
            rvGoodsAdapter.setNewData(goodsData);
        }
        if(values.size() != 0 && recommend.size() != 0 && values1.size() != 0)
            requestDialog.dismiss();
    }

    @Override
    public void refreshCallBack(BaseEntity baseEntity) {
        if(baseEntity != null && baseEntity instanceof GoodsEntity) {
            GoodsEntity goodsEntity = (GoodsEntity) baseEntity;
            if(goodsEntity.getValues() == null) {
                Toast.makeText(mContext, "抱歉，暂无相关数据", Toast.LENGTH_SHORT).show();
                return;
            }
            goodsData.clear();
            goodsData.addAll(goodsEntity.getValues());
            rvGoodsAdapter.setNewData(goodsData);
            ptr.finishRefresh();
            titleBg.setAlpha(0);
            Toast.makeText(mContext, "刷新完毕", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadMoreCallBack(BaseEntity baseEntity) {
        if(baseEntity != null && baseEntity instanceof GoodsEntity) {
            GoodsEntity goodsEntity = (GoodsEntity) baseEntity;
            if(goodsEntity.getValues() == null) {
                Toast.makeText(mContext, "滑到底了，(。・＿・。)ﾉI’m sorry~", Toast.LENGTH_SHORT).show();
                ptr.finishLoadMore();
                return;
            }
            goodsData.addAll(goodsEntity.getValues());
            rvGoodsAdapter.setNewData(goodsData);
            ptr.finishLoadMore();
            Toast.makeText(mContext, "加载完毕", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refresh() {
        mPresenter.refresh(id);
    }

    @Override
    public void loadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_head_homepage :
                getActivity().sendBroadcast(new Intent(Action.MAINBROADCAST_ACTION));
                break;
            case R.id.saoma_head_homepage :
                Intent intent = new Intent(getActivity(),CaptureActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getProcess(float process) {
        isLoading = false;
        float alpha = process / 130;
        if(alpha > 1)
            return;
        titleBg.setAlpha(alpha);
    }

    @Override
    public void getFinishProcess(float process) {
        float alpha = process / 130;
        if(process <= 170 && !isLoading) {
            titleBg.setAlpha(alpha);
        }
    }

    @Override
    public void isLoading() {
        isLoading = true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getActivity(),GoodsActivity.class);
        intent.putExtra("where","HomePageFragment");
        intent.putExtra("goods_homepageFragment", values1.get(position));
        intent.putExtra("data", (Serializable) values1);
        startActivity(intent);
    }

    class RvGoodsScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            rvGoodsScrollHeight += dy;
            float alpha = (float) rvGoodsScrollHeight / (bannerHeight + 130);
            if(alpha < 0)
                alpha = 0;
            if(alpha > 1)
                alpha = 1;
            titleBg.setAlpha(alpha);
            GridLayoutManager manager = (GridLayoutManager) rv_goods.getLayoutManager();
//            int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            if(rvGoodsScrollHeight >= 910) {
                rvCategory_homepage.setVisibility(View.VISIBLE);
                textViews_homepage = rvCategoryAdapter_homepage.getTextViews();
                textLines_homepage = rvCategoryAdapter_homepage.getTextLines();
                if(textViews_homepage.size() == 0 || textLines_homepage.size() == 0)
                    return;
                for(int i = 0;i < textViews.size();i++) {
                    textViews_homepage.get(i).setTextColor(0xffaaaaaa);
                    textLines_homepage.get(i).setVisibility(View.GONE);
                }
                textViews_homepage.get(positionHead).setTextColor(0xff000000);
                textLines_homepage.get(positionHead).setVisibility(View.VISIBLE);
            }
            else
                rvCategory_homepage.setVisibility(View.GONE);
        }
    }
}
