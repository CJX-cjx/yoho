package com.umeng.soexample.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.umeng.soexample.app.utils.StatusUtils;
import com.umeng.soexample.di.module.MenuModule;
import com.umeng.soexample.domin.Action;
import com.umeng.soexample.mvc.ui.fragment.GuangFragment;
import com.umeng.soexample.mvp.contract.MenuContract;
import com.umeng.soexample.mvp.model.entity.MenuEntity;
import com.umeng.soexample.mvp.model.entity.UpdateEntity;
import com.umeng.soexample.mvp.presenter.MenuPresenter;
import com.umeng.soexample.mvp.ui.adapter.MenuAdapter;
import com.umeng.soexample.mvp.ui.fragment.CategoryFragment;
import com.umeng.soexample.mvp.ui.fragment.HomePageFragment;
import com.umeng.soexample.mvp.ui.fragment.MyFragment;
import com.umeng.soexample.mvp.ui.fragment.ShoesDealFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MenuPresenter> implements MenuContract.MenuView
        , BaseQuickAdapter.OnItemClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.btn_homepage)
    RadioButton btn_homepage;

    @BindView(R.id.btn_fenlei)
    RadioButton btn_fenlei;

    @BindView(R.id.btn_guang)
    RadioButton btn_guang;

    @BindView(R.id.btn_my)
    RadioButton btn_my;

    @BindView(R.id.rg_main)
    RadioGroup rg;

    @BindView(R.id.img_shoes)
    ImageView shoes;

    private SlidingMenu menu;
    private View menuView;
    private RecyclerView rv;
    private List<MenuEntity.Values> list;
    private MenuAdapter adapter;
    private MainBroadcast mainBroadcast;
    private HomePageFragment homePageFragment;
    private CategoryFragment categoryFragment;
    private ShoesDealFragment shoesDealFragment;
    private GuangFragment guangFragment;
    private MyFragment myFragment;
    private int versionCode;
    private String params;
    private long lastTime;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainCompoent.builder().appComponent(appComponent)
                .menuModule(new MenuModule(this))
                .build().inject(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        int a[] = {5,15,2,8,99,1,-2,4,5,10};
//        sort(a,0,a.length - 1);
//        for(int i : a)
//            Log.e("cjx","i = " + i);
        init();
        setListener();
        mPresenter.getMenu();
        setFragment(0);
        canUpdateProduct();
    }

    private void canUpdateProduct() {
        versionCode = AppUtils.getAppVersionCode();
        JSONObject object = new JSONObject();
        try {
            object.put("version_code","" + versionCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        Log.e("cjx","params:" + params);
        mPresenter.update(params);
    }

    private void sort(int a[],int low,int high) {

        int start = low;
        int end = high;
        int key = a[low];

        while (end >start) {
            while (end > start && a[end] >= key)
                end--;
            if (a[end] < key) {
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }

            while (end > start && a[start] <= key)
                start++;
            if (a[start] > key) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
        }
        if(start > low)
            sort(a,low,start - 1);
        if(end < high)
            sort(a,end + 1,high);
    }

    private void setListener() {
        adapter.setOnItemClickListener(this);
        rg.setOnCheckedChangeListener(this);
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_homepage.isChecked())
                    btn_homepage.setChecked(false);
                if(btn_fenlei.isChecked())
                    btn_fenlei.setChecked(false);
                if(btn_guang.isChecked())
                    btn_guang.setChecked(false);
                if(btn_my.isChecked())
                    btn_my.setChecked(false);
                setFragment(2);
            }
        });
    }

    private void init() {
        StatusUtils.setStatusBarBackground(this,Color.BLACK);
        btn_homepage.setChecked(true);
        menu = new SlidingMenu(getBaseContext());
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setMenu(R.layout.slidview);
        menu.setBehindOffset(100);
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        menuView = menu.getMenu();
        rv = menuView.findViewById(R.id.rv_slid);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new MenuAdapter(R.layout.item_rv_slide);
        rv.setAdapter(adapter);

        AnimationDrawable background = (AnimationDrawable) shoes.getBackground();
        background.start();

        mainBroadcast = new MainBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Action.MAINBROADCAST_ACTION);
        registerReceiver(mainBroadcast,filter);
    }

    @Override
    public void success(MenuEntity menuEntity) {
        list = menuEntity.getValues();
        adapter.setNewData(list);
    }

    @Override
    public void getData(UpdateEntity updateEntity) {
        if(updateEntity.getStatues().equals("1")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(updateEntity.getUrl()));
            startActivity(intent);
            Log.e("cjx","开始下载");
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        menu.showContent();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.btn_homepage :
                setFragment(0);
                break;
            case R.id.btn_fenlei :
                setFragment(1);
                break;
            case R.id.btn_guang :
                setFragment(3);
                break;
            case R.id.btn_my :
                setFragment(4);
                break;
        }
    }

    private void setFragment(int i) {
        if(i > 5)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(homePageFragment != null)
            transaction.hide(homePageFragment);
        if(categoryFragment != null)
            transaction.hide(categoryFragment);
        if(shoesDealFragment != null)
            transaction.hide(shoesDealFragment);
        if(guangFragment != null)
            transaction.hide(guangFragment);
        if(myFragment != null)
            transaction.hide(myFragment);

        if(i == 0) {
            if(homePageFragment == null) {
                homePageFragment = new HomePageFragment();
                transaction.add(R.id.fLayout_main,homePageFragment);
            }else {
                transaction.show(homePageFragment);
            }
        }else if(i == 1) {
            if(categoryFragment == null) {
                categoryFragment = new CategoryFragment();
                transaction.add(R.id.fLayout_main,categoryFragment);
            }else
                transaction.show(categoryFragment);
        }else if(i == 2) {
            if(shoesDealFragment == null) {
                shoesDealFragment = new ShoesDealFragment();
                transaction.add(R.id.fLayout_main,shoesDealFragment);
            }else
                transaction.show(shoesDealFragment);
        }else if(i == 3) {
            if(guangFragment == null) {
                guangFragment = new GuangFragment();
                transaction.add(R.id.fLayout_main,guangFragment);
            }else
                transaction.show(guangFragment);
        }else if(i == 4) {
            if(myFragment == null) {
                myFragment = new MyFragment();
                transaction.add(R.id.fLayout_main,myFragment);
            }else
                transaction.show(myFragment);
        }

        transaction.commit();

    }

    public class MainBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Action.MAINBROADCAST_ACTION)) {
                menu.showMenu();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mainBroadcast);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            long nowTime = System.currentTimeMillis();
            if(nowTime - lastTime > 2000) {
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                lastTime = nowTime;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
