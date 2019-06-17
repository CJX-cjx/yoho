package com.umeng.soexample.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.umeng.soexample.R;
import com.umeng.soexample.app.utils.ImageLoaderUtils;
import com.umeng.soexample.mvp.model.api.Api;
import com.umeng.soexample.mvp.model.entity.ShoesDealEntity;
import com.umeng.soexample.mvp.ui.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class RvMyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageLoader loader;
    private View view;
    private Context context;
    private List<ShoesDealEntity.Values> list = new ArrayList<>();

    public void refresh(List<ShoesDealEntity.Values> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        loader = new ImageLoaderUtils(viewGroup.getContext()).getImageLoader();
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_my, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
           MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
                myViewHolder.goodsName.setText(list.get(i).getGoods_name());
                myViewHolder.price.setText("¥ " + list.get(i).getGoods_original_price());
                loader.loadImage(context,ImageConfigImpl.builder()
                        .url(Api.IMG_DOMAIN + list.get(i).getGoods_img_path())
                        .placeholder(R.drawable.share_list_placeholder_s_background)
                        .errorPic(R.drawable.tt_default_image_error)
                        .imageView(myViewHolder.img)
                        .build());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView goodsName;
        TextView price;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsName = itemView.findViewById(R.id.tv_goodsName_my);
            price = itemView.findViewById(R.id.tvPrice_my);
            img = itemView.findViewById(R.id.img_rv_my);
        }
    }
}
