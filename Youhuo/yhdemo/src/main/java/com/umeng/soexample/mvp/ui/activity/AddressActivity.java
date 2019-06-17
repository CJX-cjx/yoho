package com.umeng.soexample.mvp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.AddressListEntity;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.R;
import com.umeng.soexample.mvp.ui.adapter.RvAddressListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

public class AddressActivity extends BaseActivity<LrePresenter> implements LreContract.LreIView, View.OnClickListener {
    @BindView(R.id.rv_address)
    RecyclerView rv;

    @BindView(R.id.back_address)
    ImageView back;

    @BindView(R.id.addNewAddress)
    TextView addnewAddress;
    private JSONObject object;
    private SharedPreferences sharedPreferences;
    private String userId;
    private String params;
    private RvAddressListAdapter rvAddressListAdapter;
    private List<AddressListEntity.Values> values;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
        request();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);
        object = new JSONObject();
        try {
            object.put("user_id",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvAddressListAdapter = new RvAddressListAdapter(R.layout.item_rv_address);
    }

    private void setListener() {
        back.setOnClickListener(this);
        addnewAddress.setOnClickListener(this);
    }

    private void setAdapter() {
        rv.setAdapter(rvAddressListAdapter);
    }

    private void request() {
        mPresenter.requestData(params,Constant.TYPE_ADDRESSLIST_ACTIVITY);
    }

    @Override
    public void getData(BaseEntity entity, int type) {
        if(type == Constant.TYPE_ADDRESSLIST_ACTIVITY) {
            AddressListEntity addressListEntity = (AddressListEntity) entity;
            values = addressListEntity.getValues();
            if(values == null || values.size() == 0) {
                return;
            }
            rvAddressListAdapter.setNewData(values);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_address:
                finish();
                break;
            case R.id.addNewAddress:
                Intent intent = new Intent(AddressActivity.this,AddNewAddressActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == 2) {
            request();
        }
    }
}
