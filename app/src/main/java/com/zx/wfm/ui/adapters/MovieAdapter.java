package com.zx.wfm.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.wfm.R;
import com.zx.wfm.bean.Videobean;

import java.util.List;

/**
 * Created by ${zhouxue} on 16/12/19 01: 33.
 * QQ:515278502
 */
public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private List<Videobean> mList;

    public MovieAdapter(Context mContext, List<Videobean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.movie_item,null);
            TextView tv= (TextView) view.findViewById(R.id.movie_name);
            ImageView img= (ImageView) view.findViewById(R.id.movie_head);

        }
        return view;
    }
}
