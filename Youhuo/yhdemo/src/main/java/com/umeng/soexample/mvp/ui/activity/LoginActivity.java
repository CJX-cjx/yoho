package com.umeng.soexample.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.umeng.soexample.R;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Action;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.LoginEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LrePresenter> implements LreContract.LreIView, View.OnClickListener {

    @BindView(R.id.cancel_login)
    ImageView cancel;

    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @BindView(R.id.username_login)
    EditText username;

    @BindView(R.id.pwd_login)
    EditText pwd;

    @BindView(R.id.btn_login)
    Button btn_login;

    private String default_username;
    private String edt_name;
    private String edt_pwd;
    private JSONObject object;
    private String params;
    private String key;
    private String pwdRSA;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
    }

    private void init() {
        key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJa4C5IKvNRcLWXiLFcF4F+i1S2QAusCMszlQeJV84UetEkcz" +
                "jUdJ4dWbnpRkeAmXCTzRHyO67XKS6GSCuKVO/sf7cyll0i6e+d0MSWB2CTxojYingZSV6ZQO8K1Z3fJyFYSHiRhDwJ4idC80zT" +
                "yKagsWV29uNa38iQYr4FwbNqZAgMBAAECgYAxV1k6W1eMMg0OsKeRabQVuwoNG3tJEnQtDdSu0zKg3vdohAyh6MR7EvmiA7g86" +
                "HH8CsPd/y/9WJe/8j6sBO0Ye9gt7eyQ2NiwWvlTuwNmngcSTapVvVI6NEyJFMfQt9PB1EHLNAXlz8jtJUyA7C48jReQD9p/SzAP" +
                "0VxG7lwyMQJBAOjE7hAZ/6fyP3DB1fG7jr9gONZcz3TUaqx6BUn4GKZnckW08ht9Xqcqft5Hthu8BbLM9ptQ0U8QZekrJwD6ya0" +
                "CQQClwstZMPu8jLhsgugVwodcG1mPEOiw9Yjnmt9+WTI07Ll2uFv//hRXBnahBBnZbucUYEbUY3kqUX9b3e9TmEodAkEAybPMb" +
                "xt4VDoxCy6Mi/pxChkBZ4/pHV3sSiU6bAyWn6vIc+sGWRfca5MBePA/N+1IKtY9Y/02QwL8rH5+P/URyQJAL/hdjORGFdzLi" +
                "muf6pwvPBKWKncEQCHuisghIZmClBpl2duklELddAnkztg2+tvDd/wcw14+NGb9aoKhvhl2aQJAbvcgoPU+xs0CjeexH+TS2" +
                "S/jKkTRpvP2CpPK/k71m13xWdE8RtMkYY1measRmlIwOfWze7ll/PGT4dxWf31FNg==";


        if(default_username != null && !default_username.equals(""))
            username.setText(default_username);
    }

    private void setListener() {
        cancel.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void request() {
        byte[] buf = EncryptUtils.encryptRSA(edt_pwd.getBytes(), Base64.decode(key.getBytes()
                ,Base64.DEFAULT),false,"RSA/ECB/PKCS1Padding");
        byte[] buff = Base64.encode(buf, Base64.DEFAULT);
        pwdRSA = new String(buff);
        object = new JSONObject();
        try {
            object.put("username",edt_name.trim());
            object.put("password", pwdRSA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_LOGIN_ACTIVITY);
    }

    @Override
    public void getData(BaseEntity entity, int type) {
        if(type == Constant.TYPE_LOGIN_ACTIVITY ) {
            LoginEntity loginEntity = (LoginEntity) entity;
            if(loginEntity.getValues() != null) {
                Intent intent = new Intent(Action.LOGINBROADCAST_ACTION);
                intent.putExtra("isLoginSuccess",true);
                intent.putExtra("Rsa_pwd",pwdRSA);
                intent.putExtra("userInfo",loginEntity.getValues().get(0));
                sendBroadcast(intent);
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
            case R.id.cancel_login:
                finish();
                break;
            case R.id.tvRegister:
                startActivityForResult(new Intent(LoginActivity.this,RegisterActivity.class),101);
                break;
            case R.id.btn_login:
                edt_name = username.getText().toString();
                edt_pwd = pwd.getText().toString();
                if(edt_name.equals("") || edt_pwd.equals(""))
                    return;
                request();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == 102)
            finish();
    }
}
