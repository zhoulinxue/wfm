package com.zx.wfm.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;

import com.zx.wfm.R;

/**
 *   作者：周学 on 2017/2/25 17:22
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public class AnimUtils {
    public static AnimationDrawable getFramDrawable(Context context,int [] res) {
        AnimationDrawable frame = new AnimationDrawable();
        /*下面直接读取本地文件容易内存溢出*/
        for(int a:res ) {
            frame.addFrame(context.getResources().getDrawable(a), 100);
        }
        frame.setOneShot(false);
        return frame;
    }
    public static AnimationDrawable getFramDrawable(Context context,int [] res,int duration) {
        AnimationDrawable frame = new AnimationDrawable();
        /*下面直接读取本地文件容易内存溢出*/
        for(int a:res ) {
            frame.addFrame(context.getResources().getDrawable(a), duration);
        }
        frame.setOneShot(false);
        return frame;
    }

   public static AnimationDrawable getLoadingDrawable(Context context){
        final AnimationDrawable drawable = AnimUtils.getFramDrawable(context, new int[]{R.mipmap.loading_1,
                R.mipmap.loading_2, R.mipmap.loading_3,
                R.mipmap.loading_4, R.mipmap.loading_5, R.mipmap.loading_6,
                R.mipmap.loading_7, R.mipmap.loading_8, R.mipmap.loading_9, R.mipmap.loading_10,
                R.mipmap.loading_11, R.mipmap.loading_12});
       return drawable;
    }
}
