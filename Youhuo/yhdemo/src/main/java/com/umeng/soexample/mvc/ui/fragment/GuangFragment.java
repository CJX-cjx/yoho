package com.umeng.soexample.mvc.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.umeng.soexample.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.umeng.soexample.domin.Action;
import com.umeng.soexample.mvc.base.BaseFragment;
import com.umeng.soexample.mvc.entity.TabEntity;
import com.umeng.soexample.mvp.ui.fragment.CommunityFragment;
import com.umeng.soexample.mvp.ui.fragment.LookLookCategoryFragment;
import com.umeng.soexample.mvp.ui.fragment.LookShowFragment;

import java.util.ArrayList;
import java.util.Objects;

public class GuangFragment extends BaseFragment implements View.OnClickListener {
    private CommonTabLayout ctl;
    private ArrayList<CustomTabEntity> customTabEntities = new ArrayList<>();
    private String titles[];
    private ImageView menu;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private LookLookCategoryFragment lookLookCategoryFragment;
    private CommunityFragment communityFragment;
    private LookShowFragment lookShowFragment;

    @Override
    protected int layoutId() {
        return R.layout.fragment_look;
    }

    @Override
    protected void init() {
        ctl = f(R.id.ctl_guang);
        menu = f(R.id.menu_guang);
        titles = new String[] {"逛","社区","ShOw"};
        for(int i = 0;i < 3;i++) {
            customTabEntities.add(new TabEntity(titles[i]));
        }
        ctl.setTextsize(18);
        lookLookCategoryFragment = new LookLookCategoryFragment();
        communityFragment = new CommunityFragment();
        lookShowFragment = new LookShowFragment();
        fragments.add(lookLookCategoryFragment);
        fragments.add(communityFragment);
        fragments.add(lookShowFragment);
        ctl.setTabData(customTabEntities,Objects.requireNonNull(getActivity()),R.id.fLayout_guang,fragments);
    }

    @Override
    protected void setListener() {
        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_guang:
                getActivity().sendBroadcast(new Intent(Action.MAINBROADCAST_ACTION));
                break;
        }
    }
}
