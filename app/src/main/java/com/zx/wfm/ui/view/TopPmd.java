package com.zx.wfm.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import com.zx.wfm.utils.PhoneUtils;

/**
 * Created by aspros on 15/7/26.
 */
public class TopPmd extends TopPmdView {
    private Animation animation0,animation1;
    private int fx0,tx0,fx1,tx1;
    private int duration0,duration1;
    private OnAnimationEndListener onAnimationEndListener;

    public interface OnAnimationEndListener
    {
        public void clearPosition();//第一个动画结束，将当前行设置为可以发送弹幕
        public void animationEnd();//弹幕完全移出屏幕
    }

    @SuppressLint("NewApi")
	public TopPmd(Context context,int fx,int tx)
    {
        super(context);
        this.fx0=fx;
        this.tx0=Math.abs(fx)-Math.abs(tx)-10;//第一个动画结束位置，当尾部空出10像素时就可以通知其他弹幕跟上了
        this.fx1=tx0;
        this.tx1=tx;

        duration0=10000*(Math.abs(tx0-fx0))/ PhoneUtils.getScreenWidth((Activity) context);
        duration1=10000*(Math.abs(tx1-fx1))/PhoneUtils.getScreenWidth((Activity)context);
//        setAlpha((float)(255*0.8));
        initAnimation();
    }

    private void initAnimation()
    {
        animation0=new TranslateAnimation(fx0,tx0,0,0);
        animation1=new TranslateAnimation(fx1,tx1,0,0);
        animation0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                clearAnimation();
                startAnimation(animation1);
                if (onAnimationEndListener!=null)
                {
                    onAnimationEndListener.clearPosition();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation0.setFillAfter(true);
        animation0.setDuration(duration0);
        animation0.setInterpolator(new LinearInterpolator());

        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(onAnimationEndListener!=null)
                {
                    onAnimationEndListener.animationEnd();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation1.setFillAfter(true);
        animation1.setDuration(duration1);
        animation1.setInterpolator(new LinearInterpolator());
    }

    public void setOnAnimationEndListener(OnAnimationEndListener onAnimationEndListener)
    {
        this.onAnimationEndListener=onAnimationEndListener;
    }

    @Override
    public void send() {
        startAnimation(animation0);
    }
}
