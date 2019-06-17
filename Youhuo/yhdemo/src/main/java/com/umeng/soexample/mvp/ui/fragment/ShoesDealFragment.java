package com.umeng.soexample.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView_youHuo;
import com.jwenfeng.library.pulltorefresh.view.LoadMoreView;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.ShoesDealEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.RvShoesDealAdapter;
import com.umeng.soexample.mvp.ui.adapter.RvShoesDealHeadAdapter;
import com.umeng.soexample.mvp.ui.widget.BannerImageLoader;
import com.umeng.soexample.mvp.ui.widget.RequestDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoesDealFragment extends BaseFragment<LrePresenter> implements LreContract.LreIView, BaseRefreshListener {

    private View view;
    private View headView;
    private Banner banner;
    private RecyclerView rvHead;
    private JSONObject object;
    private String params;
    private List<String> bannerList = new ArrayList<>();
    private ImageLoader loader;
    private RvShoesDealHeadAdapter rvShoesDealHeadAdapter;

    @BindView(R.id.ptr_shoesDeal)
    PullToRefreshLayout ptr;

    @BindView(R.id.rv_shoesDeal)
    RecyclerView rvShoesDeal;
    private RvShoesDealAdapter rvShoesDealAdapter;
    private DividerItemDecoration itemDecoration;
    private RequestDialog dialog;

    @Override
    public void getData(BaseEntity entity, int type) {
        switch (type) {
            case Constant.TYPE_SHOESDEALENTITY_SHOESDEAL :
                ShoesDealEntity shoesDealEntity = (ShoesDealEntity) entity;
                for(ShoesDealEntity.Banner banner : shoesDealEntity.getBanner()) {
                    bannerList.add(banner.getRecommend_url());
                }
                banner.setImages(bannerList).start();
                rvShoesDealHeadAdapter.setNewData(shoesDealEntity.getBrand());
                rvShoesDealAdapter.setNewData(shoesDealEntity.getValues());
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void refreshCallBack(BaseEntity entity) {
        if(entity instanceof ShoesDealEntity) {
            ShoesDealEntity shoesDealEntity = (ShoesDealEntity) entity;
            rvShoesDealAdapter.setNewData(shoesDealEntity.getValues());
            ptr.finishRefresh();
        }
    }

    @Override
    public void loadMoreCallBack(BaseEntity entity) {
        if(entity instanceof ShoesDealEntity) {
            ShoesDealEntity shoesDealEntity = (ShoesDealEntity) entity;
            if(shoesDealEntity.getValues() == null) {
                ptr.finishLoadMore();
                Toast.makeText(mContext, "滑到底了，(。・＿・。)ﾉI’m sorry~", Toast.LENGTH_SHORT).show();
                return;
            }
            rvShoesDealAdapter.addData(shoesDealEntity.getValues());
            ptr.finishLoadMore();
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_shoesdeal, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
        requestData();
    }

    private void requestData() {
        dialog.show();
        object = new JSONObject();
        try {
            object.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_SHOESDEALENTITY_SHOESDEAL);
    }

    private void init() {
        dialog = RequestDialog.getInstance(getContext());
        loader = new ImageLoaderUtils(getContext()).getImageLoader();
        ptr.setHeaderView(new HeadRefreshView_youHuo(getContext()));
        ptr.setFooterView(new LoadMoreView(getContext()));
        headView = LayoutInflater.from(getContext()).inflate(R.layout.head_rv_shoes_deal, null);
        banner = headView.findViewById(R.id.bannerShoesDealHead);
        banner.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setDelayTime(1500);

        rvHead = headView.findViewById(R.id.rv_shoesDealHead);
        rvHead.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvShoesDealHeadAdapter = new RvShoesDealHeadAdapter(R.layout.item_rvhead_shoes_deal, loader);

        rvShoesDeal.setLayoutManager(new GridLayoutManager(getContext(),2));
        itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvShoesDeal.addItemDecoration(itemDecoration);
        rvShoesDealAdapter = new RvShoesDealAdapter(R.layout.item_rv_shoes_deal, loader);
        rvShoesDealAdapter.setHeaderView(headView);
    }

    private void setListener() {
        ptr.setRefreshListener(this);

    }

    private void setAdapter() {
        rvHead.setAdapter(rvShoesDealHeadAdapter);
        rvShoesDeal.setAdapter(rvShoesDealAdapter);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Log.e("cjx","is this?" + message);
    }

    @Override
    public void refresh() {
        mPresenter.refresh(params,Constant.TYPE_SHOESDEALENTITY_SHOESDEAL);
    }

    @Override
    public void loadMore() {
        mPresenter.loadMore(params,Constant.TYPE_SHOESDEALENTITY_SHOESDEAL);
    }
}
