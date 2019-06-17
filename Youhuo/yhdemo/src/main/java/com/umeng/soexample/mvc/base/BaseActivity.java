package com.umeng.soexample.mvc.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        init();
        setListener();
    }

    protected abstract int layoutId();

    protected abstract void init();

    protected abstract void setListener();

    protected <T extends View>T f(int viewId) {
        return findViewById(viewId);
    }

    protected void start(Class clazz) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
