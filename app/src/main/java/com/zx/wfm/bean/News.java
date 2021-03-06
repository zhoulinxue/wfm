package com.zx.wfm.bean;

import java.io.Serializable;

/**
 * Created by 周学 on 2016/12/31.
 */
public class News implements Serializable{
    private String title;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
