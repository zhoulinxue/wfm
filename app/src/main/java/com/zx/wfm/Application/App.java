package com.zx.wfm.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import com.aspsine.swipetoloadlayout.BuildConfig;


/**
 * Created by ${zhouxue} on 16/10/11 02: 14.
 * QQ:515278502
 */
public class App extends Application {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static App wfmApplication;
    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        wfmApplication=this;
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor=preferences.edit();
        setStrictMode();
        sContext = getApplicationContext();
    }
    private void setStrictMode() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.enableDefaults();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
