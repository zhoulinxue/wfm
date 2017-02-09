package com.zx.wfm.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogUtil;
import com.bumptech.glide.Glide;
import com.zx.wfm.R;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.ui.VideoDetailActivity;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieAdapter;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.NetWorkUtils;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.SpacesItemDecoration;
import com.zx.wfm.utils.ThreadUtil;
import com.zx.wfm.utils.UKutils;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *   作者：周学 on 2017/1/25 01:22
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public class UkTelevisionFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener,BaseRecycleViewAdapter.OnItemClickListener{
    private int mType;
    private List<Televisionbean> list;
    @InjectView(R.id.swipe_target)
    protected RecyclerView mRecyclerView;
    protected MovieAdapter movieAdapter;
    @InjectView(R.id.swipeToLoadLayout)
    protected SwipeToLoadLayout swipeToLoadLayout;
    private int page=0;
    private int pageNum;
    private int netPage=0;
    public static Fragment newInstance(int type) {
        TwitterRecyclerFragment fragment = new TwitterRecyclerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("LAYOUT_MANAGER_TYPE", type);
        fragment.setArguments(bundle);
        return fragment;
    }
    public UkTelevisionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mType = getArguments().getInt("LAYOUT_MANAGER_TYPE", TYPE_LINEAR);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.uk_activity_main,container,false);
//        ButterKnife.inject(this,view);
        return view;
    }
    @Override
    protected void refreshFragment() {
        super.refreshFragment();
        if(swipeToLoadLayout==null){
            return;
        }
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageNum=preferences.getInt(Constants.PAGE_NUM,Constants.PAGE_MIN_NUM);
        netPage=preferences.getInt(Constants.NET_PAGE_NUM,0);
        list= DBManager.getInstance().getTelevisionList(Constants.Net.TELEVISION_URL);
        Log.i("下一页", UKutils.getNextPageUrl(Constants.Net.TELEVISION_URL));
        movieAdapter=new MovieAdapter(getActivity(),list,R.layout.movie_item);
        movieAdapter.setColumnNum(2);
        movieAdapter.setOnItemClickListener(this);
        final StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(movieAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView,newState);
                manager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    Glide.with(getActivity()).resumeRequests();
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }else {
                    Glide.with(getActivity()).pauseRequests();
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
    public void onLoadMore() {
        List<Televisionbean> currentlist=movieAdapter.getmList();
        final String urlpage=UKutils.getNextPageUrl(currentlist.get(currentlist.size()-1).getNetPage());
        final List<Televisionbean> list=DBManager.getInstance().getTelevisionList(urlpage);
        if(list==null||list.size()==0){
            ThreadUtil.runOnNewThread(new Runnable() {
                @Override
                public void run() {
                    List<Televisionbean> newlist=UKutils.getVideoInfo(urlpage, new FindCallback<Televisionbean>() {
                        @Override
                        public void done(List<Televisionbean> list, AVException e) {
                            for(Televisionbean televisionbean:list){
                                Log.i("服务器",televisionbean.toString());
                            }
                            loadMoreCompelete();
                            DBManager.getInstance().saveTelevisions(list);
                            movieAdapter.addAll(DBManager.getInstance().getTelevisionList(urlpage));
                        }
                    });
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
            page = 0;
            if (NetWorkUtils.isNetworkConnected(getActivity())) {
                getDataFromNet();
            } else {
                refreshCompelete();
            }

    }
    private void   getDataFromNet(){
        ThreadUtil.runOnNewThread( new Runnable() {
            @Override
            public void run() {
                try {
                    List<Televisionbean> list=UKutils.getVideoInfo(Constants.Net.TELEVISION_URL, new FindCallback<Televisionbean>() {
                        @Override
                        public void done(List<Televisionbean> list, AVException e) {
                            DBManager.getInstance().saveTelevisions(list);
                            if(movieAdapter.getItemCount()==0) {
                                movieAdapter.addAll(DBManager.getInstance().getTelevisionList(Constants.Net.TELEVISION_URL));
                            }
                        }
                    });
                    DBManager.getInstance().saveTelevisions(list);
                    if(movieAdapter.getItemCount()==0) {
                        movieAdapter.addAll(DBManager.getInstance().getTelevisionList(Constants.Net.TELEVISION_URL));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    refreshCompelete();
                }
                refreshCompelete();
            }
        });
    }

    private void refreshCompelete() {
//        postToServer(movieAdapter.getmList());
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
    private void loadMoreCompelete() {
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

    @Override
    public void OnItemClickListener(View view, int position) {
        Televisionbean bean=DBManager.getInstance().getTelevisionById(movieAdapter.getmList().get(position).getTelevisionId());
        Intent intent=new Intent(getActivity(),VideoDetailActivity.class);
        intent.putExtra(Constants.VIDEO_OBJ,(Serializable) bean);
        startActivity(intent);
    }

}
