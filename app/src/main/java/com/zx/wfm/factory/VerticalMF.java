package com.zx.wfm.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.zx.wfm.R;

public class VerticalMF extends MarqueeFactory<TextView, String> {
    private LayoutInflater inflater;

    public VerticalMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public TextView generateMarqueeItemView(String data) {
        TextView mView = (TextView) inflater.inflate(R.layout.describe_item, null);
        mView.setText(data);
        return mView;
    }
}