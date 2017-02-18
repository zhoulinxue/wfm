package com.zx.wfm.service;

import com.zx.wfm.bean.Televisionbean;

import java.util.List;

/**
 *   作者：周学 on 2017/2/18 18:38
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public interface ParseUrlServer {
    public void   OnGetFromLeadCload(List<Televisionbean> list,String url);
    public  void  onParsrUrlCallback(List<Televisionbean> list,String url);
    public void   onParseUrlError(Exception e);

}
