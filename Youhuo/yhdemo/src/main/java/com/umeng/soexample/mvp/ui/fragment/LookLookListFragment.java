package com.umeng.soexample.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.soexample.R;

import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.mvp.ui.widget.BannerImageLoader;
import com.umeng.soexample.mvp.ui.widget.RequestDialog;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView_youHuo;
import com.jwenfeng.library.pulltorefresh.view.LoadMoreView;
import com.umeng.soexample.app.utils.DpUtils;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.LookLookListEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.RvLookLookListAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class LookLookListFragment extends BaseFragment<LrePresenter> implements LreContract.LreIView
        , BaseRefreshListener, BaseQuickAdapter.OnItemChildClickListener, UMShareListener {

    @BindView(R.id.ptr_look_look_list)
    PullToRefreshLayout ptr;

    @BindView(R.id.rv_look_look_vp)
    RecyclerView rv_look_look_vp;

    private View view;
    private JSONObject object;
    private String params;
    private Boolean showBanner;
    private ImageLoader loader;
    private List<String> imageUrls = new ArrayList<>();
    private Banner banner;
    private RvLookLookListAdapter rvLookLookListAdapter;
    private View headView;
    private View itemView;
    private RequestDialog dialog;

    @SuppressLint("ValidFragment")
    public LookLookListFragment(boolean showBanner) {
        this.showBanner = showBanner;
    }

    @Override
    public void getData(BaseEntity entity, int type) {
        switch (type) {
            case Constant.TYPE_LISTENTITY_LOOKLOOK :
                LookLookListEntity lookLookListEntity = (LookLookListEntity) entity;
                List<LookLookListEntity.Banner> bannerList = lookLookListEntity.getBanner();
                for(int i = 0;i < bannerList.size();i++)
                    imageUrls.add(bannerList.get(i).getRecommend_url());
                banner.setImages(imageUrls).start();
                List<LookLookListEntity.Values> values = lookLookListEntity.getValues();
                rvLookLookListAdapter.setNewData(values);
                if(!showBanner)
                    rvLookLookListAdapter.removeHeaderView(headView);
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void refreshCallBack(BaseEntity entity) {
        if(entity instanceof LookLookListEntity) {
            LookLookListEntity lookLookListEntity = (LookLookListEntity) entity;
            rvLookLookListAdapter.setNewData(lookLookListEntity.getValues());
        }
        ptr.finishRefresh();
    }

    @Override
    public void loadMoreCallBack(BaseEntity entity) {
        if(entity instanceof LookLookListEntity) {
            LookLookListEntity lookLookListEntity = (LookLookListEntity) entity;
            if(lookLookListEntity.getValues() == null) {
                ptr.finishLoadMore();
                Toast.makeText(mContext, "滑到底了，(。・＿・。)ﾉI’m sorry~", Toast.LENGTH_SHORT).show();
                return;
            }
            rvLookLookListAdapter.addData(lookLookListEntity.getValues());
        }
        ptr.finishLoadMore();
    }

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
        view = inflater.inflate(R.layout.fragment_look_look_vp, null);
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

    private void init() {
        dialog = RequestDialog.getInstance(getContext());
        headView = LayoutInflater.from(getContext()).inflate(R.layout.head_look_look_list, null);
        headView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,DpUtils.dip2px(getContext(),150)));
        itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_rv_look_look_vp, null);
        banner = headView.findViewById(R.id.banner_look_look_vp);
        ptr.setHeaderView(new HeadRefreshView_youHuo(getContext()));
        ptr.setFooterView(new LoadMoreView(getContext()));
        loader = new ImageLoaderUtils(getContext()).getImageLoader();
        banner.setImageLoader(new BannerImageLoader())
              .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
              .setIndicatorGravity(BannerConfig.CENTER)
              .setDelayTime(1500);

        rv_look_look_vp.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvLookLookListAdapter = new RvLookLookListAdapter(R.layout.item_rv_look_look_vp, loader);
        rvLookLookListAdapter.setHeaderView(headView);
    }

    private void setListener() {
        ptr.setRefreshListener(this);
        rvLookLookListAdapter.setOnItemChildClickListener(this);
    }

    private void setAdapter() {
        rv_look_look_vp.setAdapter(rvLookLookListAdapter);
    }

    private void requestData() {
        dialog.show();
        object = new JSONObject();
        try {
            object.put("categoryid","1");
            object.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_LISTENTITY_LOOKLOOK);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Log.e("cjx",TAG + ":" + message);
    }

    @Override
    public void refresh() {
        mPresenter.refresh(params,Constant.TYPE_LISTENTITY_LOOKLOOK);
    }

    @Override
    public void loadMore() {
        mPresenter.loadMore(params,Constant.TYPE_LISTENTITY_LOOKLOOK);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.imgFavorite_looklookVp :
                break;
            case R.id.imgShare_looklookVp :
                UMImage image = new UMImage(getActivity(), "https://pic.baike.soso.com/ugc/baikepic2/0/20170512152539-2114283836.jpg/800");
                UMWeb web = new UMWeb("http://www.baidu.com");
                web.setTitle("麻瓜胖");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("此^(*￣(oo)￣)^乃天下第一骚胖");//描述

                new ShareAction(getActivity())
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ)
                        .setCallback(this).open();
//                new ShareAction(getActivity()).withText("hello")
//                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
//                        .setCallback(this).open();
                break;
        }
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.e("cjx","start");
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.e("cjx",share_media.toString());
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.e("cjx",throwable.getMessage());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
