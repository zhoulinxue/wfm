package com.zx.wfm.Application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;
import com.wilddog.wilddogauth.model.WilddogUser;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;
import com.zx.wfm.utils.ApiManager;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.NetWorkUtils;
import com.zx.wfm.utils.PhoneUtils;


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
        ApiManager.init(this);
        WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://zx-app.wilddogio.com").build();
        WilddogApp.initializeApp(this, options);
        SyncReference ref = WilddogSync.getInstance().getReference();
        ref.setValue("name", "hello word");
        ref.push();
        setnetWorkType();
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
}
