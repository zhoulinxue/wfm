package com.zx.wfm.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zx.wfm.R;


/**
 * Created by zhouxue on 2016/7/28.
 * Company czl_zva
 */
public class DialogUtils {
    public static PopupWindow createPopupWindow(Context mContext, View.OnClickListener onClickListener,View rootView,String noticeMsg,String leftText,String rightText) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_select, null);
        final PopupWindow    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView titletx = (TextView) view.findViewById(R.id.title);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView certain = (TextView) view.findViewById(R.id.certain);//确认
        TextView cancel = (TextView) view.findViewById(R.id.cancel);//取消
        titletx.setText(noticeMsg);
        if(leftText!=null){
            cancel.setText(leftText);
        }
        if(rightText!=null){
            certain.setText(rightText);
        }
        content.setVisibility(View.GONE);
        certain.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);

        popupWindow.setFocusable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        return  popupWindow;
    }


}
