package com.zx.wfm.ui;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.zx.wfm.R;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.factory.NoticeMF;
import com.zx.wfm.factory.VerticalMF;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieItemAdapter;
import com.zx.wfm.ui.view.ItemDecoration;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.DialogUtils;
import com.zx.wfm.utils.ToastUtil;
import com.zx.wfm.utils.UKutils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class PlayActivity extends BaseActivity implements BaseRecycleViewAdapter.OnItemClickListener {
    @InjectView(R.id.id_drawerlayout2)
	protected DrawerLayout mDrawerLayout;
	private FragmentManager mFragmentMannager;
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

	@InjectView(R.id.marqueeView)
	MarqueeView marqueeView;
	@InjectView(R.id.marqueeView_left)
	MarqueeView marqueeViewLeft;
	@InjectView(R.id.marqueeView_right)
	MarqueeView marqueeViewRight;
	private Paint paint;
	private Handler mHandler=new Handler();
	private boolean isAlph=false;
	private List<Moviebean> beans;
	@InjectView(R.id.swipe_target)
	RecyclerView mRecyclerView;
	MovieItemAdapter movieItemAdapter;
	private  boolean isOpen=false;
	@InjectView(R.id.video_name_tv)
	protected  TextView videoName;
	@InjectView(R.id.video_items)
	TextView itemsTv;
	private PopupWindow mPopupWindow;


	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_activity);
		mContext = this;
		mFragmentMannager=getSupportFragmentManager();
		mDrawerLayout.closeDrawers();
		mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {

			}

			@Override
			public void onDrawerOpened(View drawerView) {

			}

			@Override
			public void onDrawerClosed(View drawerView) {

			}

			@Override
			public void onDrawerStateChanged(int newState) {

			}
		});

		mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				isOpen=false;
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				isOpen=true;
			}
		});
		initWeb();

	}

	@Override
	protected void onPause() {
		super.onPause();
		webview.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		webview.onResume();
	}

	@SuppressLint("JavascriptInterface")
	private void initWeb() {
		initFloatbtn();
		beans= getIntent().getParcelableArrayListExtra(Constants.VIDEO_OBJ_LIST);
		 int pos=getIntent().getIntExtra(Constants.VIDEO_OBJ_POS,0);

		if(beans==null||beans.size()==0){
			return;
		}

		movieItemAdapter=new MovieItemAdapter(this,beans,R.layout.video_num_item_layout);
		movieItemAdapter.setCurrentPosition(pos);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
		mRecyclerView.addItemDecoration(new ItemDecoration());
		mRecyclerView.setAdapter(movieItemAdapter);
		movieItemAdapter.setOnItemClickListener(this);
		home=beans.get(pos).getItemUrl();
		videoName.setText(beans.get(pos).getVideoName());
		itemsTv.setText("(共"+beans.size()+"集)");

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

	private void initFloatbtn() {
		final ImageView fabIconNew = new ImageView(this);
		fabIconNew.setImageResource(R.mipmap.ic_action_new_light);
		final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
				.setContentView(fabIconNew)
				.setLayoutParams(new FloatingActionButton.LayoutParams(150,150))
				.build();

		SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this).setLayoutParams(new FrameLayout.LayoutParams(100,100));
		ImageView rlIcon1 = new ImageView(this);
		ImageView rlIcon2 = new ImageView(this);
		ImageView rlIcon3 = new ImageView(this);
		ImageView rlIcon4 = new ImageView(this);
		ImageView rlIcon5 = new ImageView(this);

		rlIcon1.setBackgroundResource(R.mipmap.next);
		rlIcon2.setBackgroundResource(R.mipmap.icon_yuan);
		rlIcon3.setBackgroundResource(R.mipmap.pause);
		rlIcon4.setBackgroundResource(R.mipmap.last);
		rlIcon5.setBackgroundResource(R.mipmap.right);

	SubActionButton	nextImg=rLSubBuilder.setContentView(rlIcon1).build();
		SubActionButton	refreshimg=rLSubBuilder.setContentView(rlIcon2).build();
		SubActionButton pauseImg=rLSubBuilder.setContentView(rlIcon3).build();
		SubActionButton lastImg=rLSubBuilder.setContentView(rlIcon4).build();
		SubActionButton rightImg=rLSubBuilder.setContentView(rlIcon5).build();
		// Build the menu with default options: light theme, 90 degrees, 72dp radius.
		// Set 4 default SubActionButtons
		final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
				.addSubActionView(nextImg)
				.addSubActionView(refreshimg)
//				.addSubActionView(pauseImg)
				.addSubActionView(lastImg)
				.addSubActionView(rightImg)
				.attachTo(rightLowerButton)
				.build();
		rightImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isOpen) {
					mDrawerLayout.openDrawer(GravityCompat.START);
					v.setRotation(0);
					PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 180);
					ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(v, pvhR);
					animation.setDuration(100);
					animation.start();
				}else {
					mDrawerLayout.closeDrawers();
					v.setRotation(180);
					PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION,0);
					ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(v, pvhR);
					animation.setDuration(100);
					animation.start();
				}
			}
		});
		nextImg.setOnClickListener(new ActionClickListenr() {

			@Override
			public void myClick(View v) {
				rightLowerMenu.close(true);
				if(movieItemAdapter!=null&&movieItemAdapter.getmList().size()!=0){
					if(movieItemAdapter.getCurrentPosition()<movieItemAdapter.getmList().size())
						OnItemClickListener(v,movieItemAdapter.getCurrentPosition()+1);
					else {
						ToastUtil.showToastShort(PlayActivity.this,"没有下一集了");
					}
				}
			}
		});
		lastImg.setOnClickListener(new ActionClickListenr() {
			@Override
			public void myClick(View v) {
				rightLowerMenu.close(true);
				if(movieItemAdapter!=null&&movieItemAdapter.getmList().size()!=0){
					if(movieItemAdapter.getCurrentPosition()>0)
						OnItemClickListener(v,movieItemAdapter.getCurrentPosition()-1);
					else {
						ToastUtil.showToastShort(PlayActivity.this,"已经是第一集了");
					}
				}
			}
		} );
		refreshimg.setOnClickListener(new ActionClickListenr() {
			@Override
			public void myClick(View v) {
				rightLowerMenu.close(true);
				if(mDialog!=null){
					mDialog.show();
				}
			}
		});
		pauseImg.setOnClickListener(new ActionClickListenr() {


			@Override
			public void myClick(View v) {
				rightLowerMenu.close(true);
				if(webview!=null){
					webview.onPause();
				}
			}
		});

		// Listen menu open and close events to animate the button content view
		rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
			@Override
			public void onMenuOpened(FloatingActionMenu menu) {
				// Rotate the icon of rightLowerButton 45 degrees clockwise
//				fabIconNew.setRotation(0);
//				PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
//				ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
//				animation.start();

			}

			@Override
			public void onMenuClosed(FloatingActionMenu menu) {
				// Rotate the icon of rightLowerButton 45 degrees counter-clockwise
//				fabIconNew.setRotation(45);
//				PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
//				ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
//				animation.start();

			}

		});

	}

	Runnable getRealUrl=new Runnable() {
		@Override
		public void run() {
			Document doc= UKutils.getDoc(home);
			if(doc==null){
				return;
			}
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

	@Override
	public void OnItemClickListener(View view, int position) {
		movieItemAdapter.setCurrentPosition(position);
		mDrawerLayout.closeDrawers();
		home=beans.get(position).getItemUrl();
		if(mDialog!=null) {
			ToastUtil.showToastShort(this,"第"+(position+1)+"集");
			mDialog.show();
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
			// http://www.soku.com/search_video/q_电影?f=1&kb=04114020kv41000__电影
		}
	};

	/**
	 * 初始化 介绍 文字。
	 */
	private void initMaqueeView() {
		final List<String> fresh = Arrays.asList("加载失败?", "有广告?");
		MarqueeFactory<TextView, String> marqueeFactory = new NoticeMF(mContext);
		marqueeView.setMarqueeFactory(marqueeFactory);
		marqueeView.setAnimDuration(3000);
		marqueeView.setInterval(6000);
		marqueeView.startFlipping();
		marqueeFactory.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
			@Override
			public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
				if(mPopupWindow!=null&&!mPopupWindow.isShowing()){
					mPopupWindow.showAtLocation( PlayActivity.this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
					return;
				}
			mPopupWindow=DialogUtils.createPopupWindow(PlayActivity.this, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						switch (v.getId()){
							case R.id.certain:
								mDialog.show();
								break;
							case R.id.cancel:
								break;
						}
						mPopupWindow.dismiss();
					}
				}, PlayActivity.this.getWindow().getDecorView(), "是否重新加载视频？", "取消", "确认");
			}
		});
		marqueeFactory.setData(fresh);
		final List<String> datas = Arrays.asList("左侧拉出可以选集","《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
		MarqueeFactory<TextView, String> marqueeFactoryleft = new VerticalMF(mContext);
		marqueeViewLeft.setMarqueeFactory(marqueeFactoryleft);
		marqueeViewLeft.setAnimDuration(3000);
		marqueeViewLeft.setInterval(6000);
		marqueeViewLeft.startFlipping();
		marqueeFactoryleft.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
			@Override
			public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
				Toast.makeText(mContext, holder.data, Toast.LENGTH_SHORT).show();
			}
		});
		marqueeFactoryleft.setData(datas);

		MarqueeFactory<TextView, String> marqueeFactoryRight = new VerticalMF(mContext);
		marqueeViewRight.setMarqueeFactory(marqueeFactoryRight);
		marqueeViewRight.startFlipping();
		marqueeViewRight.setAnimDuration(3000);
		marqueeViewRight.setInterval(6000);
		marqueeFactoryRight.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
			@Override
			public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
				Toast.makeText(mContext, holder.data, Toast.LENGTH_SHORT).show();
			}
		});
		marqueeFactoryRight.setData(datas);

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
	public void onBackPressed() {
		if(isOpen){
			mDrawerLayout.closeDrawers();
			return;
      	}
			if (!hasPress) {
				firstTouchBackBt = System.currentTimeMillis();
				ToastUtil.showToastLong(this, "再次点击退出播放");
				hasPress = true;
			} else {
				if ((System.currentTimeMillis() - firstTouchBackBt) < 2000) {
					hasPress = false;
					webview.destroy();
					super.onBackPressed();
				} else {
					hasPress = true;
					firstTouchBackBt = System.currentTimeMillis();
					ToastUtil.showToastLong(this, "再次点击退出播放");
				}
			}
	}
	public abstract class ActionClickListenr implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if(isOpen){
				mDrawerLayout.closeDrawers();
			}
			myClick(v);
		}
		public abstract void myClick(View v);
	}

}
