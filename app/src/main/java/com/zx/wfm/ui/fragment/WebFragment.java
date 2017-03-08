package com.zx.wfm.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.zx.wfm.R;
import com.zx.wfm.bean.TrafficInfo;
import com.zx.wfm.factory.NoticeMF;
import com.zx.wfm.factory.VerticalMF;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.UKutils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

/**
 * Created by ${zhouxue} on 17/1/27 16: 53.
 * QQ:515278502
 */

public class WebFragment extends BaseFragment {
    @BindView(R.id.webview)
    WebView webview;
    private ProgressDialog mDialog;
    private Context mContext;
    private View customView;
    String home="http://player.youku.com/embed/XMTg4NTkzNDg4";

    private Context context;
    private String[] hostStr;
    private int index=0;
    private boolean hasPress = false;
    private long firstTouchBackBt;
    @BindView(R.id.right_bottom_view)
    TextView rightbottomView;
    @BindView(R.id.fresh_video)
    Button refreshbtn;

    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.marqueeView_left)
    MarqueeView marqueeViewLeft;
    @BindView(R.id.marqueeView_right)
    MarqueeView marqueeViewRight;

    private Paint paint;
    private Handler mHandler=new Handler();
    private boolean isAlph=false;
    private List<TrafficInfo> infoList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_fragment, container, false);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        SharedPreferences share = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        mDialog = new ProgressDialog(mContext);
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.i("index",index+"");
//					webview.loadUrl(home);
                new Thread(getRealUrl).start();
            }
        });
        webview.setVerticalScrollBarEnabled(false);
        webview.setDownloadListener(new  WebViewDownLoadListener());
        webview.setWebViewClient(new WeweWebViewClient());
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setBackgroundColor(0);
        WebSettings ws = webview.getSettings();
        //自适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setLoadWithOverviewMode(true);
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setDatabaseEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setSaveFormData(false);
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
//		ws.setLoadWithOverviewMode(false);//<==== 一定要设置为false，不然有声音没图像
        ws.setUseWideViewPort(true);
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setBuiltInZoomControls(false);
        ws.setSupportZoom(false);
        ws.setDisplayZoomControls(false);
        ws.setSupportMultipleWindows(true);
        mDialog.setMessage(getString(R.string.web_loading));
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (mDialog!=null&&!mDialog.isShowing()) {
            mDialog.show();
        }

        // -------------------------------------------------------------------------
        webview.addJavascriptInterface(new Object(){
            public void click(){
                Log.i("点击","!!!!!!!!!!!");

            }
        },"resize");
        refreshbtn.bringToFront();
        mHandler.postDelayed(freshbtnRunable,20*1000);
        initMaqueeView();
    }

    private void initMaqueeView() {
        final List<String> fresh = Arrays.asList("加载失败?", "有广告?");
        MarqueeFactory<TextView, String> marqueeFactory = new NoticeMF(getActivity());
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.setAnimDuration(3000);
        marqueeView.setInterval(6000);
        marqueeView.startFlipping();
        marqueeFactory.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                mDialog.show();
            }
        });
        marqueeFactory.setData(fresh);
        final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
        MarqueeFactory<TextView, String> marqueeFactoryleft = new VerticalMF(getActivity());
        marqueeViewLeft.setMarqueeFactory(marqueeFactoryleft);
        marqueeViewLeft.setAnimDuration(3000);
        marqueeViewLeft.setInterval(6000);
        marqueeViewLeft.startFlipping();
        marqueeFactoryleft.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                Toast.makeText(getActivity(), holder.data, Toast.LENGTH_SHORT).show();
            }
        });
        marqueeFactoryleft.setData(datas);

        MarqueeFactory<TextView, String> marqueeFactoryRight = new VerticalMF(getActivity());
        marqueeViewRight.setMarqueeFactory(marqueeFactoryRight);
        marqueeViewRight.startFlipping();
        marqueeViewRight.setAnimDuration(3000);
        marqueeViewRight.setInterval(6000);
        marqueeFactoryRight.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                Toast.makeText(getActivity(), holder.data, Toast.LENGTH_SHORT).show();
            }
        });
        marqueeFactoryRight.setData(datas);

    }
    Runnable freshbtnRunable=new Runnable() {
        @Override
        public void run() {
            if(isAlph) {
                refreshbtn.setAlpha(0f);
                animate(refreshbtn).setDuration(2000).alpha(1f);
                isAlph=false;
            }else {
                isAlph=true;
                refreshbtn.setAlpha(1f);
                animate(refreshbtn).setDuration(2000).alpha(0f);
            }
        }
    };

    Runnable getRealUrl=new Runnable() {
        @Override
        public void run() {
            Document doc= UKutils.getDoc(home);
            Elements elements= doc.getElementsByClass("item");

            if(elements!=null&&elements.size()!=0){
                Element element=elements.get(0);
                final Element ele= element.getElementById("link4");
                String str= ele.attr("value");
//				final	String url=str.replace("height=498","height="+(PhoneUtils.getScreenHight(WebActivity.this))).replace("width=510","width="+PhoneUtils.getScreenWidth(WebActivity.this));

                int start=str.indexOf("src");
                int end=str.indexOf("frameborder");
                final  String url=str.substring(start+5,end-2);
                for(Element element1:elements){
                    Log.i("内容",element1.html());
                }

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        webview.loadUrl(url);
//						webview.loadData(url,"text/html","utf-8");
                    }
                },0);
            }
        }
    };


    private class WeweWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            index++;
            url = url.toLowerCase();
            if((index%7==0||index%11==0)&&!url.equals("http://player.youku.com/h5player/img/xplayerv4.png")) {
                Log.i("广告",index+"  url"+url);
                return new WebResourceResponse(null, null, null);
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            try {
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private class WebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }

    }

    final class MyWebChromeClient extends WebChromeClient {
        private CustomViewCallback customViewCallback;
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            result.confirm();
            return true;
        }

        @Override
        public void onConsoleMessage(String message, int lineNumber,
                                     String sourceID) {
            // TODO Auto-generated method stub
            super.onConsoleMessage(message, lineNumber, sourceID);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
//            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            webview.setVisibility(View.INVISIBLE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (customView != null) {
                callback.onCustomViewHidden();
                return;
            }

            customView = view;
            customViewCallback = callback;
        }

        @Override
        public void onHideCustomView() {
            if (customView == null)// 不是全屏播放状态
                return;
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            customView.setVisibility(View.GONE);

            customView = null;
            customViewCallback.onCustomViewHidden();
            webview.setVisibility(View.VISIBLE);


        }

        @Override
        public View getVideoLoadingProgressView() {
            return super.getVideoLoadingProgressView();
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.i("进度","进度"+newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
}
