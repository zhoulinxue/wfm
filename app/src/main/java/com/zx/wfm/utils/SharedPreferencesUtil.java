package com.zx.wfm.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zx.wfm.Application.App;

import java.util.UUID;

/**
 * Created by zhouxue on 2017/2/28.
 * QQ 515278502
 */

public class SharedPreferencesUtil {
    private static  SharedPreferencesUtil instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtil() {
        preferences= PreferenceManager.getDefaultSharedPreferences(App.wfmApplication.getApplicationContext());
        editor=preferences.edit();
    }

    public static SharedPreferencesUtil getInstance() {
        if(instance==null){
            instance=new SharedPreferencesUtil();
        }
        return instance;
    }

    public String getUUID() {
        return preferences.getString(Constants.User.USER_UUID,getDefaultUUid());
    }

    public String getDefaultUUid() {
        String uuid= UUID.randomUUID().toString();
        editor.putString(Constants.User.USER_UUID,uuid);
        editor.commit();
        return uuid;
    }
}
