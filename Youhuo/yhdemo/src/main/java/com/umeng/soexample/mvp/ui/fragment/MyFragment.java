package com.umeng.soexample.mvp.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Action;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.LoginEntity;
import com.umeng.soexample.mvp.model.entity.ShoesDealEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.activity.AddressActivity;
import com.umeng.soexample.mvp.ui.activity.LoginActivity;
import com.umeng.soexample.mvp.ui.activity.OrderActivity;
import com.umeng.soexample.mvp.ui.activity.PersonalActivity;
import com.umeng.soexample.mvp.ui.adapter.RvMyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFragment extends BaseFragment<LrePresenter> implements LreContract.LreIView, View.OnClickListener {

    @BindView(R.id.rv_my)
    RecyclerView rv;

    @BindView(R.id.tv_loginRegister)
    TextView login;

    @BindView(R.id.rl_default)
    RelativeLayout rl_default;

    @BindView(R.id.rl_logined)
    RelativeLayout rl_logined;

    @BindView(R.id.imgIconMy)
    ImageView userIcon;

    @BindView(R.id.tvIdMy)
    TextView userId;

    @BindView(R.id.allOrder)
    TextView allOrder;

    @BindView(R.id.tv_addressManageMy)
    TextView addressManage;

    @BindView(R.id.rl_youhuiquan)
    RelativeLayout rl_youhuiquan;

    @BindView(R.id.rl_goods_shoucang)
    RelativeLayout rl_goods_shoucang;

    @BindView(R.id.rl_liulanjilu)
    RelativeLayout rl_liulanjilu;

    private View view;
    private GridLayoutManager gridLayoutManager;
    private DividerItemDecoration itemDecorationVertical;
    private DividerItemDecoration itemDecorationHorizontal;
    private RvMyAdapter rvMy;
    private JSONObject object;
    private String params;
    private List<ShoesDealEntity.Values> data = new ArrayList<>();
    private LoginReceiver loginReceiver;
    private IntentFilter intentFilter;
    private LoginEntity.ValuesBean userInfo;
    private ImageLoader loader;
    private boolean success;
    private boolean isLogined;

    @Override
    public void getData(BaseEntity entity, int type) {
        switch (type) {
            case Constant.TYPE_SHOESDEALENTITY_SHOESDEAL :
                ShoesDealEntity shoesDealEntity = (ShoesDealEntity) entity;
                for(ShoesDealEntity.Values values : shoesDealEntity.getValues())
                    data.add(values);
                rvMy.refresh(data);
                break;
            case Constant.TYPE_LOGIN_ACTIVITY:
                LoginEntity loginEntity = (LoginEntity) entity;
                userInfo = loginEntity.getValues().get(0);
                if(loginEntity.getMsg().equals("请求成功") && userInfo != null) {
                    isLogined = true;
                    rl_default.setVisibility(View.GONE);
                    rl_logined.setVisibility(View.VISIBLE);
                    loader.loadImage(getContext(),ImageConfigImpl.builder()
                    .url(Api.IMG_DOMAIN + userInfo.getUser_head())
                    .placeholder(R.drawable.share_list_placeholder_s_background)
                    .errorPic(R.drawable.tt_default_image_error)
                    .imageView(userIcon)
                    .build());
                    userId.setText(userInfo.getNick_name());
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
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder()
                .appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.head_rv_my, null);
        ButterKnife.bind(this,view);
        SharedPreferences preferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean isLoginSuccess = preferences.getBoolean("isLoginSuccess", false);
        if(isLoginSuccess) {
            String name = preferences.getString("userName",null);
            String pwd = preferences.getString("Rsa_pwd",null);
            if(name != null && pwd != null) {
                JSONObject object = new JSONObject();
                try {
                    object.put("username",name);
                    object.put("password",pwd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mPresenter.requestData(object.toString(),Constant.TYPE_LOGIN_ACTIVITY);
            }
        }
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
        loginReceiver = new LoginReceiver();
        intentFilter = new IntentFilter(Action.LOGINBROADCAST_ACTION);
        getActivity().registerReceiver(loginReceiver,intentFilter);
        loader = new ImageLoaderUtils(getContext()).getImageLoader();
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(gridLayoutManager);
        itemDecorationVertical = new DividerItemDecoration(getContext(), GridLayoutManager.VERTICAL);
        itemDecorationVertical.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider_vertical));
        itemDecorationHorizontal = new DividerItemDecoration(getContext(), GridLayoutManager.HORIZONTAL);
        itemDecorationHorizontal.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider_horizontal));
        rv.addItemDecoration(itemDecorationVertical);
        rv.addItemDecoration(itemDecorationHorizontal);
        rvMy = new RvMyAdapter();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                //Log.e("cjx","size:" + i);
                if (i == 1) {
                    return 1;
                }
                return 1;
            }
        });
    }

    private void setListener() {
        login.setOnClickListener(this);
        allOrder.setOnClickListener(this);
        addressManage.setOnClickListener(this);
        rl_youhuiquan.setOnClickListener(this);
        rl_goods_shoucang.setOnClickListener(this);
        rl_liulanjilu.setOnClickListener(this);
        userIcon.setOnClickListener(this);
    }

    private void setAdapter() {
        rv.setAdapter(rvMy);
    }

    private void request() {
        object = new JSONObject();
        try {
            object.put("page","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_SHOESDEALENTITY_SHOESDEAL);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Log.e("cjx",message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_loginRegister:
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.allOrder:
                if(!isLogined) {
                    Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent1 = new Intent(getActivity(),OrderActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_addressManageMy:
                if(!isLogined) {
                    Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent2 = new Intent(getActivity(),AddressActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_youhuiquan:
                if(!isLogined) {
                    Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.rl_goods_shoucang:
                if(!isLogined) {
                    Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.rl_liulanjilu:
                if(!isLogined) {
                    Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.imgIconMy:
                Intent intent3 = new Intent(getActivity(),PersonalActivity.class);
                startActivity(intent3);
                break;
        }
    }

    class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == Action.LOGINBROADCAST_ACTION) {
                success = intent.getBooleanExtra("isLoginSuccess", false);
                if(success) {
                    isLogined = true;
                    userInfo = (LoginEntity.ValuesBean) intent.getSerializableExtra("userInfo");
                    rl_default.setVisibility(View.GONE);
                    rl_logined.setVisibility(View.VISIBLE);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoginSuccess",true);
                    editor.putString("userName",userInfo.getUser_name());
                    editor.putString("userId",userInfo.getUser_id());
                    editor.putString("Rsa_pwd",intent.getStringExtra("Rsa_pwd"));
                    editor.commit();
                    loader.loadImage(getContext(),ImageConfigImpl.builder()
                            .url(Api.IMG_DOMAIN + userInfo.getUser_head())
                            .placeholder(R.drawable.share_list_placeholder_s_background)
                            .errorPic(R.drawable.tt_default_image_error)
                            .imageView(userIcon)
                            .build());
                    userId.setText(userInfo.getNick_name());
                }else {
                    rl_default.setVisibility(View.VISIBLE);
                    rl_logined.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(loginReceiver);
        super.onDestroy();
    }
}
