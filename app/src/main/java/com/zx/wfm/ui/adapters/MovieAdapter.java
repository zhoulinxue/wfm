package com.zx.wfm.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.wfm.R;
import com.zx.wfm.bean.Videobean;
import com.zx.wfm.ui.View.ViewHolder;

import java.util.List;

/**
 * Created by ${zhouxue} on 16/12/19 01: 33.
 * QQ:515278502
 */
public class MovieAdapter extends BaseRecycleViewAdapter<Videobean>{
    public MovieAdapter(Context mContext, List<Videobean> mDatas, int mLayoutId) {
        super(mContext, mDatas, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, Videobean data, int position) {
        Log.i("数据",data.toString());
        holder.setImageNet(R.id.movie_head,data.getHeadUrl()).setText(R.id.movie_name,data.getVideoName());
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }
}
