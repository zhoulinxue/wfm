package com.zx.wfm.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zx.wfm.R;
import com.zx.wfm.utils.Constants;

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
        return new String[]{"优酷"};
    }

    @Override
    protected Fragment getFragment(int position) {
        String title = getTitles()[position];
        Fragment fragment = null;
        if(title.equals("优酷")){
            fragment=new UkTelevisionFragment();
        }
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_television:
               return  ((BaseFragment)mAdapter.getItem(viewPager.getCurrentItem())).postRefresh(Constants.Net.TELEVISION_URL);
            case R.id.action_movie:
                return  ((BaseFragment)mAdapter.getItem(viewPager.getCurrentItem())).postRefresh(Constants.Net.MOVIE_URL);
            case R.id.action_cartoon:
                return ((BaseFragment)mAdapter.getItem(viewPager.getCurrentItem())).postRefresh(Constants.Net.CARTOON_URL);
            case R.id.action_varty:
                return  ((BaseFragment)mAdapter.getItem(viewPager.getCurrentItem())).postRefresh(Constants.Net.VARIETY_URL);

        }
        return super.onOptionsItemSelected(item);
    }
}
