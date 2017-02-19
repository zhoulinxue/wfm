package com.zx.wfm.service;

import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.bean.Televisionbean;

import java.util.List;

/**
 *   作者：周学 on 2017/2/18 18:38
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public interface ParseUrlServer {
    public void   OnGetTelevisionFromLeadCload(List<Televisionbean> list,String url);
    public void   OnGetMovieFromLeadCload(List<Moviebean> list, String url);
    public  void  onParsrTelevisionUrlCallback(List<Televisionbean> list,String url);
    public  void  onParsrMovieUrlCallback(List<Moviebean> list,String url);
    public void   onParseUrlError(Exception e);


}
