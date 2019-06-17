package com.umeng.soexample.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.soexample.R;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.mvp.ui.widget.RequestDialog;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.view.HeadRefreshView_youHuo;
import com.jwenfeng.library.pulltorefresh.view.LoadMoreView;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.LookLookCategoryEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LookLookCategoryFragment extends BaseFragment<LrePresenter> implements LreContract.LreIView{

    private View view;

    @BindView(R.id.stl_look_look)
    SlidingTabLayout stl;

    @BindView(R.id.vp_look_look)
    ViewPager vp;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String titles[] = new String[5];
    private JSONObject object;
    private String params;
    private LookLookListFragment lookLookListFragment;
    private RequestDialog dialog;

    @Override
    public void getData(BaseEntity entity, int type) {
        switch (type) {
            case Constant.TYPE_CATEGORYENTITY_LOOKLOOK :
                LookLookCategoryEntity lookLookCategoryEntity = (LookLookCategoryEntity) entity;
                for(int i = 0;i < lookLookCategoryEntity.getValues().size();i++) {
                    titles[i] = lookLookCategoryEntity.getValues().get(i).getSee_category_name();
                }
                stl.setViewPager(vp,titles,Objects.requireNonNull(getActivity()),fragments);
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void refreshCallBack(BaseEntity entity) {

    }

    @Override
    public void loadMoreCallBack(BaseEntity entity) {

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
        view = inflater.inflate(R.layout.fragment_look_look, null);
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
        lookLookListFragment = new LookLookListFragment(true);
        fragments.add(lookLookListFragment);
        lookLookListFragment = new LookLookListFragment(false);
        fragments.add(lookLookListFragment);
        lookLookListFragment = new LookLookListFragment(false);
        fragments.add(lookLookListFragment);
        lookLookListFragment = new LookLookListFragment(false);
        fragments.add(lookLookListFragment);
        lookLookListFragment = new LookLookListFragment(false);
        fragments.add(lookLookListFragment);
    }

    private void setListener() {

    }

    private void setAdapter() {

    }

    private void requestData() {
        dialog.show();
        object = new JSONObject();
        try {
            object.put("menuid","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_CATEGORYENTITY_LOOKLOOK);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Log.e("cjx",TAG + ":" + message);
    }
}
