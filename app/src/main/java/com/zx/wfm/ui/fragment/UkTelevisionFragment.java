package com.zx.wfm.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.zx.wfm.R;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.ui.VideoDetailActivity;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieAdapter;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.NetWorkUtils;
import com.zx.wfm.utils.SpacesItemDecoration;
import com.zx.wfm.utils.UKutils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *   作者：周学 on 2017/1/25 01:22
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public class UkTelevisionFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, BaseRecycleViewAdapter.OnItemClickListener {
    @BindView(R.id.ivArrow)
    ImageView ivArrow;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.ivSuccess)
    ImageView ivSuccess;
    @BindView(R.id.tvLoadMore)
    TextView tvLoadMore;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private int mType;
    private List<Televisionbean> list;
    protected MovieAdapter movieAdapter;
    private int page = 0;
    private int pageNum;
    private int netPage = 0;
    private String currentPage=Constants.Net.TELEVISION_URL;
    private String newPage=Constants.Net.TELEVISION_URL;

    public UkTelevisionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uk_activity_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void refreshFragment() {
        super.refreshFragment();
        if (swipeToLoadLayout == null) {
            return;
        }
        postRefresh(currentPage);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageNum = preferences.getInt(Constants.PAGE_NUM, Constants.PAGE_MIN_NUM);
        netPage = preferences.getInt(Constants.NET_PAGE_NUM, 0);
        list = DBManager.getInstance().getTelevisionList(currentPage);
//        Log.i("下一页", UKutils.getNextPageUrl(currentPage));
        movieAdapter = new MovieAdapter(getActivity(), list, R.layout.movie_item);
        movieAdapter.setColumnNum(2);
        movieAdapter.setOnItemClickListener(this);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
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
                super.onScrollStateChanged(recyclerView, newState);
                manager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getActivity()).resumeRequests();
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                } else {
                    Glide.with(getActivity()).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    Log.i("滑动状态", "上");

                } else if (dy > 0) {
                    Log.i("滑动状态", "下");
                }
            }
        });
         postRefresh(currentPage);
    }

    @Override
    public void onLoadMore() {
        page++;
        List<Televisionbean> currentlist = movieAdapter.getmList();
        final String urlpage = UKutils.getNextPageUrl(currentlist.get(currentlist.size() - 1).getNetPage());
        final List<Televisionbean> list = DBManager.getInstance().getTelevisionList(urlpage);
        if (list == null || list.size() == 0) {
            server.getTeleVisionDataFromNet(urlpage);
        } else {
            loadMoreCompelete(swipeToLoadLayout);
            movieAdapter.addAll(list);
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        if (NetWorkUtils.isNetworkConnected(getActivity())) {
            server.getTeleVisionDataFromNet(newPage);
        } else {
            refreshCompelete(swipeToLoadLayout,null);
        }

    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Log.i("item", movieAdapter.getmList().get(position).toString());
        Televisionbean bean = movieAdapter.getmList().get(position);
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.VIDEO_OBJ, movieAdapter.getmList().get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void OnGetTelevisionFromLeadCload(List<Televisionbean> list, String url) {
        if(currentPage.equals(url)||page!=0) {
            movieAdapter.addAll(DBManager.getInstance().getTelevisionList(url));
        }else {
            currentPage=url;
            movieAdapter.setmList(DBManager.getInstance().getTelevisionList(url));
        }
        refreshCompelete(swipeToLoadLayout, null);
    }

    @Override
    public void onParsrTelevisionUrlCallback(List<Televisionbean> list, String url) {
        DBManager.getInstance().saveTelevisions(list);
        movieAdapter.addAll(DBManager.getInstance().getTelevisionList(url));
        refreshCompelete(swipeToLoadLayout,list);
    }

    @Override
    public void onParseUrlError(Exception e) {
        refreshCompelete(swipeToLoadLayout, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unBindView(this);
    }
    @Override
    public boolean postRefresh(String url){
        newPage=url;
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        ((NavTwitterFragment)getParentFragment()).setTitle(getTitiFromUrl(newPage));
        return true;
    }

    public String getTitiFromUrl(String url) {
        switch (url){
            case Constants.Net.CARTOON_URL:
               return  "动漫";
            case Constants.Net.TELEVISION_URL:
                return  "电视剧";
            case Constants.Net.VARIETY_URL:
                return  "综艺";
            case Constants.Net.MOVIE_URL:
                return  "电影";

        }
        return "优酷";
    }
}
