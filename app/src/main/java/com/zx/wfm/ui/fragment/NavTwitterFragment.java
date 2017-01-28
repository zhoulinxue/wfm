package com.zx.wfm.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.wfm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavTwitterFragment extends BaseNavPagerFragment {

    public static BaseNavigationFragment newInstance() {
        BaseNavigationFragment fragment = new NavTwitterFragment();
        return fragment;
    }


    public NavTwitterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // override the method
        // delete app:layout_scrollFlags="scroll|enterAlways"
        // delete reason: ListView don't support coordinate scroll
        // these property lead height measure issue and scroll issue
        // it's not an bug of SwipeToLoadLayout
        return inflater.inflate(R.layout.fragment_nav_twitter, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("优酷视频");
    }

    @Override
    protected String[] getTitles() {
        return new String[]{"电视剧","电影","动漫","综艺", "明星",
                "音乐" };
    }

    @Override
    protected Fragment getFragment(int position) {
        String title = getTitles()[position];
        Fragment fragment = null;
        if(title.equals("电视剧")){
            fragment=new UkTelevisionFragment();
        }else if (title.equals("电影")) {
            fragment = new UkMoveFragment();
        } else if (title.equals("动漫")) {
            fragment = new UkCartoonFragment();
        }else if (title.equals("综艺")) {
            fragment = new UkVarietyFragment();
        }else if (title.equals("明星")) {
            fragment = new TwitterGridViewFragment();
        } else if (title.equals("音乐")) {
            fragment = TwitterRecyclerFragment.newInstance(TwitterRecyclerFragment.TYPE_LINEAR);
        }
        return fragment;
    }


}
