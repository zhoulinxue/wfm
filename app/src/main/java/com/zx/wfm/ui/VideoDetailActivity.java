package com.zx.wfm.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.zx.wfm.R;
import com.zx.wfm.bean.VideoItembean;
import com.zx.wfm.bean.Videobean;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieItemAdapter;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.UKutils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by zhouxue on 2016/7/29.
 * Company czl_zva
 */
public class VideoDetailActivity extends BaseActivity implements View.OnClickListener ,BaseRecycleViewAdapter.OnItemClickListener{
    @InjectView(R.id.video_numn_recycal)
     RecyclerView mRecyclerView;
    MovieItemAdapter movieItemAdapter;
    private Videobean videobean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.video_detail_layout);
        ButterKnife.inject(this);
        Intent intent=getIntent();
        videobean= (Videobean) intent.getSerializableExtra(Constants.VIDEO_OBJ);
        new Thread(videoDetailrunable).start();
        movieItemAdapter=new MovieItemAdapter(this,videobean.getAddrDetail(),R.layout.video_num_item_layout);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);
        ImageView headimg= (ImageView) header.findViewById(R.id.video_header_head_img);
        headimg.setLayoutParams(new RelativeLayout.LayoutParams(PhoneUtils.getScreenWidth(this)/3, ViewGroup.LayoutParams.WRAP_CONTENT));
        Glide.with(this).load(videobean.getHeadUrl()).into(headimg);
        TextView name= (TextView) header.findViewById(R.id.video_header_name);
        name.setText(videobean.getVideoName());
        TextView desc= (TextView) header.findViewById(R.id.video_header_introdesc);

        desc.setText("    "+videobean.getDesc());
        header.attachTo(mRecyclerView,true);
        mRecyclerView.setAdapter(movieItemAdapter);
        movieItemAdapter.setOnItemClickListener(this);

    }

    Runnable videoDetailrunable=new Runnable() {
        @Override
        public void run() {
            if(videobean!=null){
//                UKutils.
            }
        }
    };

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
    public void OnItemClickListener(View view, int position) {
        Intent intent=new Intent(this,WebActivity.class);
        intent.putExtra(Constants.VIDEO_ITEM_OBJ,movieItemAdapter.getmList().get(position));
        startActivity(intent);
    }
}
