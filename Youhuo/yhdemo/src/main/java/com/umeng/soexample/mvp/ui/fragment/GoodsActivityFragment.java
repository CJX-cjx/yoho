package com.umeng.soexample.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.GoodsActivityEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.RvGoodsActivityAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsActivityFragment extends BaseFragment<LrePresenter> implements LreContract.LreIView {

    @BindView(R.id.rv_goodsActivity)
    RecyclerView rv;

    private View view;
    private RvGoodsActivityAdapter rvGoodsActivityAdapter;
    private JSONObject object;
    private String params;

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
        view = inflater.inflate(R.layout.fragment_goodsactivity, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
        request();
    }

    private void init() {
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvGoodsActivityAdapter = new RvGoodsActivityAdapter(R.layout.item_rv_goodsactivity, new ImageLoaderUtils(getContext()).getImageLoader());

    }

    private void setListener() {

    }

    private void setAdapter() {
        rv.setAdapter(rvGoodsActivityAdapter);
    }

    private void request() {
        object = new JSONObject();
        try {
            object.put("categoryid","1");
            object.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_GOODSENTITY_COMMUNITYGOODSACTIVITY);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void getData(BaseEntity entity, int type) {
        if(type == Constant.TYPE_GOODSENTITY_COMMUNITYGOODSACTIVITY) {
            GoodsActivityEntity goodsActivityEntity = (GoodsActivityEntity) entity;
            rvGoodsActivityAdapter.setNewData(goodsActivityEntity.getValues());
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
