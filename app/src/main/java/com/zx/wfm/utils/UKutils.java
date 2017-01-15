package com.zx.wfm.utils;

import android.util.Log;

import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.bean.Moviebean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhouxue} on 16/12/19 00: 31.
 * QQ:515278502
 */
public class UKutils {
    public static List<Televisionbean> getVideoInfo(String pageUrl) {// 一页调用一次
        Document doc =getDoc(pageUrl);
        List<Televisionbean> list=new ArrayList<>();
        List<String> pages=new ArrayList<>();

        if (doc == null) {
            Log.e("Jsoup", "doc==null");
            return null;
        }
        Elements divs_info = doc.getElementsByClass("p_link");// 视频专辑url，如电视剧
        Elements desc_info = doc.getElementsByClass("p_desc");// 视频描述
        Elements link_info = doc.getElementsByClass("p_panels");// 视频集数
        Elements rating_info = doc.getElementsByClass("ranking");// 视频评分
        Elements pages_info=doc.getElementsByClass("pages");
//        if(pages_info!=null){
//            Elements elements=pages_info.select("a[href]");
//            for(Element element:elements){
//                String url=element.attr("abs:href");
//                if(!pages.contains(url)) {
//                    Log.i("多少页",url);
//                    pages.add(url);
//                }
//            }
//
//        }
        if (divs_info != null) {
            Log.d("size",desc_info.size()+"#"+link_info.size()+"@"+divs_info.size());
            if (divs_info.size() <= 0) {
                divs_info = doc.getElementsByClass("v_link");// 视频播放url，如资讯
            }
            Elements urls = divs_info.select("a[href]");
            if (null != urls) {
                for (int i=0;i<urls.size();i++) {
                   Element urlElement=urls.get(i);
                    Televisionbean bean=new Televisionbean();
                    bean.setTime(System.currentTimeMillis());
                    bean.setDesc(desc_info.get(i).text());
                            bean.setVideoName(urlElement.attr("title"));
                    bean.setAddressUrl(urlElement.attr("abs:href"));
                    bean.setTelevisionId(bean.getAddressUrl());
                    getDetailaddres(bean);
                    if(i<rating_info.size()) {
                        bean.setRating(rating_info.get(i).text());
                    }else {
                        bean.setRating("无评分");
                    }
                    list.add(bean);
                    Log.i("desc",bean.getRating());
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
    }

    private static void getDetailaddres(final Televisionbean bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Moviebean> list=getVideoList(bean);
                if(list==null){
                    return;
                }
                bean.setMoviebeans(list);
                Log.i("总集数：",bean.getVideoName()+":"+bean.childrens().size());
            }
        }).start();
    }

    private static List<Moviebean> getVideoList(Televisionbean vbean) {
        Document doc = getDoc(vbean.getAddressUrl());
        if(doc==null){
            return null;
        }
        List<Moviebean> list=new ArrayList<>();
        Elements link_info = doc.getElementsByClass("linkpanel");
        Log.i("link_info",link_info.size()+"");
        if(link_info!=null&&link_info.size()!=0){
            Elements urls = link_info.get(0).select("a[href]");
            for(Element e:urls) {
                Moviebean bean=new Moviebean();
                bean.setItemUrl(e.attr("abs:href"));
                bean.setMovieId(bean.getItemUrl());
                bean.setVideoName(vbean.getVideoName());
                bean.setDesc(vbean.getDesc());
                bean.setVideoHeadUrl(vbean.getHeadUrl());
                list.add(bean);
            }
        }
        return list;
    }

    /**
     *
     * @return 每一页得 url
     * @throws Exception
     */
    public static List<String> getVideoPages() throws Exception{
        List<String> pages=new ArrayList<>();
        String firsturl= Constants.Net.TELEVISION_URL;
        pages.add(firsturl);
        Document  doc =getDoc(firsturl);
        Elements pages_info=doc.getElementsByClass("pages");
        if(pages_info!=null){
            Elements elements=pages_info.select("a[href]");
            for(Element element:elements){
                String url=element.attr("abs:href");
                if(!pages.contains(url)) {
                    Log.i("多少页",url);
                    pages.add(url);
                }
            }

        }
        return  pages;
    }


    public static void getRealUrl(final String itemUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc=getDoc(itemUrl);
                Elements element=doc.getElementsByClass("panels");
                Log.i("input元素",itemUrl+"!"+ doc.outerHtml());
            }
        }).start();
    }

    public static Document getDoc(String url) {
        try {
           return Jsoup.connect(url).timeout(6000).get();
            // Added a maximum body response size to Jsoup.Connection, to
            // prevent running out of memory when trying to read extremely
            // large
            // documents. The default is 1MB.
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
