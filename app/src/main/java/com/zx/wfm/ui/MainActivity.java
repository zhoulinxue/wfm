package com.zx.wfm.ui;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.zx.wfm.R;
import com.zx.wfm.bean.VideoItembean;
import com.zx.wfm.bean.Videobean;
import com.zx.wfm.ui.BaseActivity;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieAdapter;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.DuzheUtils;
import com.zx.wfm.utils.SpacesItemDecoration;
import com.zx.wfm.utils.UKutils;


public class MainActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener,BaseRecycleViewAdapter.OnItemClickListener {
    private List<Videobean> list;
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private SwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        mRecyclerView= (RecyclerView) findViewById(R.id.swipe_target);
        movieAdapter=new MovieAdapter(this,list,R.layout.movie_item);
        movieAdapter.setOnItemClickListener(this);
        final StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(movieAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView,newState);
                manager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    Glide.with(MainActivity.this).resumeRequests();
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }else {
                    Glide.with(MainActivity.this).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    Log.i("滑动状态","上");

                }else if(dy>0){
                    Log.i("滑动状态","下");
                }
            }
        });

        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                list= UKutils.getVideoInfo("http://www.soku.com/channel/teleplaylist_0_0_0_1_1.html");
                Log.i("数据",list.size()+"");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                        if(list!=null&&list.size()!=0){
                            movieAdapter.setmList(list);
                        }
                    }
                });

            }
        }).start();
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Intent intent=new Intent(this,VideoDetailActivity.class);
        intent.putExtra(Constants.VIDEO_OBJ,movieAdapter.getmList().get(position));
        startActivity(intent);
    }
}
