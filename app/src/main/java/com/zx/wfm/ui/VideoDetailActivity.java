package com.zx.wfm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.zx.wfm.R;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieItemAdapter;
import com.zx.wfm.ui.view.ItemDecoration;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * Created by zhouxue on 2016/7/29.
 * Company czl_zva
 */
public class VideoDetailActivity extends BaseActivity implements View.OnClickListener, BaseRecycleViewAdapter.OnItemClickListener, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.head_img)
    ImageView headimg;
    @BindView(R.id.circleImageView_head)
    CircleImageView circleimageview;
    @BindView(R.id.video_desc)
    TextView descTv;
    @BindView(R.id.header_layout)
    LinearLayout headerLayout;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.main_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.video_numn_recycal)
    RecyclerView mRecyclerView;
    private Televisionbean videobean;
    private int mMaxScrollSize;
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private MovieItemAdapter movieItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_coordinator);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        videobean = (Televisionbean) intent.getExtras().getParcelable(Constants.VIDEO_OBJ);
        final List<Moviebean> list = DBManager.getInstance().getMovieListByTeleId(videobean.getTelevisionId());
        if (list == null || list.size() == 0) {
            server.getMovieDataFromNet(videobean);
        }
        appBarLayout.addOnOffsetChangedListener(this);
        Glide.with(this).load(videobean.getHeadUrl()).bitmapTransform(new BlurTransformation(this, 50)).into(headimg);
        Glide.with(this).load(videobean.getHeadUrl()).into(circleimageview);
        descTv.setText(videobean.getDesc());
        movieItemAdapter = new MovieItemAdapter(this, list, R.layout.video_num_item_layout);
        movieItemAdapter.setColumnNum(4);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.addItemDecoration(new ItemDecoration());
        collapsingToolbarLayout.setTitle(videobean.getVideoName());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.style_color_primary_dark));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRecyclerView.setAdapter(movieItemAdapter);
        movieItemAdapter.setOnItemClickListener(this);
    }


    private void postToserver() {
        final List<Moviebean> list = movieItemAdapter.getmList();
        List<Moviebean> objList = null;
        if (list != null && list.size() != 0) {
            objList = new ArrayList<>();
        } else {
            return;
        }
        for (Moviebean bean : list) {
            Moviebean mbean = (Moviebean) bean.toAVObject();
//            mbean.setObjectId("");
            objList.add(mbean);
        }
        if (list != null && list.size() != 0) {
            final List<Moviebean> finalObjList = objList;
            AVObject.saveAllInBackground(objList, new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        DBManager.getInstance().saveMoveBean(finalObjList);
                    } else {
                        e.printStackTrace();
                        Log.e("保存数据", e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.bottom_slid_in, R.anim.bottom_slid_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_slid_in, R.anim.bottom_slid_out);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void OnItemClickListener(View view, final int position) {
        movieItemAdapter.setCurrentPosition(position);
        final ProgressBar bar = (ProgressBar) view.findViewById(R.id.progressbar);
        final TextView tv = (TextView) view.findViewById(R.id.video_num_tv);
        tv.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bar.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                Intent intent = new Intent(VideoDetailActivity.this, PlayActivity.class);
                intent.putParcelableArrayListExtra(Constants.VIDEO_OBJ_LIST, (ArrayList<? extends Parcelable>) movieItemAdapter.getmList());
                intent.putExtra(Constants.VIDEO_OBJ_POS, position);
                startActivity(intent);
            }
        }, 200);


    }

    @Override
    public void OnGetMovieFromLeadCload(List<Moviebean> list, String url) {
        DBManager.getInstance().saveMoveBean(list);
        movieItemAdapter.setmList(list);
    }

    @Override
    public void onParsrMovieUrlCallback(List<Moviebean> list, String url) {
        DBManager.getInstance().saveMoveBean(list);
        movieItemAdapter.setmList(list);
        postToserver();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0) {
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        }

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            headerLayout.animate().scaleY(0).scaleX(0).setDuration(500).start();
        }


        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;
            headerLayout.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }
}
