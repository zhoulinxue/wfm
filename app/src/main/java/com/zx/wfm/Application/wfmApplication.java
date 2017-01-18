package com.zx.wfm.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.zx.wfm.bean.AVErrorbean;
import com.zx.wfm.bean.Userbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.NetWorkUtils;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.ThreadUtil;


/**
 * Created by ${zhouxue} on 16/10/11 02: 14.
 * QQ:515278502
 */
public class WFMApplication extends Application {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static WFMApplication wfmApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        wfmApplication=this;
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor=preferences.edit();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
