package com.zx.wfm.ui;

import java.io.File;
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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zx.wfm.R;

public class WebActivity extends Activity {

	private WebView webview;
	private ProgressDialog mDialog;
	private Context mContext;
	private ImageButton backBtn = null;
	private Handler mHandler = new Handler();
	private RelativeLayout topbar = null;

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.web_activity);
		mContext = this;
		SharedPreferences share = PreferenceManager
				.getDefaultSharedPreferences(mContext);

		backBtn = (ImageButton) findViewById(R.id.web_back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mDialog = new ProgressDialog(mContext);
		webview = (WebView) findViewById(R.id.webview);
		webview.setVerticalScrollBarEnabled(false);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.setDownloadListener(new WebViewDownLoadListener());
		webview.setWebViewClient(new WeweWebViewClient());
		webview.setWebChromeClient(new MyWebChromeClient());

		WebSettings setting = webview.getSettings();
		setting.setJavaScriptEnabled(true);
		String url = null;
		String key = getIntent().getStringExtra("key");
		String postDate = getIntent().getStringExtra("param");

		// -------------------------------------------------------------------------

			webview.loadUrl(url);


	}

	@Override
	protected void onResume() {
		super.onResume();
		// WebView.enablePlatformNotifications();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// WebView.disablePlatformNotifications();
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
	}

	private class WeweWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			return super.shouldOverrideUrlLoading(view, url);
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
	}

}
