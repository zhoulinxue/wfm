package com.zx.wfm.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.PowerManager;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import java.text.DecimalFormat;

/**
 * Created by zhouxue on 2016/6/21.
 */
public class PhoneUtils {

    /**
     * 获取屏幕分辨率 长
     *
     * @param context
     * @return
     */
    public static int getScreenHight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = context.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕分辨率 宽
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = context.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        return dm.widthPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机信息
     *
     * @param context
     */
    public static String getImeiInfo(Context context) {
        String imei = getPhoneId(context);
        return imei;
    }

    public static String getPhoneId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        return id;
    }


    /**
     * 隐藏键盘
     *
     * @param context
     * @return
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    /**
     * 格式化数据
     *
     * @param data
     * @return
     */
    public static String formatTrafficByte(long data) {
        DecimalFormat format = new DecimalFormat("##.##");
        if (data < 1024) {
            return data + "bytes";
        } else if (data < 1024 * 1024) {
            return format.format(data / 1024f) + "KB";
        } else if (data < 1024 * 1024 * 1024) {
            return format.format(data / 1024f / 1024f) + "MB";
        } else if (data < 1024 * 1024 * 1024 * 1024) {
            return format.format(data / 1024f / 1024f / 1024f) + "GB";
        } else {
            return "超出统计范围";
        }
    }


    public static void weakUp(Context context) {
        // TODO Auto-generated method stub
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        mWakeLock.acquire();

        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        // 解锁
        kl.disableKeyguard();
    }

    public static int OpenSpeaker(Context context) {
        int currVolume = 0;
        try {
            AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.STREAM_RING);
            currVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);

            if (!audioManager.isSpeakerphoneOn()) {
                audioManager.setSpeakerphoneOn(true);

                audioManager.setStreamVolume(AudioManager.STREAM_RING,
                        audioManager
                                .getStreamMaxVolume(AudioManager.STREAM_RING),
                        AudioManager.STREAM_RING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currVolume;
    }

    public static void CloseSpeaker(Context mContext) {

        try {
            AudioManager audioManager = (AudioManager) mContext
                    .getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.STREAM_RING);
            if (audioManager != null) {
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);

                    audioManager
                            .setStreamVolume(
                                    AudioManager.STREAM_RING,
                                    audioManager
                                            .getStreamMaxVolume(AudioManager.STREAM_RING),
                                    AudioManager.STREAM_RING);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
