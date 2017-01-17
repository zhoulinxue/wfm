package com.zx.wfm.ui;

import java.util.List;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.avos.avoscloud.LogUtil;
import com.bumptech.glide.Glide;
import com.zx.wfm.R;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieAdapter;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.NetWorkUtils;
import com.zx.wfm.utils.SpacesItemDecoration;
import com.zx.wfm.utils.ThreadUtil;
import com.zx.wfm.utils.ToastUtil;
import com.zx.wfm.utils.UKutils;


public class UKMainActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener,BaseRecycleViewAdapter.OnItemClickListener {
    private List<Televisionbean> list;
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private int page=0;
    private int pageNum;
    private int netPage=0;
    private  List<String> url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        mRecyclerView= (RecyclerView) findViewById(R.id.swipe_target);
        pageNum=preferences.getInt(Constants.PAGE_NUM,Constants.PAGE_MIN_NUM);
        netPage=preferences.getInt(Constants.NET_PAGE_NUM,0);
        list=DBManager.getInstance().getTelevisionList(Constants.Net.TELEVISION_URL);
        Log.i("下一页",UKutils.getNextPageUrl(Constants.Net.TELEVISION_URL));
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
                    Glide.with(UKMainActivity.this).resumeRequests();
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }else {
                    Glide.with(UKMainActivity.this).pauseRequests();
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
        List<Televisionbean> currentlist=movieAdapter.getmList();
        final String urlpage=UKutils.getNextPageUrl(currentlist.get(currentlist.size()-1).getNetPage());
        final List<Televisionbean> list=DBManager.getInstance().getTelevisionList(urlpage);
            if(list==null||list.size()==0){
                ThreadUtil.runOnNewThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Televisionbean> newlist=UKutils.getVideoInfo(urlpage);
                        loadMoreCompelete();
                        DBManager.getInstance().saveTelevisions(newlist);
                        movieAdapter.addAll(DBManager.getInstance().getTelevisionList(urlpage));
                    }
                });
            }else {
                loadMoreCompelete();
                movieAdapter.addAll(list);
            }
    }

    @Override
    public void onRefresh() {
        page=0;
        if(NetWorkUtils.isNetworkConnected(this)){
            getDataFromNet();
        }else {
          refreshCompelete();
        }
    }
  private void   getDataFromNet(){
      ThreadUtil.runOnNewThread( new Runnable() {
          @Override
          public void run() {
              try {
                  url =UKutils.getVideoPages();
                  refreshCompelete();
                  List<Televisionbean> list=UKutils.getVideoInfo(Constants.Net.TELEVISION_URL);
                  DBManager.getInstance().saveTelevisions(list);
                  if(movieAdapter.getItemCount()==0) {
                      movieAdapter.addAll(DBManager.getInstance().getTelevisionList(Constants.Net.TELEVISION_URL));
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      });
  }

    private void refreshCompelete() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(swipeToLoadLayout!=null)
                swipeToLoadLayout.setRefreshing(false);
            }
        });
    }
    private void loadMoreCompelete() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(swipeToLoadLayout!=null)
                    swipeToLoadLayout.setLoadingMore(false);
            }
        });
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Televisionbean bean=DBManager.getInstance().getTelevisionById(movieAdapter.getmList().get(position).getTelevisionId());
        Intent intent=new Intent(this,VideoDetailActivity.class);
        intent.putExtra(Constants.VIDEO_OBJ,bean);
        startActivity(intent);
    }
}
