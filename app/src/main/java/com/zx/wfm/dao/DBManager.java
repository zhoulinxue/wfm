package com.zx.wfm.dao;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.LogUtil;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.utils.Constants;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zx on 2015/10/15.
 */
public class DBManager {
    private static final String TAG = DBManager.class.getSimpleName();
    private static DBManager instance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    static TelevisionbeanDao mTelevisionbeanDao;
    static  BaseUserDao mBaseUserDao;
    static  MoviebeanDao moviebeanDao;
    static MovieCourseDao movieCourseDao;
    static IDCardDao idCardDao;


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
                HMROpenHelper helper = new HMROpenHelper(context, context.getApplicationInfo().getClass().getSimpleName()+".db", null);
                daoMaster = new DaoMaster(helper.getWritableDatabase());
            }
            daoSession = daoMaster.newSession();
            Log.e(TAG,"创建表"+"daoSession:"+(daoSession==null));
            mTelevisionbeanDao=daoSession.getTelevisionbeanDao();
            mBaseUserDao=daoSession.getBaseUserDao();
            moviebeanDao=daoSession.getMoviebeanDao();
            movieCourseDao=daoSession.getMovieCourseDao();
            idCardDao=daoSession.getIDCardDao();
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

    /**
     * 保存 电视节目
     * @param list
     */

    public  void saveTelevisions(List<Televisionbean> list){
        if(list==null){
            return;
        }
        mTelevisionbeanDao.insertOrReplaceInTx(list);
    }
    public   List<Televisionbean> getTelevisionList(int num,int page){
        
        QueryBuilder<Televisionbean> builder=getTelevisionbeanQueryBuilder();
        builder.limit(getNum(num));
        builder.offset(getNum(num)*page);
        return builder.list();
    }

    public   List<Televisionbean> getAllTelevisionList(){
        QueryBuilder<Televisionbean> builder=getTelevisionbeanQueryBuilder();
        return builder.list();
    }

    private  int getNum(int num) {
        if(num==0){
            num= Constants.PAGE_MIN_NUM;
        }
        return  num;
    }

    private   QueryBuilder<Televisionbean> getTelevisionbeanQueryBuilder(){
        QueryBuilder<Televisionbean> builder=mTelevisionbeanDao.queryBuilder();
        return builder.orderDesc(TelevisionbeanDao.Properties.Time);
    }

}
