package com.zx.wfm.ui.adapters;

import android.content.Context;
import android.widget.LinearLayout;

import com.zx.wfm.R;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.ui.VideoDetailActivity;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.RecyclerViewHolder;

import java.util.List;

/**
 * Created by 周学 on 2017/1/8.
 */
public class MovieItemAdapter extends BaseRecycleViewAdapter<Moviebean> {
    public MovieItemAdapter(Context mContext, List<Moviebean> mDatas, int mLayoutId) {
        super(mContext, mDatas, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, Moviebean data, int position) {
            holder.findView(R.id.video_num_tv).setLayoutParams(new LinearLayout.LayoutParams(PhoneUtils.getScreenWidth((VideoDetailActivity) mContext) / 4, PhoneUtils.getScreenWidth((VideoDetailActivity) mContext) / 4));
            holder.setText(R.id.video_num_tv, (position +1) +"集");
    }
}