package com.zx.wfm.dao;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Shaolin on 2015/10/15.
 */
public class DBManager {
    private static final String TAG = "DBManager";
    private static DBManager instance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        instance = new DBManager(context);
    }

    private DBManager() {
    }

    private DBManager(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                HMROpenHelper helper = new HMROpenHelper(context, "xue_old.db", null);
              //  SQLiteDatabase sqlDB = helper.getWritableDatabase();
   /*             return  sqlDB;
                DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, context.getPackageName(), null);*/
                daoMaster = new DaoMaster(helper.getWritableDatabase());
            }
            daoSession = daoMaster.newSession();
        }
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public void setDaoMaster(DaoMaster daoMaster) {
        this.daoMaster = daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

}
