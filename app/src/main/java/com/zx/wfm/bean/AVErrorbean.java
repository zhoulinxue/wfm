package com.zx.wfm.bean;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by 周学 on 2017/1/8.
 */
public class AVErrorbean implements Serializable {
    private int code;
    private String error;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
