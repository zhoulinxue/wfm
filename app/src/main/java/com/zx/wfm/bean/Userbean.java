package com.zx.wfm.bean;

import android.annotation.SuppressLint;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVUser;

/**
 * Created by 周学 on 2017/1/8.
 */
public class Userbean {
    private AVUser avUser;
    private String deviceId;

    public Userbean() {
        avUser=new AVUser();
    }

    public AVUser getAvUser() {
        return avUser;
    }

    public void setAvUser(AVUser avUser) {
        this.avUser = avUser;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
