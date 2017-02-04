package com.zx.wfm.ui.adapters;
import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import com.zx.wfm.R;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.RecyclerViewHolder;

import java.util.List;


/**
 * Created by 周学 on 2017/1/8.
 */
public class MovieItemAdapter extends BaseRecycleViewAdapter<Moviebean> {
    private int columnNum=4;
    public MovieItemAdapter(Context mContext, List<Moviebean> mDatas, int mLayoutId) {
        super(mContext, mDatas, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, Moviebean data, int position) {
        if(position==getCurrentPosition()){
            if(position==0) {
                holder.itemView.setBackgroundResource(R.drawable.shape_bottom_start_radus_now);
            }else if((position)%columnNum==0){
                holder.itemView.setBackgroundResource(R.drawable.shape_bottom_start_radus_now);
            }else if((position+1)%columnNum==0){
                if(position+1==columnNum) {
                    holder.itemView.setBackgroundResource(R.drawable.shape_bottom_end_radus_now);
                }else {
                    holder.itemView.setBackgroundResource(R.drawable.shape_left_radus_now);
                }
            }else if(position%columnNum!=0&&position<columnNum){
                    holder.itemView.setBackgroundResource(R.drawable.shape_bottom_radus_now);
            }else {
                //设置背景
                holder.itemView.setBackgroundResource(R.drawable.shape_four_radus_now);
            }
        }else {
            if(position==0){
                holder.itemView.setBackgroundResource(R.drawable.shape_bottom_start_radus_nomal);
            }else if(position%columnNum==0) {
                    holder.itemView.setBackgroundResource(R.drawable.shape_right_radus_nomal);

            } else if((position+1)%columnNum==0){
                if(position+1==columnNum) {
                    holder.itemView.setBackgroundResource(R.drawable.shape_bottom_end_radus_nomal);
                }else {
                    holder.itemView.setBackgroundResource(R.drawable.shape_left_radus_nomal);
                }
            }else if(position%columnNum!=0&&position<columnNum){
                    holder.itemView.setBackgroundResource(R.drawable.shape_bottom_radus_nomal);
            }else {
                //设置背景
                holder.itemView.setBackgroundResource(R.drawable.shape_four_radus_nomal);
            }
        }
            holder.findView(R.id.video_num_tv).setLayoutParams(
                    new LinearLayout.LayoutParams(PhoneUtils.getScreenWidth((Activity) mContext) / columnNum,
                            PhoneUtils.getScreenWidth((Activity) mContext) / columnNum));
            holder.setText(R.id.video_num_tv, (position +1) +"集");
    }
}
