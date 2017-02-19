package com.zx.wfm.service.Impl;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zx.wfm.Application.App;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.service.NetWorkServer;
import com.zx.wfm.service.ParseUrlServer;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.ThreadUtil;
import com.zx.wfm.utils.UKutils;

import java.util.List;

/**
 *   作者：周学 on 2017/2/18 19:30
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public class NetWorkServerImpl implements NetWorkServer ,ParseUrlServer{
    private ParseUrlServer server;

    public NetWorkServerImpl() {
        server=this;
    }

    public NetWorkServerImpl(ParseUrlServer server) {
        this.server = server;
    }

    @Override
    public void getAllTeleVisionDataFromNet() {
        AVQuery<Televisionbean> query = AVObject.getQuery(Televisionbean.class);
        query.whereExists("TelevisionId");
        query.findInBackground(new FindCallback<Televisionbean>() {
            @Override
            public void done(List<Televisionbean> list, AVException e) {
                Log.i("获取同步数据","电视"+(list==null));
                if(list!=null) {
                    Log.i("获取同步数据","电视"+list.size());
                    DBManager.getInstance().saveTelevisions(list);
                }
            }
        });
    }

    @Override
    public void getAllMovieDataFromNet() {
        AVQuery<Moviebean> query = AVObject.getQuery(Moviebean.class);
        query.whereExists("movieId");
        query.findInBackground(new FindCallback<Moviebean>() {
            @Override
            public void done(List<Moviebean> list, AVException e) {
                Log.i("获取同步数据","剧集"+(list==null));
                if(list!=null) {
                    Log.i("获取同步数据","剧集"+list.size());
                    DBManager.getInstance().saveMoveBean(list);
                }
            }
        });
    }

    @Override
    public void getTeleVisionDataFromNet(final String url) {
        AVQuery<Televisionbean> query = AVObject.getQuery(Televisionbean.class);
        query.whereEqualTo("netPage", url);
        query.findInBackground(new FindCallback<Televisionbean>() {
            @Override
            public void done(List<Televisionbean> results, AVException e) {
                Log.i("从野狗获取数据",(results==null)+"");
                if(results!=null&&results.size()!=0) {
                    DBManager.getInstance().saveTelevisions(results);
                    server.OnGetTelevisionFromLeadCload(results,url);
                }else {
                    getTeleVisionDataFromYK(url);
                }
            }
        });
    }

    @Override
    public void getTeleVisionDataFromYK(final String televisionUrl) {
        ThreadUtil.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Televisionbean> list= UKutils.getVideoInfo(televisionUrl, new FindCallback<Televisionbean>() {
                        @Override
                        public void done(List<Televisionbean> list, AVException e) {
                            DBManager.getInstance().saveTelevisions(list);
                        }
                    });
                    DBManager.getInstance().saveTelevisions(list);
                    server.onParsrTelevisionUrlCallback(list,televisionUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    server.onParseUrlError(e);
                }
            }
        });
    }

    @Override
    public void getMovieDataFromNet(final Televisionbean url) {
        AVQuery<Moviebean> query = AVObject.getQuery(Moviebean.class);
        query.whereEqualTo("TelevisionId", url);
        query.findInBackground(new FindCallback<Moviebean>() {
            @Override
            public void done(List<Moviebean> results, AVException e) {
                Log.i("从野狗获取数据Moviebean",(results==null)+"");
                if(results!=null&&results.size()!=0) {
                    DBManager.getInstance().saveMoveBean(results);
                    server.OnGetMovieFromLeadCload(results,url.getAddressUrl());
                }else {
                    getMovieDataFromYK(url);
                }
            }
        });
    }

    @Override
    public void getMovieDataFromYK(final Televisionbean televisionbean) {
        ThreadUtil.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                List<Moviebean> list = UKutils.getVideoList(televisionbean);
                server.onParsrMovieUrlCallback(list,televisionbean.getAddressUrl());
            }
        });
    }

    @Override
    public void OnGetTelevisionFromLeadCload(List<Televisionbean> list, String url) {

    }

    @Override
    public void OnGetMovieFromLeadCload(List<Moviebean> list, String url) {

    }

    @Override
    public void onParsrTelevisionUrlCallback(List<Televisionbean> list, String url) {

    }

    @Override
    public void onParsrMovieUrlCallback(List<Moviebean> list, String url) {

    }

    @Override
    public void onParseUrlError(Exception e) {

    }
}
