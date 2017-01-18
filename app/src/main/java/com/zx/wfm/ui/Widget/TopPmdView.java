package com.zx.wfm.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by aspros on 15/7/26.
 */
public abstract class TopPmdView extends TextView{
    private Context context;
    private int position;//弹幕的位置，在屏幕哪一行

    @SuppressLint("NewApi")
	public TopPmdView(Context context) {
        super(context);
        this.context=context;
        setSingleLine();
        setAlpha(0.8f);
        
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public abstract void send();


}
