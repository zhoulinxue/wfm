package com.zx.wfm.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {
    public static void showToastLong(final Context context, final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public static void showToastLong(final Context context, final int msgId) {
        if (msgId != 0) {
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msgId, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public static void showToastShort(final Context context, final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    
    public static void showToastShort(final Context context, final int msgId) {
        if (msgId > 0) {
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
 