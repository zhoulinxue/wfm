package com.zx.wfm.ui.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.SaveCallback;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by aspsine on 15/9/8.
 */
public class BaseFragment extends Fragment {
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
        List<AVObject> objList=null;
        if(list!=null&&list.size()!=0){
        objList=new ArrayList<>();
        }else {
            return;
        }
        for(Televisionbean bean:list){
            objList.add(bean.toAVObject());
        }
        if(list!=null&&list.size()!=0) {
            Log.i("未上传数据",list.size()+"");
            AVObject.saveAllInBackground(objList, new SaveCallback() {
                @Override
                public void done(AVException e) {
                    e.printStackTrace();
                    if (e != null) {
                        Log.e("保存数据", e.getMessage());
                    } else {
                        Log.e("保存数据", "成功");
                        DBManager.getInstance().saveTelevisions(list);
                    }
                }
            });
        }
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
}
