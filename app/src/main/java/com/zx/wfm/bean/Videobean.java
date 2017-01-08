package com.zx.wfm.bean;

import java.util.List;

/**
 * Created by ${zhouxue} on 16/12/19 01: 16.
 * QQ:515278502
 */
public class Videobean extends Basebean{
    private String videoName;//名字
    private String headUrl;// 封面
    private String address; // 剧集数
    private String desc;// 视频描述
    private String rating;// 评分
    private List<VideoItembean> addrDetail;// 播放地址

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<VideoItembean> getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(List<VideoItembean> addrDetail) {
        this.addrDetail = addrDetail;
    }

    @Override
    public String toString() {
        return "Videobean{" +
                "videoName='" + videoName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", address='" + address + '\'' +
                ", desc='" + desc + '\'' +
                ", rating='" + rating + '\'' +
                ", addrDetail=" + addrDetail +
                '}';
    }
}
