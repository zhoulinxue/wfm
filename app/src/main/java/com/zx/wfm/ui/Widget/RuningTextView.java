package com.zx.wfm.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 周学 on 2017/1/2.
 */

public class RuningTextView extends TextView {
    public RuningTextView(Context context) {
        super(context);
    }

    public RuningTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RuningTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
