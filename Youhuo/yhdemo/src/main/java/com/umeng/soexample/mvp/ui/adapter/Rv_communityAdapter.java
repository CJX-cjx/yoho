package com.umeng.soexample.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.CommunityEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Rv_communityAdapter extends BaseQuickAdapter<CommunityEntity.Values,BaseViewHolder> {
    private boolean isrepeat = true;
    private SimpleDateFormat format = new SimpleDateFormat("MM.dd.yy");
    private ImageLoader loader;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;
    private ImageView img8;
    private ImageView img9;
    private TextView line1;
    private TextView line2;
    private TextView line3;
    private TextView line4;
    private TextView line5;
    private TextView line6;
    private TextView tvLine1;
    private TextView tvLine2;
    private View bottom;

    private ImageView userIcon;
    private TextView tvZhiding;
    public Rv_communityAdapter(int layoutResId,ImageLoader loader) {
        super(layoutResId);
        this.loader = loader;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommunityEntity.Values item) {
        userIcon = helper.getView(R.id.userIcon_rv_community);
        img1 = helper.getView(R.id.img1_rv_community);
        img2 = helper.getView(R.id.img2_rv_community);
        img3 = helper.getView(R.id.img3_rv_community);
        img4 = helper.getView(R.id.img4_rv_community);
        img5 = helper.getView(R.id.img5_rv_community);
        img6 = helper.getView(R.id.img6_rv_community);
        img7 = helper.getView(R.id.img7_rv_community);
        img8 = helper.getView(R.id.img8_rv_community);
        img9 = helper.getView(R.id.img9_rv_community);
        line1 = helper.getView(R.id.line1_rv_community);
        line2 = helper.getView(R.id.line2_rv_community);
        line3 = helper.getView(R.id.line3_rv_community);
        line4 = helper.getView(R.id.line4_rv_community);
        line5 = helper.getView(R.id.line5_rv_community);
        line6 = helper.getView(R.id.line6_rv_community);
        tvLine1 = helper.getView(R.id.tvLine1_rv_community);
        tvLine2 = helper.getView(R.id.tvLine2_rv_community);
        tvZhiding = helper.getView(R.id.zhiding_rv_community);
        bottom = helper.getView(R.id.bottom_rv_community);

        loader.loadImage(mContext,ImageConfigImpl.builder()
                                                .url("https://b279.photo.store.qq.com/psb?/V13z4upL11DU8W/TQfvX9ciNvVpNZxCFxYNIUvzi1GhIAcYO00KevEXD2c!/m/dBcBAAAAAAAAnull&bo=hAMgAwAAAAARB5Q!&rf=photolist&t=5")
                                                .placeholder(R.drawable.share_list_placeholder_s_background)
                                                .errorPic(R.drawable.tt_default_image_error)
                                                .imageView(userIcon)
                                                .build());
        helper.setText(R.id.userName_rv_community,"麻瓜胖");
        Long time = Long.valueOf(item.getCommunity_time() + "000");
        helper.setText(R.id.time_rv_community,format.format(new Date(time)));
        if(item.getHot_flag().equals("1"))
            tvZhiding.setVisibility(View.VISIBLE);
        else
            tvZhiding.setVisibility(View.GONE);
        helper.setText(R.id.value_rv_community,item.getCommunity_values());
        helper.setText(R.id.title_rv_community,item.getTitle());
        helper.setText(R.id.tvTag_rv_community,item.getTag());
        helper.setText(R.id.tvMsg_rv_community,item.getFabulous());
        helper.setText(R.id.tvZan_rv_community,item.getCollection());
        setImgs(helper,item);
        helper.itemView.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
        helper.itemView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        if(isrepeat) {
            helper.itemView.setBackgroundColor(0xffffffff);
            bottom.setBackgroundColor(0xffffffff);
            line1.setBackgroundColor(0xffffffff);
            line2.setBackgroundColor(0xffffffff);
            line3.setBackgroundColor(0xffffffff);
            line4.setBackgroundColor(0xffffffff);
            line5.setBackgroundColor(0xffffffff);
            line6.setBackgroundColor(0xffffffff);
            tvLine1.setBackgroundColor(0xffffffff);
            tvLine2.setBackgroundColor(0xffffffff);
        }
        else {
            helper.itemView.setBackgroundColor(0xffefefef);
            bottom.setBackgroundColor(0xffefefef);
            line1.setBackgroundColor(0xffefefef);
            line2.setBackgroundColor(0xffefefef);
            line3.setBackgroundColor(0xffefefef);
            line4.setBackgroundColor(0xffefefef);
            line5.setBackgroundColor(0xffefefef);
            line6.setBackgroundColor(0xffefefef);
            tvLine1.setBackgroundColor(0xffefefef);
            tvLine2.setBackgroundColor(0xffefefef);
        }
        isrepeat = !isrepeat;
    }

    private void setImgs(BaseViewHolder helper, CommunityEntity.Values item) {
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
        img6.setVisibility(View.GONE);
        img7.setVisibility(View.GONE);
        img8.setVisibility(View.GONE);
        img9.setVisibility(View.GONE);
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        line3.setVisibility(View.GONE);
        line4.setVisibility(View.GONE);
        line5.setVisibility(View.GONE);
        line6.setVisibility(View.GONE);
        tvLine1.setVisibility(View.GONE);
        tvLine2.setVisibility(View.GONE);
        switch (item.getImgs().size()) {
            case 1 :
                loadImg(img1,item.getImgs().get(0));
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                break;
            case 2 :
                loadImg(img1,item.getImgs().get(0));
                loadImg(img2,item.getImgs().get(1));
                line1.setVisibility(View.VISIBLE);
                break;
            case 3 :
                loadImg(img1,item.getImgs().get(0));
                line1.setVisibility(View.VISIBLE);
                loadImg(img2,item.getImgs().get(1));
                line2.setVisibility(View.VISIBLE);
                loadImg(img3,item.getImgs().get(2));
                break;
            case 4 :
                loadImg(img1,item.getImgs().get(0));
                line1.setVisibility(View.VISIBLE);
                loadImg(img2,item.getImgs().get(1));
                line2.setVisibility(View.VISIBLE);
                tvLine1.setVisibility(View.VISIBLE);
                loadImg(img4,item.getImgs().get(2));
                line3.setVisibility(View.VISIBLE);
                loadImg(img5,item.getImgs().get(3));
                line4.setVisibility(View.VISIBLE);
                break;
            case 5 :
                loadImg(img1,item.getImgs().get(0));
                line1.setVisibility(View.VISIBLE);
                loadImg(img2,item.getImgs().get(1));
                tvLine1.setVisibility(View.VISIBLE);
                loadImg(img4,item.getImgs().get(2));
                line3.setVisibility(View.VISIBLE);
                loadImg(img5,item.getImgs().get(3));
                line4.setVisibility(View.VISIBLE);
                loadImg(img6,item.getImgs().get(4));
                break;
            case 6 :
                loadImg(img1,item.getImgs().get(0));
                line1.setVisibility(View.VISIBLE);
                loadImg(img2,item.getImgs().get(1));
                line2.setVisibility(View.VISIBLE);
                loadImg(img3,item.getImgs().get(2));
                tvLine1.setVisibility(View.VISIBLE);
                loadImg(img4,item.getImgs().get(3));
                line3.setVisibility(View.VISIBLE);
                loadImg(img5,item.getImgs().get(4));
                line4.setVisibility(View.VISIBLE);
                loadImg(img6,item.getImgs().get(5));
                break;
            case 7 :
                loadImg(img1,item.getImgs().get(0));
                line1.setVisibility(View.VISIBLE);
                loadImg(img2,item.getImgs().get(1));
                line2.setVisibility(View.VISIBLE);
                loadImg(img3,item.getImgs().get(2));
                tvLine1.setVisibility(View.VISIBLE);
                loadImg(img4,item.getImgs().get(3));
                line3.setVisibility(View.VISIBLE);
                loadImg(img5,item.getImgs().get(4));
                line4.setVisibility(View.VISIBLE);
                loadImg(img6,item.getImgs().get(5));
                tvLine2.setVisibility(View.VISIBLE);
                loadImg(img7,item.getImgs().get(6));
                img8.setVisibility(View.INVISIBLE);
                img9.setVisibility(View.INVISIBLE);
                break;
            case 8 :
                loadImg(img1,item.getImgs().get(0));
                line1.setVisibility(View.VISIBLE);
                loadImg(img2,item.getImgs().get(1));
                line2.setVisibility(View.VISIBLE);
                loadImg(img3,item.getImgs().get(2));
                tvLine1.setVisibility(View.VISIBLE);
                loadImg(img4,item.getImgs().get(3));
                line3.setVisibility(View.VISIBLE);
                loadImg(img5,item.getImgs().get(4));
                line4.setVisibility(View.VISIBLE);
                loadImg(img6,item.getImgs().get(5));
                tvLine2.setVisibility(View.VISIBLE);
                loadImg(img7,item.getImgs().get(6));
                line5.setVisibility(View.VISIBLE);
                loadImg(img8,item.getImgs().get(7));
                img9.setVisibility(View.INVISIBLE);
                break;
            case 9 :
                loadImg(img1,item.getImgs().get(0));
                line1.setVisibility(View.VISIBLE);
                loadImg(img2,item.getImgs().get(1));
                line2.setVisibility(View.VISIBLE);
                loadImg(img3,item.getImgs().get(2));
                tvLine1.setVisibility(View.VISIBLE);
                loadImg(img4,item.getImgs().get(3));
                line3.setVisibility(View.VISIBLE);
                loadImg(img5,item.getImgs().get(4));
                line4.setVisibility(View.VISIBLE);
                loadImg(img6,item.getImgs().get(5));
                tvLine2.setVisibility(View.VISIBLE);
                loadImg(img7,item.getImgs().get(6));
                line5.setVisibility(View.VISIBLE);
                loadImg(img8,item.getImgs().get(7));
                line6.setVisibility(View.VISIBLE);
                loadImg(img9,item.getImgs().get(8));
                break;
        }
    }

    private void loadImg(ImageView imageView, CommunityEntity.Values.Imgs imgs) {
        imageView.setVisibility(View.VISIBLE);
        loader.loadImage(mContext,ImageConfigImpl.builder()
        .url(Api.IMG_DOMAIN + imgs.getImg_path() + ".jpg")
        .placeholder(R.drawable.share_list_placeholder_s_background)
        .errorPic(R.drawable.tt_default_image_error)
        .imageView(imageView)
        .build());
    }
}
