package com.zx.wfm.utils;

import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.SaveCallback;
import com.zx.wfm.Application.App;
import com.zx.wfm.bean.Televisionbean;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.dao.DBManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhouxue} on 16/12/19 00: 31.
 * QQ:515278502
 */
public class UKutils {
    private static String TAG=UKutils.class.getSimpleName();

    /**
     * 解析优酷电视剧 地址。
     * @param pageUrl 目标网页
     * @return
     */
    public static List<Televisionbean> getVideoInfo(String pageUrl,FindCallback<Televisionbean> callback) {// 一页调用一次
        Document doc =getDoc(pageUrl);
        final List<Televisionbean> list=new ArrayList<>();

        if (doc == null) {
            Log.e("Jsoup", "dot==null");
            return null;
        }
        Elements divs_info = doc.getElementsByClass("p-thumb");// 视频专辑url，如电视剧
        Elements desc_info = doc.getElementsByClass("actor");// 视频描述
        Elements times = doc.getElementsByClass("info-list");// 视频描述
        Elements link_info = doc.getElementsByClass("ibg");// 视频集数
        Elements rating_info = doc.getElementsByClass("ranking");// 视频评分
        Elements head_info = doc.getElementsByClass("quic");// 头像


        if (divs_info != null) {
            Log.d(TAG,desc_info.size()+"#"+link_info.size()+"@"+divs_info.size());
            if (divs_info.size() <= 0) {
                divs_info = doc.getElementsByClass("v_link");// 视频播放url，如资讯
            }
            Elements urls = divs_info.select("a[href]");
            if (null != urls) {
                for (int i=0;i<urls.size();i++) {
                   Element urlElement=urls.get(i);
                    Televisionbean bean=new Televisionbean();
                    bean.setHeadUrl(head_info.get(i).absUrl("src"));
                    bean.setTime(System.currentTimeMillis());
                    if(desc_info!=null&&desc_info.size()!=0&&desc_info.size()==urls.size()) {
                        bean.setDesc(desc_info.get(i).text());
                    }else {
                        bean.setDesc("暂无介绍");
                    }
                    bean.setVideoName(urlElement.attr("title"));
                    bean.setAddressUrl(urlElement.attr("abs:href"));
                    Log.e(TAG,"播放地址："+bean.getAddressUrl());
                    for(int a=0;a<times.get(i).select("li").size();a++){
                        Log.e(TAG,"li"+times.get(i).select("li").get(a).text());
                        if(a==2){
                            bean.setPlayTimes(times.get(i).select("li").get(a).text());
                        }
                    }
                    bean.setTelevisionId(bean.getAddressUrl());
                    bean.setNetPage(pageUrl);
                    getDetailaddres(bean);
                    if(i<rating_info.size()) {
                        bean.setRating(rating_info.get(i).text());
                    }else {
                        bean.setRating("无评分");
                    }
                    list.add(bean);
                    Log.i(TAG,bean.getRating());
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
                    list.get(i).toAVObject();
                    list.get(i).setFrom(AVUser.getCurrentUser().getObjectId());
                }
            }
        }
        return list;
    }

    public static String getPage(String pageUrl) {
        String[] strs=null;
        if(!TextUtils.isEmpty(pageUrl)) {
            strs = pageUrl.split("/");
            if(strs!=null)
            return strs[strs.length-1];
        }
      return "no";
    }

    /**
     * 获取下一页的电视剧 栏目
     * @param pageUrl
     * @return
     */
    public static String getNextPageUrl(String pageUrl) {
        String[] strs=null;
        String lastStr="";
        StringBuilder builder=new StringBuilder();
        if(!TextUtils.isEmpty(pageUrl)) {
            strs = pageUrl.split("_");
            lastStr =strs[strs.length-1];
            String urlpage=lastStr.replace(Constants.Net.HIML,"");
            if(!TextUtils.isEmpty(urlpage)){
                int page=Integer.parseInt(urlpage)+1;
                builder.append(page);
                builder.append(Constants.Net.HIML);
            }
        }
        return pageUrl.replace(lastStr,builder.toString());
    }

    /**
     *   解析并保存电视剧 每一集的内容
     * @param bean
     */

    private static void getDetailaddres(final Televisionbean bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Moviebean> list=getVideoList(bean);
                if(list==null){
                    return;
                }
                DBManager.getInstance().saveMoveBean(list);
            }
        }).start();
    }

    /**
     * 获取电视剧的 剧集
     * @param vbean
     * @return
     */
    public static List<Moviebean> getVideoList(Televisionbean vbean) {
        Document doc = getDoc(vbean.getAddressUrl());
        if(doc==null){
            return null;
        }
        List<Moviebean> list=new ArrayList<>();
        Elements link_info = doc.getElementsByClass("lists");
        if(link_info!=null&&link_info.size()!=0){
            Elements urls = link_info.get(0).select("a[href]");
            for(Element e:urls) {
                Log.i(TAG,e.ownText()+"!!!"+link_info.size()+"");
                Moviebean bean=new Moviebean();
                bean.setTelevisionId(vbean.getTelevisionId());
                bean.setItemUrl(e.attr("abs:href"));
                bean.setMovieDetail(e.ownText());
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
        if(doc==null){
            return null;
        }
        Elements pages_info=doc.getElementsByClass("pages");
        if(pages_info!=null){
            Elements elements=pages_info.select("a[href]");
            for(Element element:elements){
                String url=element.attr("abs:href");
                if(!pages.contains(url)) {
                    Log.i(TAG,url);
                    pages.add(url);
                }
            }

        }
        return  pages;
    }



    /**
     * 获取 html 文档
     * @param url
     * @return
     */
    public static Document getDoc(String url) {
        Log.d(TAG,url+"");

        try {
           return Jsoup.connect(url).timeout(20000).get();
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

    public static void getSearchWords(final String words) {
        List<Televisionbean> list=new ArrayList<>();
                String url=Constants.Net.SEARCH_URL+words;
//         WebConversation wc = new WebConversation();
//        Document doc=null;
//                // 向指定的URL发出请求，获取响应

//            WebResponse wr = wc.getResponse(url);
//            Log.e("unit", wr.getText());
//           doc=  new Document(wr.getText());
        Document doc=getDoc(url);

                if (doc == null) {
                    Log.e("Jsoup", "dot==null");
                    return ;
                }
               Elements elements=doc.getElementsByClass("DIR");
                Log.e("啥html",elements.size()+"!!@");
                if (null != elements) {
                    Elements adressUrls=elements.select("s_link");
                    Elements head_info = elements.select("s_target");// 头像
                    Elements bases=elements.select("s_info");
                    for (int i=0;i<elements.size();i++) {
                        Element urlElement=elements.get(i);
                        Televisionbean bean=new Televisionbean();
                        bean.setHeadUrl(head_info.get(i).absUrl("src"));
//                        bean.setAddressUrl(adressUrls.attr("abs:href"));
                        bean.setTime(System.currentTimeMillis());
                        if(bases!=null&&bases.size()!=0&&bases.size()==elements.size()) {
                            bean.setDesc(bases.get(i).text());
                        }else {
                            bean.setDesc("暂无介绍");
                        }
                        bean.setVideoName(urlElement.attr("title"));
                        bean.setAddressUrl(urlElement.attr("abs:href"));
                        Log.e(TAG,"播放地址："+bean.getAddressUrl());
                        bean.setTelevisionId(bean.getAddressUrl());
                        bean.setNetPage(url);
                        getDetailaddres(bean);
                            bean.setRating(elements.select("source").text());

                        list.add(bean);
                        Log.i(TAG,bean.toString());
                    }
                }

    }
}
