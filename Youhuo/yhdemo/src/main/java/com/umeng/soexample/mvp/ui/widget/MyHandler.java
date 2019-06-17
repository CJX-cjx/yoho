package com.umeng.soexample.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.soexample.mvp.pay.demo.PayResult;

import java.util.Map;

@SuppressLint("HandlerLeak")
public class MyHandler extends Handler {

    private static MyHandler myHandler;
    private static Context context;
    public static final int SDK_PAY_FLAG = 1;

    public static synchronized MyHandler getInstance(Context context) {
        if(myHandler == null) {
            synchronized (MyHandler.class) {
                if(myHandler == null)
                    myHandler = new MyHandler();
            }
        }
        MyHandler.context = context;
        return myHandler;
    }

    @SuppressWarnings("unused")
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息，将此信息发到app服务器核实账户是否收到钱
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }
    }

}
