package com.zx.wfm.dao;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.SaveCallback;
import com.zx.wfm.bean.Moviebean;
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
        for(Televisionbean bean:list){
            Televisionbean oldbean=findLocalTelevisionById(bean.getTelevisionId());
            if(oldbean==null){
                mTelevisionbeanDao.insertInTx(bean);
            }else {
                bean.setObjectId(oldbean.getObjectId());
                mTelevisionbeanDao.update(bean);
            };
        }
    }

    private Televisionbean findLocalTelevisionById(String televisionId) {
        QueryBuilder<Televisionbean> builder=getTelevisionbeanQueryBuilder();
        builder.where(TelevisionbeanDao.Properties.TelevisionId.eq(televisionId));
        return builder.unique();
    }


    /**
     * 获取 指定页数得电影
     * @param num
     * @param page
     * @return
     */
    public   List<Televisionbean> getTelevisionList(int num,int page){
        Log.i(TAG,"num=:"+num+"  page=:"+page);
        QueryBuilder<Televisionbean> builder=getTelevisionbeanQueryBuilder();
        builder.limit(getNum(num));
        builder.offset(getNum(num)*page);
        return builder.list();
    }

    /**
     * 获取 指定页数得电影
     * @param netPage
     * @return
     */

    public   List<Televisionbean> getTelevisionList(String netPage){
        QueryBuilder<Televisionbean> builder=getTelevisionbeanQueryBuilder();
        builder.where(TelevisionbeanDao.Properties.NetPage.eq(netPage));
        return builder.list();
    }
    /**
     * 获取所有电影
     * @return
     */
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

    /**
     * 获取 电影查询对象
     * @return
     */
    private   QueryBuilder<Televisionbean> getTelevisionbeanQueryBuilder(){
        QueryBuilder<Televisionbean> builder=mTelevisionbeanDao.queryBuilder();
        return builder.orderAsc(TelevisionbeanDao.Properties.Time);
    }

    /**
     * 按id 查询 电影
     * @param televisionId
     * @return
     */
    public Televisionbean getTelevisionById(String televisionId) {
        QueryBuilder<Televisionbean> builder=getTelevisionbeanQueryBuilder();
        builder.where(TelevisionbeanDao.Properties.TelevisionId.eq(televisionId));
        return builder.unique();
    }

    /**
     * 保存 剧集
     * @param list
     */
    public void saveMoveBean(List<Moviebean> list) {
        if(list==null){
            return;
        }
        if(list==null){
            return;
        }
        for(Moviebean bean:list){
            Moviebean oldbean=findLocalMovieById(bean.getMovieId());
            if(oldbean==null){
                moviebeanDao.insertInTx(bean);
            }else {
                bean.setObjectId(oldbean.getObjectId());
                moviebeanDao.update(bean);
            };
        }
    }

    /**
     * 按 id 查询电影
     * @param movieId
     * @return
     */
    private Moviebean findLocalMovieById(String movieId) {
        QueryBuilder<Moviebean> builder=moviebeanDao.queryBuilder();
        builder.where(MoviebeanDao.Properties.MovieId.eq(movieId));
        return builder.unique();
    }

    public List<Moviebean> getMovieListByTeleId(String televisionId) {
        if(TextUtils.isEmpty(televisionId)){
            return null;
        }
     return    moviebeanDao.queryBuilder().where(MoviebeanDao.Properties.TelevisionId.eq(televisionId)).list();
    }

    public List<Televisionbean> findLocalTelevision() {
        QueryBuilder<Televisionbean> queryBuilder=getTelevisionbeanQueryBuilder();
        queryBuilder.where(TelevisionbeanDao.Properties.ObjectId.isNull());
        return  queryBuilder.list();
    }
}
