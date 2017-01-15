package com.zx.wfm.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zx.wfm.R;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.utils.RecyclerViewHolder;
import com.zx.wfm.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周学 on 2016/12/25.
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

   public Context mContext;
    protected List<T> mList;
    protected int mLayoutId;
    protected LayoutInflater mInflater;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;


    private OnItemClickListener onItemClickListener;

    public BaseRecycleViewAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mList = mDatas;
        this.mLayoutId = mLayoutId;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里是创建ViewHolder的地方，RecyclerAdapter内部已经实现了ViewHolder的重用
        //这里我们直接new就好了
        return new RecyclerViewHolder(mInflater.inflate(mLayoutId, parent, false),mContext);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        if (onItemClickListener != null) {
            //设置背景
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.style_color_accent));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //注意，这里的position不要用上面参数中的position，会出现位置错乱
                    onItemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
        convert(holder, mList.get(position), position);
    }
    public abstract void convert(RecyclerViewHolder holder, T data, int position);
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public int addAll(List<T> list) {
        if(mList==null){
            mList=new ArrayList<>();
        }
        if(list!=null&&list.size()!=0){
            if(!mList.containsAll(list)) {
                mList.addAll(list);
                return 0;
            }
            notifymyData();
        }
        return 1;
    }
    

    public int addAll(List<T> list,int position) {
        if(mList==null){
            mList=new ArrayList<>();
        }
        if(list!=null&&list.size()!=0){
            if(!mList.containsAll(list)) {
                mList.addAll(position, list);
                return 0;
            }
            notifymyData();
        }
        return 1;
    }

    /**自定义RecyclerView item的点击事件的点击事件*/
  public  interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    public List<T> getmList() {
        return mList;
    }

    public void setmList(List<T> mList) {
        this.mList = mList;
        notifymyData();
    }

    private void notifymyData() {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    
}

