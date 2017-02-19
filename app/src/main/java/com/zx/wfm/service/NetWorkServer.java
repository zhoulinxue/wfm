package com.zx.wfm.service;

import com.zx.wfm.bean.Televisionbean;

/**
 *   作者：周学 on 2017/2/18 19:28
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public interface NetWorkServer {
    public void   getAllTeleVisionDataFromNet();
    public void   getAllMovieDataFromNet();
    public void   getTeleVisionDataFromNet(String url);
    public void   getTeleVisionDataFromYK(String televisionUrl);
    public void   getMovieDataFromNet(Televisionbean url);
    public void   getMovieDataFromYK(Televisionbean televisionbean);
}
