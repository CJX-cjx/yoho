package com.umeng.soexample.mvp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.umeng.soexample.mvc.entity.Ev_goodsActivityEntity;

import java.util.ArrayList;
import java.util.List;

public class Gv_ev_goodsActivityAdapter extends BaseAdapter {
    private List<Ev_goodsActivityEntity.Children> children = new ArrayList<>();

    public void refresh(List<Ev_goodsActivityEntity.Children> children) {
        this.children.clear();
        this.children.addAll(children);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return children.size();
    }

    @Override
    public Object getItem(int position) {
        return children.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gv_ev_goods_activity,null);
        }
        textView = convertView.findViewById(R.id.tv_gv_ev_goodsActivity);
        textView.setText(children.get(position).getContent());
        return convertView;
    }
}
