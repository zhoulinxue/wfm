package com.zx.wfm.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.zx.wfm.R;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.utils.RecyclerViewHolder;

import java.util.List;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

/**
 * Created by ${zhouxue} on 16/12/19 01: 33.
 * QQ:515278502
 */
public class MovieAdapter extends BaseRecycleViewAdapter<Televisionbean> {
    private boolean isUpScrolling=false;
    public MovieAdapter(Context mContext, List<Televisionbean> mDatas, int mLayoutId) {
        super(mContext, mDatas, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, Televisionbean data, int position) {
        Log.i("数据",data.toString());
        final View target=holder.findView(R.id.movie_head);
        target.setAlpha(0f);
        animate(target).setDuration(1000).alpha(1f);
        holder.setImageNet(R.id.movie_head,data.getHeadUrl()).setText(R.id.movie_name,data.getVideoName());
        holder.findView(R.id.name_layout).getBackground().setAlpha(150);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


}
