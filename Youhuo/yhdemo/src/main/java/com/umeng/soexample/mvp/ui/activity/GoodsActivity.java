package com.umeng.soexample.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.BrandActivityEntity;
import com.umeng.soexample.mvp.model.entity.BrandEntity;
import com.umeng.soexample.mvp.model.entity.CarGoodsEntity;
import com.umeng.soexample.mvp.model.entity.GoodsActivityEntity;
import com.umeng.soexample.mvp.model.entity.GoodsEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;
import com.umeng.soexample.mvp.ui.adapter.Lv_goodsActivityAdapter;
import com.umeng.soexample.mvp.ui.adapter.RvGoodsAdapter;
import com.umeng.soexample.mvp.ui.adapter.Rv_brand_activityAdapter;
import com.umeng.soexample.mvp.ui.adapter.Vp_goodsActivityAdapter;
import com.umeng.soexample.mvp.ui.widget.MyHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsActivity extends BaseActivity<LrePresenter> implements LreContract.LreIView
        , View.OnClickListener, ViewPager.OnPageChangeListener, PopupWindow.OnDismissListener {

    @BindView(R.id.back_goodsActivity)
    ImageView back;

    @BindView(R.id.share_goodsActivity)
    ImageView share;

    @BindView(R.id.rv_goodsActivity)
    RecyclerView rv;

    @BindView(R.id.rl_goodsActivity)
    RelativeLayout rl;

    @BindView(R.id.add_goodsActivity)
    TextView add;

    @BindView(R.id.rl_gouwuche)
    RelativeLayout rl_gouwuche;

    @BindView(R.id.goodsNum)
    TextView tv_goodsNum;

    private List<BrandActivityEntity.Values.Goods> data;
    private View headView;
    private ViewPager viewPager;
    private View headView2;
    private ListView lv;
    private List<String> imgsUrl = new ArrayList<>();
    private Vp_goodsActivityAdapter vp_goodsActivityAdapter;
    private Lv_goodsActivityAdapter lv_goodsActivityAdapter;
    private Rv_brand_activityAdapter rv_brand_activityAdapter;
    private boolean boo = false;
    private boolean twice;
    private PopupWindow popupWindow;
    private View popView;
    private WindowManager.LayoutParams windowParams;
    private ImageLoader loader;
    private ImageView popImg;
    private TextView tvIntroducePop;
    private TextView blackPop;
    private TextView S_pop;
    private TextView M_pop;
    private TextView L_pop;
    private TextView XL_pop;
    private TextView Jia_pop;
    private TextView Jian_pop;
    private boolean blackFlag;
    private boolean SFlag;
    private boolean MFlag;
    private boolean LFlag;
    private boolean XLFlag;
    private TextView tvNumPop;
    private Drawable selectDrawable;
    private Drawable normalDrawable;
    private StringBuffer stringBuffer;
    private String userId;
    private String goodsId;
    private String shopname;
    private String shopcolor;
    private String shopsize;
    private int shopnum = 1;
    private String shoppice;
    private JSONObject object;
    private String params;
    private Button addCar_pop;
    private JSONObject object1;
    private String params1;
    private SharedPreferences preferences;
    private CarGoodsEntity carGoodsEntity;
    private String where;
    private List<GoodsEntity.Values> goodsEntity_values = new ArrayList<>();
    private RvGoodsAdapter rvGoodsAdapter;
    private BrandActivityEntity.Values.Goods goodsObject;
    private String url = null;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder()
                .appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_goods;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
        setListener();
        setAdapter();
        request();
    }

    private void init() {
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = preferences.getString("userId",null);

        where = getIntent().getStringExtra("where");
        if(where.equals("BrandActivity")) {
            Bundle bundle = getIntent().getExtras();
            goodsObject = (BrandActivityEntity.Values.Goods) bundle.getSerializable("goods_brandActivity");
            data = (List<BrandActivityEntity.Values.Goods>) bundle.getSerializable("data");
            goodsId = goodsObject.getGoods_id();
            shopname = goodsObject.getGoods_name();
            url = goodsObject.getGoods_img_path();
        }else if(where.equals("HomePageFragment")) {
            goodsEntity_values = (List<GoodsEntity.Values>) getIntent().getSerializableExtra("data");
            GoodsEntity.Values goods = (GoodsEntity.Values) getIntent().getSerializableExtra("goods_homepageFragment");
            goodsId = goods.getGoods_id();
            shopname = goods.getGoods_name();
            url = goods.getGoods_img_path();
        }

        shoppice = "798";

        selectDrawable = ContextCompat.getDrawable(this, R.drawable.bg_tv_select_pop);
        normalDrawable = ContextCompat.getDrawable(this, R.drawable.bg_tv_pop);
        loader = new ImageLoaderUtils(this).getImageLoader();
        windowParams = getWindow().getAttributes();
        popView = LayoutInflater.from(this).inflate(R.layout.goods_pop, null);
        popImg = popView.findViewById(R.id.img_pop);
        loader.loadImage(this,ImageConfigImpl.builder()
        .url(Api.IMG_DOMAIN + url)
        .placeholder(R.drawable.share_list_placeholder_s_background)
        .errorPic(R.drawable.tt_default_image_error)
        .imageView(popImg)
        .build());
        tvIntroducePop = popView.findViewById(R.id.tvColor_pop);
        blackPop = popView.findViewById(R.id.black_pop);
        S_pop = popView.findViewById(R.id.S_pop);
        M_pop = popView.findViewById(R.id.M_pop);
        L_pop = popView.findViewById(R.id.L_pop);
        XL_pop = popView.findViewById(R.id.XL_pop);
        Jia_pop = popView.findViewById(R.id.jia);
        Jian_pop = popView.findViewById(R.id.jian);
        tvNumPop = popView.findViewById(R.id.tv_num_pop);
        tvNumPop.setTextColor(0xff000000);
        addCar_pop = popView.findViewById(R.id.addCar_pop);
        popupWindow = new PopupWindow(popView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        headView = LayoutInflater.from(this).inflate(R.layout.head_goods_activity, null);
        viewPager = headView.findViewById(R.id.vp_head_goodsActivity);
        imgsUrl.add("https://pic.baike.soso.com/ugc/baikepic2/0/20170512152539-2114283836.jpg/800");
        imgsUrl.add("https://b279.photo.store.qq.com/psb?/V13z4upL11DU8W/TQfvX9ciNvVpNZxCFxYNIUvzi1GhIAcYO00KevEXD2c!/m/dBcBAAAAAAAAnull&bo=hAMgAwAAAAARB5Q!&rf=photolist&t=5");
        imgsUrl.add("http://t2.hddhhn.com/uploads/tu/201212/00572/1.jpg");
        vp_goodsActivityAdapter = new Vp_goodsActivityAdapter(imgsUrl, new ImageLoaderUtils(this).getImageLoader());
        headView2 = LayoutInflater.from(this).inflate(R.layout.head2_goods_activity, null);
        lv = headView2.findViewById(R.id.lv_head2_goodsActivity);
        lv_goodsActivityAdapter = new Lv_goodsActivityAdapter(imgsUrl, new ImageLoaderUtils(this).getImageLoader());

        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv_brand_activityAdapter = new Rv_brand_activityAdapter(R.layout.item_rv_brand_activity, new ImageLoaderUtils(this).getImageLoader());
        rvGoodsAdapter = new RvGoodsAdapter(R.layout.item_rvgoods, new ImageLoaderUtils(this));
        if(where.equals("BrandActivity")) {
            rv_brand_activityAdapter.addHeaderView(headView);
            rv_brand_activityAdapter.addHeaderView(headView2);
        }else if(where.equals("HomePageFragment")) {
            rvGoodsAdapter.addHeaderView(headView);
            rvGoodsAdapter.addHeaderView(headView2);
        }
    }

    private void setListener() {
        back.setOnClickListener(this);
        rv.addOnScrollListener(new RvScrollListener());
        viewPager.addOnPageChangeListener(this);
        share.setOnClickListener(this);
        add.setOnClickListener(this);
        rl_gouwuche.setOnClickListener(this);
        popupWindow.setOnDismissListener(this);
        blackPop.setOnClickListener(this);
        S_pop.setOnClickListener(this);
        M_pop.setOnClickListener(this);
        L_pop.setOnClickListener(this);
        XL_pop.setOnClickListener(this);
        Jia_pop.setOnClickListener(this);
        Jian_pop.setOnClickListener(this);
        addCar_pop.setOnClickListener(this);
    }

    private void setAdapter() {
        ViewGroup.LayoutParams lp = lv.getLayoutParams();
        lp.height = 2300;
        lv.setLayoutParams(lp);
        viewPager.setAdapter(vp_goodsActivityAdapter);
        lv.setAdapter(lv_goodsActivityAdapter);
        if(where.equals("BrandActivity")) {
            rv.setAdapter(rv_brand_activityAdapter);
            rv_brand_activityAdapter.setNewData(data);
        }else if(where.equals("HomePageFragment")) {
            rv.setAdapter(rvGoodsAdapter);
            rvGoodsAdapter.setNewData(goodsEntity_values);
        }
    }

    private void request() {
        object1 = new JSONObject();
        try {
            object1.put("userid",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params1 = object1.toString();
        mPresenter.requestData(params1,Constant.TYPE_CARGOODS_ACTIVITY);
    }

    @Override
    public void getData(BaseEntity entity, int type) {
        if(type == Constant.TYPE_ADDCAR_GOODSACTIVITY && entity.getMsg().equals("添加成功")) {
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
            request();
        }else if(type == Constant.TYPE_CARGOODS_ACTIVITY) {
            carGoodsEntity = (CarGoodsEntity) entity;
            if(carGoodsEntity.getValues().size() != 0) {
                tv_goodsNum.setVisibility(View.VISIBLE);
                tv_goodsNum.setText(carGoodsEntity.getValues().size() + "");
            }else {
                tv_goodsNum.setVisibility(View.GONE);
                tv_goodsNum.setText("");
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_goodsActivity:
                if(boo) {
                    headView.setVisibility(View.VISIBLE);
                    headView2.setVisibility(View.VISIBLE);
                    rv_brand_activityAdapter.notifyDataSetChanged();
                    boo = false;
                }else
                    finish();
                break;
            case R.id.share_goodsActivity:
                UMImage image = new UMImage(this, "https://pic.baike.soso.com/ugc/baikepic2/0/20170512152539-2114283836.jpg/800");
                UMWeb web = new UMWeb("http://www.baidu.com");
                web.setTitle("麻瓜胖");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("此^(*￣(oo)￣)^乃天下第一骚胖");//描述

                new ShareAction(this)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ)
                        .open();
                break;
            case R.id.add_goodsActivity:
                windowParams.alpha=0.5f;
                getWindow().setAttributes(windowParams);
                popupWindow.showAtLocation(getWindow().getDecorView(),Gravity.BOTTOM,0,0);
                break;
            case R.id.black_pop:
                blackFlag = !blackFlag;
                setColor();
                setText();
                break;
            case R.id.S_pop:
                SFlag = !SFlag;
                MFlag = false;
                LFlag = false;
                XLFlag = false;
                setColor();
                setText();
                break;
            case R.id.M_pop:
                SFlag = false;
                MFlag = !MFlag;
                LFlag = false;
                XLFlag = false;
                setColor();
                setText();
                break;
            case R.id.L_pop:
                SFlag = false;
                MFlag = false;
                LFlag = !LFlag;
                XLFlag = false;
                setColor();
                setText();
                break;
            case R.id.XL_pop:
                SFlag = false;
                MFlag = false;
                LFlag = false;
                XLFlag = !XLFlag;
                setColor();
                setText();
                break;
            case R.id.jia:
                shopnum++;
                tvNumPop.setText(shopnum + "");
                break;
            case R.id.jian:
                shopnum--;
                if(shopnum < 0)
                    shopnum = 0;
                tvNumPop.setText(shopnum + "");
                break;
            case R.id.addCar_pop:
                addCar();
                break;
            case R.id.rl_gouwuche:
                Intent intent = new Intent(this,GoodsCarActivity.class);
                intent.putExtra("recommend", (Serializable) carGoodsEntity.getRecommend_goods());
                intent.putExtra("values", (Serializable) carGoodsEntity.getValues());
                startActivity(intent);
                break;
        }
    }

    private void addCar() {
        boolean isLoginSuccess = preferences.getBoolean("isLoginSuccess", false);
        if(!isLoginSuccess) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!blackFlag) {
            Toast.makeText(this, "清选择颜色", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!SFlag && !MFlag && !LFlag && !XLFlag) {
            Toast.makeText(this, "清选择尺寸", Toast.LENGTH_SHORT).show();
            return;
        }
        if(shopnum == 0) {
            Toast.makeText(this, "请选择购买商品数量", Toast.LENGTH_SHORT).show();
            return;
        }
//        String userName = preferences.getString("userName", null);
//        Log.e("cjx","userId:" + userId);
//        Log.e("cjx","userName:" + userName);
        object = new JSONObject();
        try {
            object.put("userid",userId);
            object.put("goodsid",goodsId);
            object.put("shopname",shopname);
            object.put("shopcolor",shopcolor);
            object.put("shopsize",shopsize);
            object.put("shopnum",shopnum + "");
            object.put("shopprice",shoppice);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_ADDCAR_GOODSACTIVITY);
    }

    private void setColor() {
        if(blackFlag) {
            blackPop.setBackgroundDrawable(selectDrawable);
            blackPop.setTextColor(0xffffffff);
            shopcolor = "黑色";
        }else {
            blackPop.setBackgroundDrawable(normalDrawable);
            blackPop.setTextColor(0xff000000);
        }
        if(SFlag) {
            S_pop.setBackgroundDrawable(selectDrawable);
            S_pop.setTextColor(0xffffffff);
        }else {
            S_pop.setBackgroundDrawable(normalDrawable);
            S_pop.setTextColor(0xff000000);
        }
        if(MFlag) {
            M_pop.setBackgroundDrawable(selectDrawable);
            M_pop.setTextColor(0xffffffff);
        }else {
            M_pop.setBackgroundDrawable(normalDrawable);
            M_pop.setTextColor(0xff000000);
        }
        if(LFlag) {
            L_pop.setBackgroundDrawable(selectDrawable);
            L_pop.setTextColor(0xffffffff);
        }else {
            L_pop.setBackgroundDrawable(normalDrawable);
            L_pop.setTextColor(0xff000000);
        }
        if(XLFlag) {
            XL_pop.setBackgroundDrawable(selectDrawable);
            XL_pop.setTextColor(0xffffffff);
        }else {
            XL_pop.setBackgroundDrawable(normalDrawable);
            XL_pop.setTextColor(0xff000000);
        }
    }

    private void setText() {
        stringBuffer = new StringBuffer();
        stringBuffer.append("已选 : ");
        if(blackFlag)
            stringBuffer.append("黑色、");
        if(SFlag) {
            stringBuffer.append("S");
            shopsize = "S";
        }
        if(MFlag) {
            stringBuffer.append("M");
            shopsize = "M";
        }
        if(LFlag) {
            stringBuffer.append("L");
            shopsize = "L";
        }
        if(XLFlag) {
            stringBuffer.append("XL");
            shopsize = "XL";
        }
        String text = stringBuffer.toString();
        if(!blackFlag && !SFlag && !MFlag && !LFlag && !XLFlag)
            tvIntroducePop.setText("清选择颜色尺码");
        else {
            if(text.substring(text.length() - 1,text.length()).equals("、")) {
                tvIntroducePop.setText(text.substring(0,text.length()-1));
                return;
            }
            tvIntroducePop.setText(text);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        if(i == 3 && twice) {
            headView.setVisibility(View.GONE);
            headView2.setVisibility(View.GONE);
            boo = true;
            viewPager.setCurrentItem(0);
            twice = false;
        }
    }

    @Override
    public void onPageSelected(int i) {
        twice = false;
        if(i == 3)
            twice = true;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDismiss() {
        windowParams.alpha = 1f;
        getWindow().setAttributes(windowParams);
    }

    class RvScrollListener extends RecyclerView.OnScrollListener {
        int scrollHeight;
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            scrollHeight += dy;
            float alpha = (float) scrollHeight / 500;
            if(alpha > 1)
                alpha = 1;
            if(alpha < 0)
                alpha = 0;
            rl.setAlpha(alpha);
        }
    }
}
