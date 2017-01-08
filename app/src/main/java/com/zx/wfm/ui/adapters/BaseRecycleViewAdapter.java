package com.zx.wfm.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zx.wfm.R;

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
    /**自定义RecyclerView item的点击事件的点击事件*/
  public  interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    public List<T> getmList() {
        return mList;
    }

    public void setmList(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
class RecyclerViewHolder extends RecyclerView.ViewHolder {

    /** 用于存储当前item当中的View */
    private SparseArray<View> mViews;
    private Context mCotext;

    public RecyclerViewHolder(View itemView,Context mCotext) {
        super(itemView);
        mViews = new SparseArray<View>();
        this.mCotext=mCotext;
    }
    public <T extends View> T findView(int ViewId) {
        View view = mViews.get(ViewId);
        //集合中没有，则从item当中获取，并存入集合当中
        if (view == null) {
            view = itemView.findViewById(ViewId);
            mViews.put(ViewId, view);
        }
        return (T) view;
    }
    public RecyclerViewHolder setText(int viewId, String text) {
        TextView tv = findView(viewId);
        tv.setText(text);
        return this;
    }
    public RecyclerViewHolder setText(int viewId, int text) {
        TextView tv = findView(viewId);
        tv.setText(text);
        return this;
    }
    public RecyclerViewHolder setImageResource(int viewId, int ImageId) {
        ImageView image = findView(viewId);
        image.setImageResource(ImageId);
        return this;
    }
    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView image = findView(viewId);
        image.setImageBitmap(bitmap);
        return this;
    }
    public RecyclerViewHolder setImageNet(int viewId, String url) {
        ImageView image = findView(viewId);
        //使用你所用的网络框架等
        Glide.with(mCotext.getApplicationContext()).load(url).error(R.mipmap.ic_launcher).into(image);
        return this;
    }

}
