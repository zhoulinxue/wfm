package com.zx.wfm.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.zx.wfm.R;
import com.zx.wfm.bean.Videobean;

import java.util.List;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

/**
 * Created by ${zhouxue} on 16/12/19 01: 33.
 * QQ:515278502
 */
public class MovieAdapter extends BaseRecycleViewAdapter<Videobean> {
    private boolean isUpScrolling=false;
    public MovieAdapter(Context mContext, List<Videobean> mDatas, int mLayoutId) {
        super(mContext, mDatas, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, Videobean data, int position) {
        Log.i("数据",data.toString());
        View target=holder.findView(R.id.movie_head);
        target.setAlpha(0f);
        animate(target).setDuration(1000).alpha(1f);
        holder.setImageNet(R.id.movie_head,data.getHeadUrl()).setText(R.id.movie_name,data.getVideoName());
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


}
