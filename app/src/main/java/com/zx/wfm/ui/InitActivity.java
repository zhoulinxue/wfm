package com.zx.wfm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.nineoldandroids.animation.Animator;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.zx.wfm.MainActivity;
import com.zx.wfm.R;
import com.zx.wfm.bean.AVErrorbean;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.bean.Userbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.ui.view.RoundProgressBar;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.ThreadUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

/**
 * Created by Administrator on 2017/1/17.
 */

public class InitActivity extends Activity{
    private static final long DURATION_TIME =1500 ;
    private Handler mHandler;
    private  int second=0;
    private RoundProgressBar bar;
    boolean isOnPause=false;
    @InjectView(R.id.sui)
    TextView suitv;
    @InjectView(R.id.yue)
    TextView yuetv;
    @InjectView(R.id.liu)
    TextView liutv;
    @InjectView(R.id.sheng)
    TextView sheng;
    private int width;
    private int hight;
    private int index=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_layout);
        ButterKnife.inject(this);
        mHandler=new Handler();
        bar = (RoundProgressBar) findViewById(R.id.roundProgressBar);
        width=PhoneUtils.getScreenWidth(this);
        hight=PhoneUtils.getScreenHight(this);
        mHandler.postDelayed(gotoMainRunable,1000);
        initData();
    }
    Animator.AnimatorListener listener=new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            index++;
            switch (index){
                case 1:
                    animateView(yuetv);
                    break;
                case 2:
                    animateView(liutv);
                    break;
                case 3:
                    animateView(sheng);
                    break;
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    void animateView(View view){
        animate(view).setDuration(DURATION_TIME).alpha(1f).setListener(listener);
    }

    private void initData() {
        ThreadUtil.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                initBugly();
                DBManager.init(InitActivity.this);
                setnetWorkType();
                logintoServer();
            }
        });
        animateView(suitv);
        AVObject.registerSubclass(Televisionbean.class);
        AVOSCloud.initialize(InitActivity.this,"2zBSbem5VbsxPa1dou5nH8EQ-gzGzoHsz","ra2GN4GM8uypJQ8khu7H2wqg");
    }

    public void logintoServer() {
        AVUser.logInInBackground(PhoneUtils.getImeiInfo(this), Constants.DEFAULT_PASS_WORD, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    Toast.makeText(InitActivity.this,"欢迎小主",Toast.LENGTH_SHORT).show();
                } else {
                    AVErrorbean errorBean= JSONObject.parseObject(e.getMessage(), AVErrorbean.class);
                    if(errorBean.getCode()==211||"Could not find user".equals(errorBean.getError())){
                        registerUser();
                    }
                }
            }
        });
    }

    private void initBugly() {
        Beta.initDelay = 2 * 1000;
        Bugly.init(getApplicationContext(), "7a23adc558", false);
    }


    private void registerUser() {
        Userbean user=new Userbean();
        user.getAvUser().setUsername(PhoneUtils.getImeiInfo(this));
        user.getAvUser().put("deviceId",PhoneUtils.getImeiInfo(this));
        user.getAvUser().setPassword(Constants.DEFAULT_PASS_WORD);
        user.getAvUser().signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 当前网络类型
     */
    private void setnetWorkType() {
//        if(NetWorkUtils.getNetworkType(this)!=NetWorkUtils.NETTYPE_WIFI&&NetWorkUtils.isNetworkConnected(this)){
//            editor.putBoolean(Constants.MOBLE_TRAFFIC,true);
//        }else {
//            editor.putBoolean(Constants.MOBLE_TRAFFIC,false);
//        }
//        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isOnPause=false;
    }
    Runnable gotoMainRunable=new Runnable() {
        @Override
        public void run() {
            if(second>=5000) {
                mHandler.removeCallbacks(null);
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {

                    bar.setProgress(second / 50);
//                if(second%1000==0) {
//                    bar.setProgressText(second / 100 + "s");
//                }
                    mHandler.postDelayed(gotoMainRunable, 10);
                if(!isOnPause) {
                    second += 10;
                }
            }
        }
    };
}
