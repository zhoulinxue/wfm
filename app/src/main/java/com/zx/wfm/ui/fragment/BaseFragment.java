package com.zx.wfm.ui.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.service.Impl.NetWorkServerImpl;
import com.zx.wfm.service.NetWorkServer;
import com.zx.wfm.service.ParseUrlServer;

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
    protected NetWorkServer server;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        server=new NetWorkServerImpl(this);
        onNetWorkServerCreat(server);
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
            for(final Televisionbean bean:finalObjList){
                if(TextUtils.isEmpty(bean.getObjectId())) {
                    AVQuery<Televisionbean> query = new AVQuery<>("Televisionbean");
                    query.whereEqualTo("TelevisionId", bean.getTelevisionId());
                    query.findInBackground(new FindCallback<Televisionbean>() {
                        @Override
                        public void done(List<Televisionbean> list, AVException e) {
                            if(list==null||list.size()==0){
                                bean.saveInBackground();
                            }
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


//            AVObject.saveAllInBackground(objList, new SaveCallback() {
//                @Override
//                public void done(AVException e) {
//                    if (e != null) {
//                        e.printStackTrace();
//                        Log.e("保存数据", e.getMessage());
//                    } else {
//                        Log.e("保存数据", "成功");
//                        DBManager.getInstance().saveTelevisions(finalObjList);
//                    }
//                }
//            });
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
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
        loadMoreCompelete(swipeToLoadLayout);
    }

    @Override
    public void OnGetTelevisionFromLeadCload(List<Televisionbean> list, String url) {

    }

    @Override
    public void OnGetMovieFromLeadCload(List<Moviebean> list, String url) {

    }

    @Override
    public void onParsrTelevisionUrlCallback(List<Televisionbean> list,String url) {
    }

    @Override
    public void onParsrMovieUrlCallback(List<Moviebean> list, String url) {

    }

    @Override
    public void onParseUrlError(Exception e) {

    }
    public void onNetWorkServerCreat(NetWorkServer server){

    }
     public boolean  postRefresh(String url){
        return true;
     };
}
