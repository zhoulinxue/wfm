package com.zx.wfm.bean;

/**
 * Created by ${zhouxue} on 16/12/19 01: 16.
 * QQ:515278502
 */
public class Videobean extends Basebean{
    private String videoName;
    private String headUrl;
    private String address;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Videobean{" +
                "videoName='" + videoName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
