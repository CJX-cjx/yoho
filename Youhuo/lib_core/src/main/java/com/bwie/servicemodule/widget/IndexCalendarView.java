package com.bwie.servicemodule.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.bwie.servicemodule.R;
import com.bwie.servicemodule.calendarview.Calendar;
import com.bwie.servicemodule.calendarview.CalendarView;
import com.bwie.servicemodule.controler.UtilsControler;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class IndexCalendarView extends LinearLayout implements CalendarView.OnCalendarSelectListener{

    private View view;
    private CalendarView mCalendarView;
    private CalendarListener listener;

    public IndexCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setListener(CalendarListener listener) {
        this.listener = listener;
    }

    private void init(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_calendar,null);
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        addView(view);
        mCalendarView = view.findViewById(R.id.calendarView);
        mCalendarView.setOnCalendarSelectListener(this);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        if (isClick&&listener!=null)
            listener.select(calendar);

    }

    public interface CalendarListener{
        void select(Calendar calendar);
    }

}
