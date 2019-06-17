package com.umeng.soexample.mvp.ui.adapter;

import android.widget.ImageView;

import com.umeng.soexample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.LookLookListEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RvLookLookListAdapter extends BaseQuickAdapter<LookLookListEntity.Values,BaseViewHolder> {
    private ImageLoader loader;
    private SimpleDateFormat dateFormat;
    public RvLookLookListAdapter(int layoutResId,ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
        dateFormat = new SimpleDateFormat("MM月 dd日 HH:mm");
    }

    @Override
    protected void convert(BaseViewHolder helper, LookLookListEntity.Values item) {
        helper.addOnClickListener(R.id.imgFavorite_looklookVp);
        helper.addOnClickListener(R.id.imgShare_looklookVp);
        helper.setText(R.id.tvNameLookLook,item.getSee_edt_name());
        helper.setText(R.id.tvSignLookLook,"#" + item.getSee_edt_tag());
        helper.setText(R.id.tvCenterLookLook,item.getSee_edt_values());
        helper.setText(R.id.tvTitle_looklookVp,item.getSee_news_title());
        helper.setText(R.id.tvSmallTitle_looklookVp,item.getSee_news_values());
        Long time = Long.valueOf(item.getSee_news_time() + "000");
        String format = dateFormat.format(new Date(time));
        helper.setText(R.id.tv_time_looklookVp,format);
        helper.setText(R.id.tv_seeTime_looklookVp,item.getSee_news_num());

        ImageView imgTab = helper.getView(R.id.iconLookLook);
        loader.loadImage(helper.itemView.getContext(),ImageConfigImpl.builder()
        .url(Api.IMG_DOMAIN + item.getSee_edt_icon())
        .placeholder(R.drawable.share_list_placeholder_s_background)
        .errorPic(R.drawable.tt_default_image_error)
        .imageView(imgTab)
        .build());

        ImageView imgNews = helper.getView(R.id.img_rv_look_look_vp);
        loader.loadImage(helper.itemView.getContext(),ImageConfigImpl.builder()
                .url(Api.IMG_DOMAIN + item.getSee_news_img())
                .placeholder(R.drawable.share_list_placeholder_s_background)
                .errorPic(R.drawable.tt_default_image_error)
                .imageView(imgNews)
                .build());
    }
}
