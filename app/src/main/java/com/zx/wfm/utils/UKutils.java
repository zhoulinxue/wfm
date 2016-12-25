package com.zx.wfm.utils;

import android.util.Log;

import com.zx.wfm.bean.Videobean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhouxue} on 16/12/19 00: 31.
 * QQ:515278502
 */
public class UKutils {
    public static List<Videobean> getVideoInfo(String pageUrl) {// 一页调用一次
        Document doc = null;
        List<Videobean> list=new ArrayList<>();

        try {
            doc = Jsoup.connect(pageUrl).timeout(6000).get();
            // Added a maximum body response size to Jsoup.Connection, to
            // prevent running out of memory when trying to read extremely
            // large
            // documents. The default is 1MB.
        } catch (IOException e) {
            // TODO Auto-generated catch block
            getVideoInfo(pageUrl);
            System.out.println("connect error");
            e.printStackTrace();
        }
        if (doc == null) {
            Log.e("Jsoup", "doc==null");
            return null;
        }


        Elements divs_info = doc.getElementsByClass("p_link");// 视频专辑url，如电视剧

        if (divs_info != null) {

            if (divs_info.size() <= 0) {
                divs_info = doc.getElementsByClass("v_link");// 视频播放url，如资讯
            }
            Elements urls = divs_info.select("a[href]");

            if (null != urls) {
                int i = 0;

                for (Element urlElement : urls) {
                    Videobean  bean=new Videobean();
                            bean.setVideoName(urlElement.attr("title"));
                    bean.setHeadUrl(urlElement.attr("abs:href"));
                    list.add(bean);
                    i++;

                }
            }

        }

        Elements divs_thumbs = doc.getElementsByClass("p_thumb");// 获取专辑图片
        if (divs_thumbs != null) {

            Elements thumbs = divs_thumbs.select("img[original]");
            if (thumbs.size() <= 0) {
                divs_thumbs = doc.getElementsByClass("v_thumb");
                thumbs = divs_thumbs.select("img[original]");
            }
            if (null != thumbs) {
                for (int i=0;i<thumbs.size();i++) {
                    list.get(i).setHeadUrl(thumbs.get(i).attr("abs:original"));
                }

            }
        }
        return list;
//        divs_pgm_source = doc.getElementsByClass("pgm-source");// 获取更新情况
//        // divs_pgm_source.select(query)
//
//        if (divs_pgm_source != null) {
//            for (Element thumb1 : divs_pgm_source) {
//                sourceId = thumb1.select("span");
//                sourceUrl = thumb1.select("a");
//                List<String> videoSourceStatus = null;
//                List<String> videoSourceUrl = null;
//                List<String> videoSourceId = null;//保存获取的数据，以供构建xml文件
//
//                if (null != sourceId) {
//                    videoSourceId = new ArrayList<String>();
//                    for (Element thumb2 : sourceId) {
//
//                        videoSourceId.add(thumb2.attr("id"));
//
//                    }
//                    videoSourceIdList.add(videoSourceId);
//
//                }
//                if (null != sourceUrl) {
//                    videoSourceStatus = new ArrayList<String>();
//                    for (Element thumb2 : sourceUrl) {
//
//                        videoSourceStatus.add(thumb2.attr("status"));
//
//                    }
//                    videoSourceStatusList.add(videoSourceStatus);
//
//                }
//
//                if (null != sourceUrl) {
//                    videoSourceUrl = new ArrayList<String>();
//                    for (Element thumb2 : sourceUrl) {
//
//                        videoSourceUrl.add(thumb2.attr("href"));
//
//                    }
//                    videoSourceUrlList.add(videoSourceUrl);
//
//                }
////
//            }
//
//        }
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }


}
