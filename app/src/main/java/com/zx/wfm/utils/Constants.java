package com.zx.wfm.utils;

/**
 * Created by ${zhouxue} on 16/12/18 23: 19.
 * QQ:515278502
 */
public class Constants {
    public static String MOBLE_TRAFFIC_DATA="moble_traffic_data";
    public  static  final String MOBLE_TRAFFIC="moble_traffic";
    public static String DEFAULT_PASS_WORD="111111";
    public static String ERROR="return_msg";
    public static String VIDEO_OBJ="video_obj";
    public static String VIDEO_OBJ_LIST="video_obj_list";
    public static String VIDEO_ITEM_OBJ="video_item_obj";
    public static int PAGE_MAX_NUM=20;
    public static int PAGE_MIN_NUM=10;
    public static String PAGE_NUM="page_num";
    public static String NET_PAGE_NUM="net_page_num";
    public static String VIDEO_OBJ_POS="video_postion";
    public static String PAGE_URL="detail_url";
    public static String OBJ_ID="object_id";


    public static class Request_Code{
        public static  final  int RC_SETTINGS_SCREEN=1000;
        public static  final  int RC_CAMERA_AND_WIFI=1001;
        public static  final  int RC_READ_PHONE_STATE=1002;
        public static final int INTERNET=1003;
        public static final int RC_LOCATION_CONTACTS_PERM = 1004;
        // 加载视频
        public final static int LOAD_VIDEO=10000;
        //点赞
        public final static int ZAN=10001;
    }

    public static class Net {
        // 电视剧
//        http://list.youku.com/category/show/c_97_s_1_d_1_p_1.html
//        http://www.soku.com/channel/teleplaylist_0_0_0_1_1.html
        public final static String TELEVISION_URL="http://list.youku.com/category/show/c_97_s_1_d_1_p_1.html";
        // 电影
//
        public final static String MOVIE_URL="http://list.youku.com/category/show/c_96_pt_1_s_1_d_1_u_1.html";
//        public static String MOVIE_URL="http://www.soku.com/channel/movielist_0_0_0_1_1.html";
        // 综艺
//        http://list.youku.com/category/show/c_85_s_1_d_1_p_1.html
          public final static String VARIETY_URL="http://list.youku.com/category/show/c_85_s_1_d_1_p_1.html";
//        public static String VARIETY_URL="http://www.soku.com/channel/varietylist_0_0_0_1_1.html";
        // 动漫
        public final static String CARTOON_URL="http://list.youku.com/category/show/c_100_s_1_d_2_p_1.html";
//        public static String CARTOON_URL="http://www.soku.com/channel/animelist_0_0_0_1_1.html";
        public static  String SEARCH_URL="http://www.soku.com/search_video/q_";
        public static String HIML=".html";
    }

    public static class Tag {
        public static String PERMISSIONS="permission_deni";
        public static String PERMISSIONS_GRAND="permission_grand";
    }

    public static class User {
        public static String USER_UUID="user_uuid";
    }
}
