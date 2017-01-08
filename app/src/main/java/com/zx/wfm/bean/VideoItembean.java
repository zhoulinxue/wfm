package com.zx.wfm.bean;

import com.fasterxml.jackson.databind.deser.Deserializers;

import java.util.List;

/**
 * Created by 周学 on 2017/1/8.
 */
public class VideoItembean extends Basebean {
    private String itemUrl;
    private List<String> introduces;
    private String videoName;
    private String videoHeadUrl;
    private String desc;// 视频描述

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public List<String> getIntroduces() {
        return introduces;
    }

    public void setIntroduces(List<String> introduces) {
        this.introduces = introduces;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoHeadUrl() {
        return videoHeadUrl;
    }

    public void setVideoHeadUrl(String videoHeadUrl) {
        this.videoHeadUrl = videoHeadUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
