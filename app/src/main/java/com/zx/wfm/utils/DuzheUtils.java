package com.zx.wfm.utils;

import android.util.Log;

import com.zx.wfm.bean.News;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by 周学 on 2016/12/31.
 */

public class DuzheUtils {
    public static List<News> BolgBody() throws IOException {
       List<News> list =new ArrayList<News>();
        // 从 URL 直接加载 HTML 文档
        Document doc2 = Jsoup.connect("http://www.hdz8.cn/html/?282536.html").get();
        Elements elements1= doc2.getElementsByClass("list_main");
        Log.i("看看",elements1.first().getElementsByTag("h1").text()+"\n");
        Elements elements=doc2.getElementsByClass("content");
        Log.i("看看",elements.first().tagName("b").text()+"\n");
        for(Element element:elements){
            Log.i("看看",element.tagName("span").text());
        }


       return list;
    }
}
