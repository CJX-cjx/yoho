package com.umeng.soexample.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.CircleImageView;
import com.umeng.soexample.app.utils.DpUtils;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.di.component.DaggerLreComponent;
import com.umeng.soexample.di.module.LreModule;
import com.umeng.soexample.domin.Constant;
import com.umeng.soexample.mvp.contract.LreContract;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.BaseEntity;
import com.umeng.soexample.mvp.model.entity.LoginEntity;
import com.umeng.soexample.mvp.presenter.LrePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class PersonalActivity extends BaseActivity<LrePresenter> implements LreContract.LreIView, View.OnClickListener, PopupWindow.OnDismissListener {
    @BindView(R.id.back_personal)
    ImageView back;

    @BindView(R.id.userIcon_personal)
    CircleImageView userIcon;

    @BindView(R.id.NickName_personal)
    TextView NickName;

    @BindView(R.id.qrCode_personal)
    ImageView qrCode;

    @BindView(R.id.sex_personal)
    TextView Sex;

    @BindView(R.id.bir_personal)
    TextView bri;

    @BindView(R.id.height_personal)
    TextView height;

    @BindView(R.id.weight_personal)
    TextView weight;

    @BindView(R.id.updateUserInfo)
    TextView update;

    private JSONObject object;
    private SharedPreferences sharedPreferences;
    private String userId;
    private String params;
    private LoginEntity.ValuesBean valuesBean;
    private ImageLoader loader;
    private SimpleDateFormat simpleDateFormat;
    private String updateParams;
    private PopupWindow popupWindow;
    private View popView;
    private View popCancelView;
    private PopupWindow popCancelWindow;
    private WindowManager.LayoutParams WindowParams;
    private TextView camera;
    private TextView pictures;
    private TextView cancel;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLreComponent.builder().appComponent(appComponent)
                .lreModule(new LreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_personal;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        loader = new ImageLoaderUtils(this).getImageLoader();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        init();
        setListener();
        request();
    }

    private void request() {
        object = new JSONObject();
        try {
            object.put("userid",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = object.toString();
        mPresenter.requestData(params,Constant.TYPE_PERSONAL_ACTIVITY);
    }

    private void init() {
        WindowParams = getWindow().getAttributes();
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);
        popView = LayoutInflater.from(this).inflate(R.layout.pop_presonal, null);
        camera = popView.findViewById(R.id.camera);
        pictures = popView.findViewById(R.id.pictures);
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, DpUtils.dip2px(this,80));
        popupWindow.setContentView(popView);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        popCancelView = LayoutInflater.from(this).inflate(R.layout.pop_cancel_personal, null);
        cancel = popCancelView.findViewById(R.id.cancel_personal);
        popCancelWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT,DpUtils.dip2px(this,40));
        popCancelWindow.setContentView(popCancelView);
        popCancelWindow.setBackgroundDrawable(new BitmapDrawable());
        popCancelWindow.setOutsideTouchable(true);
        popCancelWindow.setTouchable(true);

    }

    private void setListener() {
        back.setOnClickListener(this);
        update.setOnClickListener(this);
        userIcon.setOnClickListener(this);
        popupWindow.setOnDismissListener(this);
        popCancelWindow.setOnDismissListener(this);
        camera.setOnClickListener(this);
        pictures.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void getData(BaseEntity entity, int type) {
        if(type == Constant.TYPE_PERSONAL_ACTIVITY ) {
            LoginEntity loginEntity = (LoginEntity) entity;
            valuesBean = loginEntity.getValues().get(0);
            loader.loadImage(this,ImageConfigImpl.builder()
            .url(Api.IMG_DOMAIN + valuesBean.getUser_head())
            .imageView(userIcon)
            .build());
            NickName.setText(valuesBean.getNick_name());
            loader.loadImage(this,ImageConfigImpl.builder()
            .url(Api.IMG_DOMAIN + "/qr_img/wzh.png")
            .errorPic(R.drawable.tt_default_image_error)
            .imageView(qrCode)
            .build());
            Sex.setText(valuesBean.getUser_sex());
            long time = Integer.parseInt(valuesBean.getUser_birthday());
            bri.setText(simpleDateFormat.format(new Date(time)));
            height.setText(valuesBean.getUser_height());
            weight.setText(valuesBean.getUser_weight());
        }else if(type == Constant.TYPE_UPDATEPERSONAL_ACTIVITY) {
            if(entity.getMsg().equals("修改成功")) {
                Toast.makeText(this, "修改完毕", Toast.LENGTH_SHORT).show();
                request();
            }
        }else if(type == Constant.TYPE_UPLOADUSERHEAD_ACTIVITY) {
            Toast.makeText(this, entity.getMsg(), Toast.LENGTH_SHORT).show();
            request();
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
            case R.id.back_personal:
                finish();
                break;
            case R.id.updateUserInfo:
                update();
                break;
            case R.id.userIcon_personal:
                WindowParams.alpha = 0.4f;
                getWindow().setAttributes(WindowParams);
                popCancelWindow.showAtLocation(getWindow().getDecorView(),Gravity.BOTTOM,0,30);
                popupWindow.showAtLocation(getWindow().getDecorView(),Gravity.BOTTOM,0,180);
                break;
            case R.id.camera:
                disPopWindow();
                break;
            case R.id.pictures:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1001);
                disPopWindow();
                break;
            case R.id.cancel_personal:
                disPopWindow();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1001) {
            Uri uri = data.getData();
            String path = getDataColumn(this, uri, null, null);
            Log.e("cjx","path：" + path);
            File file = new File(path);
            mPresenter.upLoadUserHead(userId,file,Constant.TYPE_UPLOADUSERHEAD_ACTIVITY);
            if(file.exists())
                Log.e("cjx","存在");
            else
                Log.e("cjx","不存在");
        }
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;
        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    private void disPopWindow() {
        if(popupWindow.isShowing())
            popupWindow.dismiss();
        if(popCancelWindow.isShowing())
            popCancelWindow.dismiss();
    }

    private void update() {
        JSONObject object = new JSONObject();
        try {
            object.put("userid",userId);
            object.put("nickname","雲霄");
            object.put("usersex","男");
            object.put("userbirthday","774589789");
            object.put("userheight","182cm");
            object.put("userweight","75kg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateParams = object.toString();
        mPresenter.requestData(updateParams,Constant.TYPE_UPDATEPERSONAL_ACTIVITY);
    }

    @Override
    public void onDismiss() {
        WindowParams.alpha = 1f;
        getWindow().setAttributes(WindowParams);
    }
}
