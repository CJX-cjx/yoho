package com.umeng.soexample.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView_youHuo;
import com.jwenfeng.library.pulltorefresh.view.LoadMoreView;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.CommunityEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.Rv_communityAdapter;
import com.umeng.soexample.mvp.ui.adapter.VpHeadCommunityAdapter;
import com.umeng.soexample.mvp.ui.widget.BannerImageLoader;
import com.umeng.soexample.mvp.ui.widget.RequestDialog;
import com.umeng.soexample.mvp.ui.widget.VpTransform;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityFragment extends BaseFragment<LrePresenter> implements LreContract.LreIView, BaseRefreshListener {

    @BindView(R.id.ptr_community_fragment)
    PullToRefreshLayout ptr;

    @BindView(R.id.rv_community_look)
    RecyclerView rv;

    private View view;
    private RequestDialog dialog;
    private View headView;
    private Banner banner;
    private ViewPager vp;
    private ImageLoader loader;
    private Rv_communityAdapter rv_communityAdapter;
    private JSONObject object;
    private String params;
    private List<String> bannerList = new ArrayList<>();
    private List<CommunityEntity.Values> values = new ArrayList<>();
    private VpHeadCommunityAdapter vpHeadCommunityAdapter;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder()
                .appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_community, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void getData(BaseEntity entity, int type) {
        switch (type) {
            case Constant.TYPE_COMMUNITYENTITY_COMMUNITY:
                CommunityEntity communityEntity = (CommunityEntity) entity;
                for(CommunityEntity.Banner bannerValues : communityEntity.getBanner())
                    bannerList.add(bannerValues.getRecommend_url());
                banner.setImages(bannerList).start();
                values = communityEntity.getValues();
                rv_communityAdapter.setNewData(values);
                break;
        }
        if(bannerList.size() != 0 && values.size() != 0)
            dialog.dismiss();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
        dialog.show();
        request();
    }

    private void init() {
        loader = new ImageLoaderUtils(getContext()).getImageLoader();
        dialog = RequestDialog.getInstance(getContext());
        headView = LayoutInflater.from(getContext()).inflate(R.layout.head_rv_community_look, null);
        banner = headView.findViewById(R.id.banner_head_community);
        banner.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setDelayTime(1500);
        vp = headView.findViewById(R.id.vp_head_community);
        vpHeadCommunityAdapter = new VpHeadCommunityAdapter(getContext());
        ptr.setHeaderView(new HeadRefreshView_youHuo(getContext()));
        ptr.setFooterView(new LoadMoreView(getContext()));
        ptr.setCanRefresh(false);
        ptr.setCanLoadMore(false);
        ptr.setRefreshListener(this);

        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv_communityAdapter = new Rv_communityAdapter(R.layout.item_rv_community_look, loader);
        rv_communityAdapter.setHeaderView(headView);

    }

    private void setListener() {

    }

    private void setAdapter() {
        rv.setAdapter(rv_communityAdapter);
        vp.setAdapter(vpHeadCommunityAdapter);
        vp.setOffscreenPageLimit(2);
        vp.setPageMargin(5);
        vp.setPageTransformer(false,new VpTransform());
    }

    private void request() {
        object = new JSONObject();
        try {
            object.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_COMMUNITYENTITY_COMMUNITY);
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
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }
}
