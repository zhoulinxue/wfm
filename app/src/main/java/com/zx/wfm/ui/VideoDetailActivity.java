package com.zx.wfm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.zx.wfm.R;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieItemAdapter;
import com.zx.wfm.ui.view.ItemDecoration;
import com.zx.wfm.ui.view.RecycleViewDivider;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.ThreadUtil;
import com.zx.wfm.utils.UKutils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;


/**
 * Created by zhouxue on 2016/7/29.
 * Company czl_zva
 */
public class VideoDetailActivity extends BaseActivity implements View.OnClickListener ,BaseRecycleViewAdapter.OnItemClickListener{
    @InjectView(R.id.video_numn_recycal)
     RecyclerView mRecyclerView;
    MovieItemAdapter movieItemAdapter;
    private Televisionbean videobean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_detail_layout);
        Intent intent=getIntent();
        videobean= (Televisionbean) intent.getExtras().getParcelable(Constants.VIDEO_OBJ);
        final List<Moviebean> list= DBManager.getInstance().getMovieListByTeleId(videobean.getTelevisionId());
        if(list==null){
            ThreadUtil.runOnNewThread(new Runnable() {
                @Override
                public void run() {
                 movieItemAdapter.setmList(UKutils.getVideoList(videobean));
                    postToserver();
                }
            });
        }
        movieItemAdapter=new MovieItemAdapter(this,list,R.layout.video_num_item_layout);
        movieItemAdapter.setColumnNum(4);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.addItemDecoration(new ItemDecoration());
        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);
        ImageView headimg= (ImageView) header.findViewById(R.id.video_header_head_img);
        headimg.setLayoutParams(new RelativeLayout.LayoutParams(PhoneUtils.getScreenWidth(this)*3/7, ViewGroup.LayoutParams.WRAP_CONTENT));
        Glide.with(this).load(videobean.getHeadUrl()).into(headimg);
        TextView name= (TextView) header.findViewById(R.id.video_header_name);
        name.setText(videobean.getVideoName());
        TextView desc= (TextView) header.findViewById(R.id.video_desc);

        desc.setText("    "+videobean.getDesc());
        header.attachTo(mRecyclerView,true);
        mRecyclerView.setAdapter(movieItemAdapter);
        movieItemAdapter.setOnItemClickListener(this);
        postToserver();
    }

    private void postToserver() {
      final   List<Moviebean> list=movieItemAdapter.getmList();
        List<Moviebean> objList=null;
        if(list!=null&&list.size()!=0){
            objList=new ArrayList<>();
        }else {
            return;
        }
        for(Moviebean bean:list){
            Moviebean mbean=(Moviebean)bean.toAVObject();
//            mbean.setObjectId("");
            objList.add(mbean);
        }
        if(list!=null&&list.size()!=0) {
            final List<Moviebean> finalObjList = objList;
            AVObject.saveAllInBackground(objList, new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null) {
                        DBManager.getInstance().saveMoveBean(finalObjList);
                    }else {
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
        final ProgressBar bar= (ProgressBar) view.findViewById(R.id.progressbar);
        final  TextView tv= (TextView) view.findViewById(R.id.video_num_tv);
        tv.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bar.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                Intent intent=new Intent(VideoDetailActivity.this,PlayActivity.class);
                intent.putParcelableArrayListExtra(Constants.VIDEO_OBJ_LIST, (ArrayList<? extends Parcelable>) movieItemAdapter.getmList());
                intent.putExtra(Constants.VIDEO_OBJ_POS,position);
                startActivity(intent);
            }
        },200);


    }
}
