package com.bwie.servicemodule.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected boolean firstFlag = true;

    //fragment与viewpager组合使用时当前fragment显示时调用
    //添加判断是否第一次显示->第一次显示调用initData方法加载网络数据
    //第二次显示时->不调用initData方法
    //  initData();->网络请求方法调用
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&firstFlag){
            initData();
            firstFlag = false;
        }
    }

    public abstract void initData();

}
