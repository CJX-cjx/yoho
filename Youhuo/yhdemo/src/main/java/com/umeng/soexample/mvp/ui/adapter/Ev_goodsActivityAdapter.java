package com.umeng.soexample.mvp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.umeng.soexample.mvc.entity.Ev_goodsActivityEntity;

import java.util.ArrayList;
import java.util.List;

public class Ev_goodsActivityAdapter extends BaseExpandableListAdapter {
    private List<Ev_goodsActivityEntity> data = new ArrayList<>();

    public void refresh(List<Ev_goodsActivityEntity> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getChild().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ev_goods_activity,parent,false);
        }
        TextView title = convertView.findViewById(R.id.title_ev_goodsActivity);
        TextView showTitles = convertView.findViewById(R.id.showTitles);
        ImageView img  = convertView.findViewById(R.id.img_itemEv_goodsActivity);
        title.setText(data.get(groupPosition).getTitle());
        showTitles.setText("");
        if(isExpanded)
            img.setImageResource(R.drawable.arrow_gray_up);
        else
            img.setImageResource(R.drawable.arrow_gray_down);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Gv_ev_goodsActivityAdapter adapter = new Gv_ev_goodsActivityAdapter();
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_ev_goods_activity,parent,false);
        }
        GridView gridView = convertView.findViewById(R.id.gv_child);
        convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,296));
        gridView.setAdapter(adapter);
        adapter.refresh(data.get(groupPosition).getChild());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
