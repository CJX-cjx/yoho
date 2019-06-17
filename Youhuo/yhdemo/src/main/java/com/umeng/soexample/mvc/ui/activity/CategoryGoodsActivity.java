package com.umeng.soexample.mvc.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.soexample.R;
import com.umeng.soexample.mvc.base.BaseActivity;
import com.umeng.soexample.mvc.entity.Ev_goodsActivityEntity;
import com.umeng.soexample.mvp.ui.adapter.Ev_goodsActivityAdapter;
import com.umeng.soexample.mvp.ui.fragment.GoodsActivityFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryGoodsActivity extends BaseActivity implements View.OnClickListener, OnTabSelectListener, SlidingMenu.OnClosedListener {

    @BindView(R.id.back_goodsActivity)
    ImageView back;

    @BindView(R.id.goodsName_categoryActivity)
    TextView categoryName;

    @BindView(R.id.more_goodsActivity)
    ImageView more;

    @BindView(R.id.stl_goodsActivity)
    SlidingTabLayout stl;

    @BindView(R.id.vp_goodsActivity)
    ViewPager vp;

    @BindView(R.id.imgMoren_goodsActivity)
    ImageView moren;

    @BindView(R.id.imgUp_goodsActivity)
    ImageView up;

    private String categoryId;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private GoodsActivityFragment fragment1;
    private GoodsActivityFragment fragment2;
    private GoodsActivityFragment fragment3;
    private GoodsActivityFragment fragment4;
    private GoodsActivityFragment fragment5;
    private boolean isUp = true;
    private SlidingMenu slidingMenu;
    private View slideView;
    private TextView tv_cuxiao;
    private TextView tv_vip;
    private List<Integer> locations = new ArrayList<>();
    private ExpandableListView ev;
    private List<Ev_goodsActivityEntity> paraments = new ArrayList<>();
    private List<Ev_goodsActivityEntity.Children> childs = new ArrayList<>();
    private String titles[];
    private Ev_goodsActivityAdapter ev_goodsActivityAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_categotygoods;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        categoryName.setText(getIntent().getStringExtra("goodsName"));
        categoryId = getIntent().getStringExtra("categoryId");
        fragment1 = new GoodsActivityFragment();
        fragment2 = new GoodsActivityFragment();
        fragment3 = new GoodsActivityFragment();
        fragment4 = new GoodsActivityFragment();
        fragment5 = new GoodsActivityFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        stl.setViewPager(vp,new String[]{"默认","关注","新品","价格","筛选"},this,fragments);
        slidingMenu = new SlidingMenu(this);
        slideView = LayoutInflater.from(this).inflate(R.layout.slidingmenu_goods_activity, null);
        tv_cuxiao = slideView.findViewById(R.id.tv_cuxiao);
        ev = slideView.findViewById(R.id.ev);
        tv_vip = slideView.findViewById(R.id.tv_vip);
        slidingMenu.setMenu(slideView);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setBehindOffset(200);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        ev_goodsActivityAdapter = new Ev_goodsActivityAdapter();
        ev.setAdapter(ev_goodsActivityAdapter);
        makeData();
        ev_goodsActivityAdapter.refresh(paraments);
    }

    private void makeData() {
        titles = new String[] {"品牌","价格","折扣","颜色","尺码"};
        for(int i = 0;i < 5;i++) {
            childs.clear();
            for(int j = 0;j < 9;j++) {
                childs.add(new Ev_goodsActivityEntity.Children("麻瓜胖"));
            }
            paraments.add(new Ev_goodsActivityEntity(childs,titles[i]));
        }
    }

    @Override
    protected void setListener() {
        back.setOnClickListener(this);
        more.setOnClickListener(this);
        stl.setOnTabSelectListener(this);
        slidingMenu.setOnClosedListener(this);
        tv_cuxiao.setOnClickListener(this);
        tv_vip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_goodsActivity:
                finish();
                break;
            case R.id.more_goodsActivity:
                Log.e("cjx","more");
                break;
            case R.id.tv_cuxiao:
                tv_cuxiao.setBackgroundColor(0xffffffff);
                tv_vip.setBackgroundColor(0xffcccccc);
                break;
            case R.id.tv_vip:
                tv_cuxiao.setBackgroundColor(0xffcccccc);
                tv_vip.setBackgroundColor(0xffffffff);
                break;
        }
    }

    @Override
    public void onTabSelect(int position) {
        locations.add(position);
        moren.setImageResource(R.drawable.js_brandstore_image_filter_3_down_h);
        up.setImageResource(R.drawable.js_brandstore_image_filter_shared_segmentedcontrol_other_normal);
        isUp = true;

        if(position == 0) {
            moren.setImageResource(R.drawable.js_brandstore_image_filter_3_down);
        }

        if(position == 3) {
            if(isUp)
                up.setImageResource(R.drawable.js_brandstore_image_filter_shared_segmentedcontrol_other_up);
            else
                up.setImageResource(R.drawable.js_brandstore_image_filter_shared_segmentedcontrol_other_down);
            isUp = !isUp;
        }
        if(position == 4) {
            slidingMenu.showMenu();
        }

    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onClosed() {
        if(locations.size() == 1 || locations.get(locations.size() - 2) == 4)
            stl.setCurrentTab(0);
        else
            stl.setCurrentTab(locations.get(locations.size() - 2));
    }
}
