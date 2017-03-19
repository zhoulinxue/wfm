package com.zx.wfm.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.nineoldandroids.animation.Animator;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.zx.wfm.MainActivity;
import com.zx.wfm.R;
import com.zx.wfm.bean.AVErrorbean;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.bean.TeleMsgbean;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.bean.Userbean;
import com.zx.wfm.dao.DBManager;
import com.zx.wfm.service.Impl.NetWorkServerImpl;
import com.zx.wfm.service.NetWorkServer;
import com.zx.wfm.ui.view.RoundProgressBar;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.SharedPreferencesUtil;
import com.zx.wfm.utils.ThreadUtil;
import com.zx.wfm.utils.ToastUtil;
import com.zx.wfm.utils.UKutils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

/**
 * Created by Administrator on 2017/1/17.
 */

public class InitActivity extends BaseActivity{
    private static final long DURATION_TIME =1250 ;
    private Handler mHandler;
    private  int second=0;
    @BindView(R.id.roundProgressBar)
    protected RoundProgressBar bar;
    boolean isOnPause=false;
    @BindView(R.id.sui)
    TextView suitv;
    @BindView(R.id.yue)
    TextView yuetv;
    @BindView(R.id.liu)
    TextView liutv;
    @BindView(R.id.sheng)
    TextView sheng;
    @BindView(R.id.shimmer_tv)
    ShimmerTextView shimmerTextView;
    private int width;
    private int hight;
    private int index=0;
    Shimmer shimmer;
    boolean isDownLoad=false;
    NetWorkServer service;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_layout);
        ButterKnife.bind(this);
        service=new NetWorkServerImpl();
        mHandler=new Handler();
        width=PhoneUtils.getScreenWidth(this);
        hight=PhoneUtils.getScreenHight(this);
        shimmer = new Shimmer();
        shimmer.setDuration(3000);
        shimmer.start(shimmerTextView);
        RequiresPermissionMethod();
    }
    private void RequiresPermissionMethod() {
        String[] perms = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            initData(PhoneUtils.getImeiInfo(this));
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "读取手机信息",
                    Constants.Request_Code.RC_READ_PHONE_STATE, perms);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        if(Constants.Request_Code.RC_READ_PHONE_STATE==requestCode){
            Log.e(Constants.Tag.PERMISSIONS,"拒绝读取手机信息");
            initData(SharedPreferencesUtil.getInstance().getUUID());
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        if(Constants.Request_Code.RC_READ_PHONE_STATE==requestCode){
            Log.e(Constants.Tag.PERMISSIONS_GRAND,"权限允许手机信息");
            initData(PhoneUtils.getImeiInfo(this));
        }
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

    private void initData(final String name) {
        mHandler.postDelayed(gotoMainRunable,1000);
        ThreadUtil.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                initBugly();
                DBManager.init(InitActivity.this);
                setnetWorkType();
                logintoServer(name);
            }
        });
        animateView(suitv);
        AVObject.registerSubclass(TeleMsgbean.class);
        AVObject.registerSubclass(Televisionbean.class);
        AVObject.registerSubclass(Moviebean.class);
        AVOSCloud.initialize(InitActivity.this,"2zBSbem5VbsxPa1dou5nH8EQ-gzGzoHsz","ra2GN4GM8uypJQ8khu7H2wqg");
        AVOSCloud.setDebugLogEnabled(true);
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    // 关联  installationId 到用户表等操作……
                } else {
                    // 保存失败，输出错误信息
                }
            }
        });
        PushService.setDefaultPushCallback(this, InitActivity.class);

        DownLoadDataToLocal();
    }

    private void DownLoadDataToLocal() {
        ThreadUtil.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                service.getAllMovieDataFromNet();
                service.getAllTeleVisionDataFromNet();
            }
        });
    }

    public void logintoServer(String name) {
        AVUser.logInInBackground(name, Constants.DEFAULT_PASS_WORD, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
//                    Toast.makeText(InitActivity.this,"欢迎小主",Toast.LENGTH_SHORT).show();
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
        Beta.upgradeCheckPeriod=10*1000;
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
        mHandler.removeCallbacks(gotoMainRunable);
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
        mHandler.post(gotoMainRunable);
    }
    Runnable gotoMainRunable=new Runnable() {
        @Override
        public void run() {
            if(second>=3000) {
                mHandler.removeCallbacks(null);
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                    bar.setProgress(second / 30);
                    mHandler.postDelayed(gotoMainRunable, 10);
                if(!isOnPause) {
                    second += 10;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shimmer.cancel();
    }

}
