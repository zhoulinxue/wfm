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
        AVOSCloud.initialize(WFMApplication.this,"2zBSbem5VbsxPa1dou5nH8EQ-gzGzoHsz","ra2GN4GM8uypJQ8khu7H2wqg");
        initData();
    }

    private void initData() {
        ThreadUtil.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                initBugly();
                DBManager.init(WFMApplication.this);
                setnetWorkType();
                logintoServer();
            }
        });
    }

    private void initBugly() {
        Beta.initDelay = 10 * 1000;
        Bugly.init(getApplicationContext(), "7a23adc558", false);
    }

    private void registerUser() {
        Userbean user=new Userbean();
        user.getAvUser().setUsername(PhoneUtils.getImeiInfo(this));
        user.getAvUser().put("deviceId",PhoneUtils.getImeiInfo(this));
        user.getAvUser().setPassword(Constants.DEFAULT_PASS_WORD);
        user.getAvUser().signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){

                }
            }
        });
    }

    /**
     * 当前网络类型
     */
    private void setnetWorkType() {
        if(NetWorkUtils.getNetworkType(this)!=NetWorkUtils.NETTYPE_WIFI&&NetWorkUtils.isNetworkConnected(this)){
            editor.putBoolean(Constants.MOBLE_TRAFFIC,true);
        }else {
            editor.putBoolean(Constants.MOBLE_TRAFFIC,false);
        }
        editor.commit();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void logintoServer() {
        AVUser.logInInBackground(PhoneUtils.getImeiInfo(this), Constants.DEFAULT_PASS_WORD, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    Toast.makeText(wfmApplication,"欢迎小主",Toast.LENGTH_SHORT).show();
                } else {
                    AVErrorbean errorBean= JSONObject.parseObject(e.getMessage(), AVErrorbean.class);
                    if(errorBean.getCode()==211||"Could not find user".equals(errorBean.getError())){
                        registerUser();
                    }
                }
            }
        });
    }
}
