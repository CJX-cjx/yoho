package com.umeng.soexample.mvp.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.blankj.utilcode.util.ImageUtils;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView_youHuo;
import com.jwenfeng.library.pulltorefresh.view.LoadMoreView;
import com.umeng.soexample.app.utils.DpUtils;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.LookShowEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.RvLookShowAdapter;
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

public class LookShowFragment extends BaseFragment<LrePresenter> implements LreContract.LreIView, BaseRefreshListener {

    @BindView(R.id.rv_look_show)
    RecyclerView rv;

    @BindView(R.id.ptr_look_show)
    PullToRefreshLayout ptr;

    private View view;
    private Banner banner;
    private View bannerView;
    private JSONObject object;
    private String params;
    private ImageLoader loader;
    private RequestDialog dialog;
    private RvLookShowAdapter rvLookShowAdapter;
    private List<String> bannerList = new ArrayList<>();
    private List<LookShowEntity.Values> values = new ArrayList<>();
    private TextView indicatorOne;
    private TextView indicatorTwo;
    private TextView indicatorThree;
    private TextView indicatorFour;
    private TextView indicatorFive;

    @Override
    public void getData(BaseEntity entity, int type) {
        switch (type) {
            case Constant.TYPE_SHOWENTITY_LOOKSHOW :
                LookShowEntity lookShowEntity = (LookShowEntity) entity;
                for(int i = 0;i < lookShowEntity.getBanner().size();i++) {
                    if(i == 5 || i == 6)
                        continue;
                    bannerList.add(lookShowEntity.getBanner().get(i).getRecommend_url());
                }
                banner.setImages(bannerList).start();
                values = lookShowEntity.getValues();
                rvLookShowAdapter.setNewData(values);
                break;
        }
        if(bannerList.size() != 0 && values.size() != 0)
            dialog.dismiss();
    }

    @Override
    public void refreshCallBack(BaseEntity entity) {
        if(entity instanceof LookShowEntity) {
            LookShowEntity lookShowEntity = (LookShowEntity) entity;
            rvLookShowAdapter.setNewData(lookShowEntity.getValues());
            ptr.finishRefresh();
        }
    }

    @Override
    public void loadMoreCallBack(BaseEntity entity) {
        if(entity instanceof LookShowEntity) {
            LookShowEntity lookShowEntity = (LookShowEntity) entity;
            if(lookShowEntity.getValues() == null) {
                ptr.finishLoadMore();
                Toast.makeText(mContext, "滑到底了，(。・＿・。)ﾉI’m sorry~", Toast.LENGTH_SHORT).show();
                return;
            }
            rvLookShowAdapter.addData(lookShowEntity.getValues());
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
        view = inflater.inflate(R.layout.fragment_show, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
        dialog.show();
        requestData();
    }

    private void init() {
        dialog = RequestDialog.getInstance(getContext());
        loader = new ImageLoaderUtils(getContext()).getImageLoader();
        ptr.setHeaderView(new HeadRefreshView_youHuo(getContext()));
        ptr.setFooterView(new LoadMoreView(getContext()));
        ptr.setRefreshListener(this);
        bannerView = LayoutInflater.from(getContext()).inflate(R.layout.head_look_show, null);
        bannerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,DpUtils.dip2px(getContext(),150)));
        indicatorOne = bannerView.findViewById(R.id.indicatorOne);
        indicatorTwo = bannerView.findViewById(R.id.indicatorTwo);
        indicatorThree = bannerView.findViewById(R.id.indicatorThree);
        indicatorFour = bannerView.findViewById(R.id.indicatorFour);
        indicatorFive = bannerView.findViewById(R.id.indicatorFive);
        banner = bannerView.findViewById(R.id.banner_look_show);
        banner.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setDelayTime(1500);

        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvLookShowAdapter = new RvLookShowAdapter(R.layout.item_rv_look_show, loader);
        rvLookShowAdapter.setHeaderView(bannerView);
    }

    private void setListener() {
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    indicatorOne.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_normal));
                    indicatorTwo.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_normal));
                    indicatorThree.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_normal));
                    indicatorFour.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_normal));
                    indicatorFive.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_normal));

                    if(i == 0)
                        indicatorOne.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_selected));
                    else if(i == 1)
                        indicatorTwo.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_selected));
                    else if(i == 2)
                        indicatorThree.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_selected));
                    else if(i == 3)
                        indicatorFour.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_selected));
                    else if(i == 4)
                        indicatorFive.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.indictor_banner_selected));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setAdapter() {
        rv.setAdapter(rvLookShowAdapter);
    }

    private void requestData() {
        object = new JSONObject();
        try {
            object.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_SHOWENTITY_LOOKSHOW);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Log.e("cjx",TAG + "(LookShowFragment):");
    }

    @Override
    public void refresh() {
        mPresenter.refresh(params,Constant.TYPE_SHOWENTITY_LOOKSHOW);
    }

    @Override
    public void loadMore() {
        mPresenter.loadMore(params,Constant.TYPE_SHOWENTITY_LOOKSHOW);
    }
}
