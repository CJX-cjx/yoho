package com.umeng.soexample.mvp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.umeng.soexample.R;

public class RequestDialog extends Dialog {

    private View view;
    private ImageView imageView;
    private AnimationDrawable drawable;
    private static RequestDialog requestDialog;

    public RequestDialog(Context context) {
        this(context,0);
    }

    public RequestDialog(Context context, int themeResId) {
        super(context, R.style.Theme_AppCompat_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.request_dialog, null);
        setContentView(view);
        view.setLayoutParams(new FrameLayout.LayoutParams(100,50));
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        imageView = view.findViewById(R.id.imgAnimDialog);
        drawable = (AnimationDrawable) imageView.getBackground();
    }

    @Override
    public void show() {
        super.show();
        drawable.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        drawable.stop();
    }

    public synchronized static RequestDialog getInstance(Context context) {
        if(requestDialog == null) {
            synchronized (RequestDialog.class) {
                if(requestDialog == null)
                    requestDialog = new RequestDialog(context);
            }
        }
        return requestDialog;
    }

}
