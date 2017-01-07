package com.zx.wfm.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.wfm.Application.WFMApplication;
import com.zx.wfm.R;
import com.zx.wfm.bean.TrafficInfo;
import com.zx.wfm.ui.Widget.TopPmd;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.PhoneUtils;
import com.zx.wfm.utils.ToastUtil;
import com.zx.wfm.utils.TrafficManagerUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class WebActivity extends Activity {
	@InjectView(R.id.webview)
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
	@InjectView(R.id.right_bottom_view)
	 TextView rightbottomView;
	@InjectView(R.id.fresh_video)
	 Button refreshbtn;
	@InjectView(R.id.left_tv)
	 TextView leftTv;
	@InjectView(R.id.right_tv)
	TextView rightTv;
	@InjectView(R.id.container)
	 RelativeLayout container;
	private Paint paint;
	private Handler mHandler=new Handler();
	private boolean isAlph=false;
	private List<TrafficInfo> infoList=new ArrayList<>();
	TrafficInfo myinfo;
	private long lastTotal;
	private Long moble;


	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		hostStr=getHostStr();
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.web_activity);
		ButterKnife.inject(this);
		mContext = this;
		SharedPreferences share = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		moble=share.getLong(Constants.MOBLE_TRAFFIC_DATA,0l);
		mDialog = new ProgressDialog(mContext);
		mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Log.i("index",index+"");
					webview.loadUrl(home);
			}
		});
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

		// -------------------------------------------------------------------------
		webview.addJavascriptInterface(new Object(){
			public void click(){
				Log.i("点击","!!!!!!!!!!!");
			}
		},"resize");
		refreshbtn.bringToFront();
		mHandler.postDelayed(freshbtnRunable,20*1000);
		initpaintPmdView();
		new Thread(refreshTrafficRunable).start();
	}
	Runnable refreshTrafficRunable =new Runnable(){

		@Override
		public void run() {
			if(myinfo!=null){
				Long total=TrafficStats.getUidRxBytes(myinfo.getUid())+TrafficStats.getUidTxBytes(myinfo.getUid());
				if(WFMApplication.preferences.getBoolean(Constants.MOBLE_TRAFFIC,false)){
					moble=moble+total-lastTotal;
				}
			String text=PhoneUtils.formatTrafficByte(total);
//				setPmd(PhoneUtils.formatTrafficByte(TrafficStats.getUidRxBytes(myinfo.getUid())+TrafficStats.getUidTxBytes(myinfo.getUid())));
			  leftTv.setText(text);

				WFMApplication.editor.putLong(Constants.MOBLE_TRAFFIC_DATA,moble);
				WFMApplication.editor.commit();
				lastTotal=total;
			}else {
				TrafficManagerUtils utils=new TrafficManagerUtils(WebActivity.this);
				infoList=utils.getInternetTrafficInfos();
				for (TrafficInfo info:infoList){
					if(info.getPackname().equals(getPackageName())){
						myinfo=info;
					};
				}
			}
			mHandler.postDelayed(refreshTrafficRunable,3000);
		}
	};

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
	@OnClick(R.id.fresh_video)
	public void BtnRefresh(Button refreshbtn){
		index=0;
		mDialog.show();
	}

	@OnClick(R.id.right_bottom_view) void fullScreen(){
		ToastUtil.showToastLong(WebActivity.this,R.string.already_fullscreen);
	}

	@Override
	protected void onResume() {
		super.onResume();
		webview.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		webview.onPause();
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
		mHandler.removeCallbacks(null);
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
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

	private void setPmd(String text) {
		final TopPmd danmu = new TopPmd(this, PhoneUtils.getScreenWidth(this),
				(int) -paint.measureText(text));
		danmu.setTextSize(15);
		danmu.setPadding(10, 5, 10, 5);
		danmu.setText(text);
		danmu.setTextColor(Color.parseColor("#fffa7a"));
		danmu.setOnAnimationEndListener(new TopPmd.OnAnimationEndListener() {
			@Override
			public void clearPosition() {

			}

			@Override
			public void animationEnd() {
				container.removeView(danmu);
				setPmd(danmu.getText().toString());
			}

		});
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			danmu.setGravity(Gravity.CENTER);
			if (danmu.getParent() == null) {
				container.addView(danmu, lp);
				danmu.send();
			}
	}
	/**
	 * 弹幕
	 */
	private void initpaintPmdView() {
		// TODO Auto-generated method stub
		TextView textView = new TextView(this);
		textView.setTextSize(15);
		textView.setPadding(10, 3, 10, 3);
		paint = textView.getPaint();
		setPmd("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
	}
}
