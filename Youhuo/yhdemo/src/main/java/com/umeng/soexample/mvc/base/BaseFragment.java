package com.umeng.soexample.mvc.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    protected View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layoutId(),null,false);
        init();
        setListener();
        return view;
    }

    protected abstract int layoutId();

    protected abstract void init();

    protected abstract void setListener();

    protected <T extends View>T f(int id) {
        return view.findViewById(id);
    }
}
