package com.zx.wfm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.zx.wfm.R;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieItemAdapter;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.PhoneUtils;

import java.io.Serializable;
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
        videobean= (Televisionbean) intent.getSerializableExtra(Constants.VIDEO_OBJ);
        List<Moviebean> list= DBManager.getInstance().getMovieListByTeleId(videobean.getTelevisionId());
        movieItemAdapter=new MovieItemAdapter(this,list,R.layout.video_num_item_layout);
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

        Intent intent=new Intent(this,PlayActivity.class);
        intent.putExtra(Constants.VIDEO_OBJ_LIST,(Serializable) movieItemAdapter.getmList());
        startActivity(intent);

    }
}
