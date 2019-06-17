package com.umeng.soexample.mvp.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.umeng.soexample.R;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class AddNewAddressActivity extends BaseActivity<LrePresenter> implements LreContract.LreIView, View.OnClickListener {
    @BindView(R.id.back_addNewAddress)
    ImageView back;

    @BindView(R.id.userName)
    EditText userName;

    @BindView(R.id.phone_addNewAddress)
    EditText phone;

    @BindView(R.id.area_addNewAddress)
    EditText area;

    @BindView(R.id.detail_addNewAddress)
    EditText detail;

    @BindView(R.id.home_addNewAddress)
    TextView homeView;

    @BindView(R.id.company_addNewAddress)
    TextView companyView;

    @BindView(R.id.school_addNewAddress)
    TextView schoolView;

    @BindView(R.id.else_addNewAddress)
    TextView otherView;

    @BindView(R.id.saveAddress)
    TextView save;

    private SharedPreferences sharedPreferences;
    private String userId;
    private String name;
    private String phoneNumber;
    private String areaValue;
    private String detailValue;
    private String tagValue = null;
    private JSONObject object;
    private String params;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_addnewaddress;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);

    }

    private void setListener() {
        back.setOnClickListener(this);
        homeView.setOnClickListener(this);
        companyView.setOnClickListener(this);
        schoolView.setOnClickListener(this);
        otherView.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void request() {
        object = new JSONObject();
        try {
            object.put("user_id",userId);
            object.put("user_name",name);
            object.put("phone",phoneNumber);
            object.put("address_area",areaValue);
            object.put("address_detail",detailValue);
            object.put("address_tag",tagValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_ADDNEWADDRESS_ACTIVITY);
    }

    @Override
    public void getData(BaseEntity entity, int type) {
        if(type == Constant.TYPE_ADDNEWADDRESS_ACTIVITY) {
            if(entity.getMsg().equals("添加成功")) {
                setResult(2);
                finish();
            }
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
            case R.id.back_addNewAddress:
                finish();
            case R.id.saveAddress:
                name = userName.getText().toString();
                phoneNumber = phone.getText().toString();
                areaValue = area.getText().toString();
                detailValue = detail.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(areaValue) || TextUtils.isEmpty(detailValue) || tagValue == null)
                    return;
                request();
                break;
            case R.id.home_addNewAddress:
                setTag(true,false,false,false);
                break;
            case R.id.company_addNewAddress:
                setTag(false,true,false,false);
                break;
            case R.id.school_addNewAddress:
                setTag(false,false,true,false);
                break;
            case R.id.else_addNewAddress:
                setTag(false,false,false,true);
                break;
        }
    }
    private void setTag(boolean home,boolean company,boolean school,boolean other) {
        if(home) {
            homeView.setTextColor(0xffffffff);
            homeView.setBackgroundColor(0xffff0000);
            tagValue = "家";
        }else {
            homeView.setTextColor(0xff000000);
            homeView.setBackgroundColor(0xffffffff);
        }
        if(company) {
            companyView.setTextColor(0xffffffff);
            companyView.setBackgroundColor(0xffff0000);
            tagValue = "公司";
        }else {
            companyView.setTextColor(0xff000000);
            companyView.setBackgroundColor(0xffffffff);
        }
        if(school) {
            schoolView.setTextColor(0xffffffff);
            schoolView.setBackgroundColor(0xffff0000);
            tagValue = "学校";
        }else {
            schoolView.setTextColor(0xff000000);
            schoolView.setBackgroundColor(0xffffffff);
        }
        if(other) {
            otherView.setTextColor(0xffffffff);
            otherView.setBackgroundColor(0xffff0000);
            tagValue = "其他";
        }else {
            otherView.setTextColor(0xff000000);
            otherView.setBackgroundColor(0xffffffff);
        }
    }
}
