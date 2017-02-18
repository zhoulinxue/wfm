package com.zx.wfm.service.Impl;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.service.NetWorkServer;
import com.zx.wfm.service.ParseUrlServer;
import com.zx.wfm.utils.ThreadUtil;
import com.zx.wfm.utils.UKutils;

import java.util.List;

/**
 *   作者：周学 on 2017/2/18 19:30
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public class NetWorkServerImpl implements NetWorkServer {
    private ParseUrlServer server;

    public NetWorkServerImpl(ParseUrlServer server) {
        this.server = server;
    }

    @Override
    public void getDataFromNet(final String url) {
        AVQuery<Televisionbean> query = AVObject.getQuery(Televisionbean.class);
        query.whereEqualTo("netPage", url);
        query.findInBackground(new FindCallback<Televisionbean>() {
            @Override
            public void done(List<Televisionbean> results, AVException e) {
                Log.i("从野狗获取数据",(results==null)+"");
                if(results!=null&&results.size()!=0) {
                    DBManager.getInstance().saveTelevisions(results);
                    server.OnGetFromLeadCload(results,url);
                }else {
                    getDataFromYK(url);
                }
            }
        });
    }

    @Override
    public void getDataFromYK(final String televisionUrl) {
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
                    server.onParsrUrlCallback(list,televisionUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    server.onParseUrlError(e);
                }
            }
        });
    }
}
