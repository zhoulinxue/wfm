package com.zx.wfm.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.params.HttpParams;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zx.wfm.Application.WFMApplication;
import com.zx.wfm.R;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.ToastUtil;

public class WebActivity extends Activity {

	private WebView webview;
	private ProgressDialog mDialog;
	private Context mContext;
	private ImageButton backBtn = null;
	private Handler mHandler = new Handler();
	private RelativeLayout topbar = null;
	private View customView;
	private FrameLayout fullScreenView;
	String home="http://player.youku.com/embed/XMTk3MjA0MjIw";
//	String home ="<iframe height=498 width=510 src='http://player.youku.com/embed/XMTk3MjA0MjIw' frameborder=0 'allowfullscreen'></iframe>";
	private Context context;
	private String[] hostStr;
	private int index=0;
	private boolean hasPress = false;
	private long firstTouchBackBt;
	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		hostStr=getHostStr();
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.web_activity);
		mContext = this;
		SharedPreferences share = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		fullScreenView= (FrameLayout) findViewById(R.id.fullScreen);
		mDialog = new ProgressDialog(mContext);
		webview = (WebView) findViewById(R.id.webview);
		webview.setVerticalScrollBarEnabled(false);
		webview.setDownloadListener(new WebViewDownLoadListener());
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



		// -------------------------------------------------------------------------

//		webview.loadData("<iframe height="+ PhoneUtils.getScreenHight(this)+" width="+PhoneUtils.getScreenWidth(this)+"src='http://player.youku.com/embed/XMTk3MjA0MjIw' frameborder=0 'allowfullscreen'></iframe>","text/html","utf-8");
		webview.loadUrl(home);

		webview.addJavascriptInterface(new Object(){
			public void click(){
				Log.i("点击","!!!!!!!!!!!");
			}
		},"resize");


	}
	public  String[] getHostStr() {
		String hostStr="";
		try {
			InputStream in= getAssets().open("hosts.txt");
			int size=in.available();
			byte[] bytes=new byte[size];
			in.read(bytes);
			in.close();
			hostStr=new String(bytes,"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hostStr.trim().split("0.0.0.0");
	}

	@Override
	protected void onResume() {
		super.onResume();
		// WebView.enablePlatformNotifications();
		webview.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// WebView.disablePlatformNotifications();
		webview.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			if (mDialog != null && mDialog.isShowing()) {
				mDialog.dismiss();
			} else {
				webview.goBack();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public int clearCacheFolder(File dir, long numDays) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, numDays);
					}
					if (child.lastModified() < numDays) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		clearCacheFolder(mContext.getCacheDir(), System.currentTimeMillis());
		webview.destroy();
	}

	public  boolean hasAd(Context context, String url) {
		 for (String adUrl : hostStr) {
			 if (url.contains(adUrl)) {
				 return true;
				 }
			 }
		 return false;
		 }

	private class WeweWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return true;
		}

		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
			index++;
			url = url.toLowerCase();
//			Log.i("广告",hostStr.length+"@"+hasAd(context, url)+"!!!"+url);
//			if(!url.contains(home)){
//				 if (!hasAd(context, url)) {
//					 return super.shouldInterceptRequest(view, url);
//					 }else{
			if(index%7==0||index%5==0) {
				return new WebResourceResponse(null, null, null);
			}
//					 }
//				 }else{
				Log.i("广告",url+"");
				 return super.shouldInterceptRequest(view, url);
//				 }
		}

		@Override
		public void onLoadResource(WebView view, String url) {
//			Log.i("广告","开始"+url);
			super.onLoadResource(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			try {
				mDialog.setMessage(getString(R.string.web_loading));
				mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mDialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// webview.stopLoading();
						// finish();
					}
				});
				if (!mDialog.isShowing())
					mDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}

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
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			webview.setVisibility(View.INVISIBLE);
			// 如果一个视图已经存在，那么立刻终止并新建一个
			if (customView != null) {
				callback.onCustomViewHidden();
				return;
			}
			fullScreenView.addView(view);
			customView = view;
			customViewCallback = callback;
			fullScreenView.setVisibility(View.VISIBLE);
		}

		@Override
		public void onHideCustomView() {
			if (customView == null)// 不是全屏播放状态
				return;
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			customView.setVisibility(View.GONE);
			fullScreenView.removeView(customView);
			customView = null;
			fullScreenView.setVisibility(View.GONE);
			customViewCallback.onCustomViewHidden();
			webview.setVisibility(View.VISIBLE);


		}

		@Override
		public View getVideoLoadingProgressView() {
			return super.getVideoLoadingProgressView();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
			if (!hasPress) {
				firstTouchBackBt = System.currentTimeMillis();
				ToastUtil.showToastLong(this, "再次点击退出");
				hasPress = true;
			} else {
				if ((System.currentTimeMillis() - firstTouchBackBt) < 2000) {
					hasPress = false;
					this.moveTaskToBack(true);
				} else {
					hasPress = true;
					firstTouchBackBt = System.currentTimeMillis();
					ToastUtil.showToastLong(this, "再次点击退出");
				}
			}
	}
}
