package com.zx.wfm.ui.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.service.ParseUrlServer;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.ThreadUtil;
import com.zx.wfm.utils.UKutils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by aspsine on 15/9/8.
 */
public class BaseFragment extends Fragment implements ParseUrlServer {
    public static final int TYPE_LINEAR = 0;
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;
    protected Activity mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            refreshFragment();
        }
    }

    protected void refreshFragment() {
    }

    protected void postToServer(final List<Televisionbean> list) {
//      final List<Televisionbean> list=  DBManager.getInstance().findLocalTelevision();
        Log.i("未上传数据",(list==null)+"");
        List<Televisionbean> objList=null;
        if(list!=null&&list.size()!=0){
        objList=new ArrayList<>();
        }else {
            return;
        }
        for(Televisionbean bean:list){
            objList.add((Televisionbean) bean.toself().toAVObject());
            Log.i("未上传数据",((Televisionbean) bean.toself().toAVObject()).toString());
        }
        if(list!=null&&list.size()!=0) {
            final List<Televisionbean> finalObjList = objList;
            AVObject.saveAllInBackground(objList, new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e != null) {
                        e.printStackTrace();
                        Log.e("保存数据", e.getMessage());
                    } else {
                        Log.e("保存数据", "成功");
                        DBManager.getInstance().saveTelevisions(finalObjList);
                    }
                }
            });
        }
    }

    protected void   getDataFromNet(final String url){
        AVQuery<Televisionbean> query = AVObject.getQuery(Televisionbean.class);
        query.whereEqualTo("netPage", url);
        query.findInBackground(new FindCallback<Televisionbean>() {
            @Override
            public void done(List<Televisionbean> results, AVException e) {
                Log.i("从野狗获取数据",(results==null)+"");
                if(results!=null&&results.size()!=0) {
                    DBManager.getInstance().saveTelevisions(results);
                   OnGetFromLeadCload(results,url);
                }else {
                    getDataFromYK(url);
                }
            }
        });

    }


    protected void getDataFromYK( final  String televisionUrl) {
        ThreadUtil.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Televisionbean> list= UKutils.getVideoInfo(televisionUrl, new FindCallback<Televisionbean>() {
                        @Override
                        public void done(List<Televisionbean> list, AVException e) {
                            DBManager.getInstance().saveTelevisions(list);
//                            if(movieAdapter.getItemCount()==0) {
//                                movieAdapter.addAll(DBManager.getInstance().getTelevisionList(televisionUrl));
//                            }
                        }
                    });
                    DBManager.getInstance().saveTelevisions(list);
                    onParsrUrlCallback(list,televisionUrl);
//                    movieAdapter.addAll(DBManager.getInstance().getTelevisionList(televisionUrl));
                } catch (Exception e) {
                    e.printStackTrace();
                    OnParseUrlError(e);
//                    refreshCompelete(swipeToLoadLayout,null);
                }
//                refreshCompelete(swipeToLoadLayout,movieAdapter.getmList());
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this,view);
        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor=preferences.edit();
    }

    protected void loadMoreCompelete(final SwipeToLoadLayout swipeToLoadLayout) {
        if(mContext==null){
            return;
        }
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(swipeToLoadLayout!=null)
                    swipeToLoadLayout.setLoadingMore(false);
            }
        });
    }
    protected void refreshCompelete(final SwipeToLoadLayout swipeToLoadLayout,List<Televisionbean> list) {
        postToServer(list);
        if(mContext==null){
            return;
        }
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(swipeToLoadLayout!=null)
                    swipeToLoadLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void OnGetFromLeadCload(List<Televisionbean> list,String url) {

    }

    @Override
    public void onParsrUrlCallback(List<Televisionbean> list,String url) {
    }

    @Override
    public void OnParseUrlError(Exception e) {

    }
}
