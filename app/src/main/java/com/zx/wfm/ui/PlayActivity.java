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
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;
import com.zx.wfm.R;
import com.zx.wfm.bean.Moviebean;
import com.zx.wfm.factory.NoticeMF;
import com.zx.wfm.factory.VerticalMF;
import com.zx.wfm.ui.adapters.BaseRecycleViewAdapter;
import com.zx.wfm.ui.adapters.MovieItemAdapter;
import com.zx.wfm.ui.view.ItemDecoration;
import com.zx.wfm.utils.AnimUtils;
import com.zx.wfm.utils.Constants;
import com.zx.wfm.utils.DialogUtils;
import com.zx.wfm.utils.ToastUtil;
import com.zx.wfm.utils.UKutils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class PlayActivity extends BaseActivity implements BaseRecycleViewAdapter.OnItemClickListener, OnMenuItemClickListener, OnMenuItemLongClickListener {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.returnBtn)
    ImageView returnBtn;
    @BindView(R.id.title)
    TextView titleTv;
    @BindView(R.id.midView)
    FrameLayout midView;
    @BindView(R.id.midContent)
    RelativeLayout midContent;
    @BindView(R.id.rightTextView)
    ImageView rightTextView;
    @BindView(R.id.rightView)
    FrameLayout rightView;
    @BindView(R.id.toolbar)
    FrameLayout toobar;
    @BindView(R.id.marqueeView_left)
    MarqueeView marqueeViewLeft;
    @BindView(R.id.marqueeView_right)
    MarqueeView marqueeViewRight;
    @BindView(R.id.right_layout)
    LinearLayout rightLayout;
    @BindView(R.id.right_bottom_view)
    TextView refreshbtn;
    @BindView(R.id.fresh_video)
    Button freshVideo;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.id_framelayout2)
    FrameLayout idFramelayout2;
    @BindView(R.id.video_name_tv)
    TextView videoName;
    @BindView(R.id.video_items)
    TextView itemsTv;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.id_linearlayout2)
    LinearLayout idLinearlayout2;
    @BindView(R.id.id_drawerlayout2)
    DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentMannager;
    private Context mContext;
    private View customView;
    String home = "http://player.youku.com/embed/XMTg4NTkzNDg4";
    //	@BindViewView(R.id.loading_img)
    protected ImageView framImg;
    private Context context;
    private String[] hostStr;
    private int index = 0;
    private long firstTouchBackBt;
    private Paint paint;
    private Handler mHandler = new Handler();
    private boolean isAlph = false;
    private List<Moviebean> beans;

    MovieItemAdapter movieItemAdapter;
    private boolean isOpen = false;

    private PopupWindow mPopupWindow;
    FloatingActionMenu rightLowerMenu;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private boolean isShowMenu = false;
    List<MenuObject> menuList;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        ButterKnife.bind(this);
        toobar.getBackground().setAlpha(150);
        fragmentManager = getSupportFragmentManager();
        mContext = this;
        mFragmentMannager = getSupportFragmentManager();
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
                isOpen = false;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isOpen = true;
            }
        });
        initWeb();
        initMenuFragment();
        rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    second = 0;
                    mMenuDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
            }
        });
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO Auto-generated method stub
        if (webview != null)
            webview.saveState(outState);
    }

    private boolean isRuning = false;
    private int second=0;
    Runnable gotoMainRunable = new Runnable() {
        @Override
        public void run() {
//			Log.e("toolbar",second+"");
            if (second >= 5 * 1000) {
                isRuning = false;
                mHandler.removeCallbacks(null);
                toobar.setVisibility(View.GONE);
            } else if (toobar.getVisibility() == View.VISIBLE) {
                isRuning = true;
                second += 1 * 1000;
                mHandler.postDelayed(gotoMainRunable, 1 * 1000);
            }
        }
    };

    private void initMenuFragment() {
        menuList = getMenuObjects();
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(menuList);
        menuParams.setClosableOutside(true);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
        mHandler.postDelayed(gotoMainRunable, 10);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.mipmap.ic_action_new_light);

        MenuObject next = new MenuObject("下一集");
        next.setResource(R.mipmap.next);


        MenuObject refrsh = new MenuObject("刷新");
        refrsh.setResource(R.mipmap.icon_yuan);

        MenuObject last = new MenuObject("上一集");
        last.setResource(R.mipmap.last);

        MenuObject right = new MenuObject("选集");
        right.setResource(R.mipmap.right);
        menuObjects.add(next);
        menuObjects.add(refrsh);
        menuObjects.add(last);
        menuObjects.add(right);
        return menuObjects;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webview != null)
            webview.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webview != null) {
            webview.onResume();
        }
    }

    @SuppressLint("JavascriptInterface")
    private void initWeb() {
//		initFloatbtn();
        beans = getIntent().getParcelableArrayListExtra(Constants.VIDEO_OBJ_LIST);
        int pos = getIntent().getIntExtra(Constants.VIDEO_OBJ_POS, 0);

        if (beans == null || beans.size() == 0) {
            return;
        }

        movieItemAdapter = new MovieItemAdapter(this, beans, R.layout.video_num_item_layout);
        movieItemAdapter.setCurrentPosition(pos);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.addItemDecoration(new ItemDecoration());
        mRecyclerView.setAdapter(movieItemAdapter);
        movieItemAdapter.setOnItemClickListener(this);
        home = beans.get(pos).getItemUrl();
        if (beans.size() == 1) {
            titleTv.setText(beans.get(pos).getVideoName());
        } else {
            titleTv.setText(beans.get(pos).getVideoName() + "(" + (pos + 1) + "集)");
        }
        videoName.setText(beans.get(pos).getVideoName());
        itemsTv.setText("(共" + beans.size() + "集)");
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
//        mDialog.setMessage(getString(R.string.web_loading));
//        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (!isShowIng()) {
            onLoadingViewShow(Constants.Request_Code.LOAD_VIDEO);
        }
        // -------------------------------------------------------------------------
        webview.addJavascriptInterface(new Object() {
            public void click() {
                Log.i("点击", "!!!!!!!!!!!");

            }
        }, "resize");
        refreshbtn.bringToFront();
        mHandler.postDelayed(freshbtnRunable, 20 * 1000);
        initMaqueeView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        second = 0;
        if (toobar.getVisibility() == View.GONE) {
            toobar.setVisibility(View.VISIBLE);
            mHandler.postDelayed(gotoMainRunable, 2000);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initFloatbtn() {
        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageResource(R.mipmap.ic_action_new_light);
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNew)
                .setLayoutParams(new FloatingActionButton.LayoutParams(150, 150))
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this).setLayoutParams(new FrameLayout.LayoutParams(100, 100));
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

        SubActionButton nextImg = rLSubBuilder.setContentView(rlIcon1).build();
        SubActionButton refreshimg = rLSubBuilder.setContentView(rlIcon2).build();
        SubActionButton pauseImg = rLSubBuilder.setContentView(rlIcon3).build();
        SubActionButton lastImg = rLSubBuilder.setContentView(rlIcon4).build();
        SubActionButton rightImg = rLSubBuilder.setContentView(rlIcon5).build();
        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        rightLowerMenu = new FloatingActionMenu.Builder(this)
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
                if (!isOpen) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    v.setRotation(0);
                    PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 180);
                    ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(v, pvhR);
                    animation.setDuration(100);
                    animation.start();
                } else {
                    mDrawerLayout.closeDrawers();
                    v.setRotation(180);
                    PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                    ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(v, pvhR);
                    animation.setDuration(100);
                    animation.start();
                }
            }
        });
        nextImg.setOnClickListener(new ActionClickListenr() {

            @Override
            public void myClick(View v) {
                noticItem("是否加载下一集？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightLowerMenu.close(true);
                        if (movieItemAdapter != null && movieItemAdapter.getmList().size() != 0) {
                            if (movieItemAdapter.getCurrentPosition() + 1 < movieItemAdapter.getmList().size())
                                OnItemClickListener(v, movieItemAdapter.getCurrentPosition() + 1);
                            else {
                                ToastUtil.showToastShort(PlayActivity.this, "没有下一集了");
                            }
                        }
                    }
                });
            }
        });
        lastImg.setOnClickListener(new ActionClickListenr() {
            @Override
            public void myClick(View v) {
                noticItem("是否加载上一集？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightLowerMenu.close(true);
                        if (movieItemAdapter != null && movieItemAdapter.getmList().size() != 0) {
                            if (movieItemAdapter.getCurrentPosition() > 0)
                                OnItemClickListener(v, movieItemAdapter.getCurrentPosition() - 1);
                            else {
                                ToastUtil.showToastShort(PlayActivity.this, "已经是第一集了");
                            }
                        }
                    }
                });

            }
        });
        refreshimg.setOnClickListener(new ActionClickListenr() {
            @Override
            public void myClick(View v) {
                noticItem("是否刷新？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightLowerMenu.close(true);
                        if (!isShowIng()) {
                            onLoadingViewShow(Constants.Request_Code.LOAD_VIDEO);
                        }
                    }
                });
            }
        });
        pauseImg.setOnClickListener(new ActionClickListenr() {


            @Override
            public void myClick(View v) {
                rightLowerMenu.close(true);
                if (webview != null) {
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

    private void noticItem(String notic, final View.OnClickListener click) {
        mPopupWindow = DialogUtils.createPopupWindow(PlayActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.certain:
                        click.onClick(v);
                        mPopupWindow.dismiss();
                        break;
                    case R.id.cancel:
                        mPopupWindow.dismiss();
                        break;
                }
            }
        }, PlayActivity.this.getWindow().getDecorView(), notic, "取消", "确认");
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (rightLowerMenu != null) {
                    rightLowerMenu.close(true);
                }
            }
        });
    }

    Runnable getRealUrl = new Runnable() {
        @Override
        public void run() {
            Log.e("播放地址", home);
            Document doc = UKutils.getDoc(home);
            if (doc == null) {
                return;
            }
            Elements elements = doc.getElementsByClass("item");

            if (elements != null && elements.size() != 0) {
                Element element = elements.get(0);
                final Element ele = element.getElementById("link4");
                String str = ele.attr("value");
//				final	String url=str.replace("height=498","height="+(PhoneUtils.getScreenHight(PlayActivity.this))).replace("width=510","width="+ PhoneUtils.getScreenWidth(PlayActivity.this));
                int start = str.indexOf("src");
                int end = str.indexOf("frameborder");
                final String url = str.substring(start + 5, end - 2);
//				for(Element element1:elements){

//				}

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("播放地址", "播放地址：" + url);
                        webview.loadUrl(url);

//						webview.loadData(url,"text/html","utf-8");
                    }
                }, 0);
            }
        }
    };

    @Override
    public void OnItemClickListener(View view, int position) {
        movieItemAdapter.setCurrentPosition(position);
        mDrawerLayout.closeDrawers();
        home = beans.get(position).getItemUrl();
            if (!isShowIng()) {
                if (beans.size() == 1) {
                    titleTv.setText(beans.get(position).getVideoName());
                } else {
                    ToastUtil.showToastShort(this, "第" + (position + 1) + "集");
                    titleTv.setText(beans.get(position).getVideoName() + "(" + (position + 1) + "集)");
                }
                onLoadingViewShow(Constants.Request_Code.LOAD_VIDEO);

            }


    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        second = 0;
        if (!isRuning) {
            mHandler.postDelayed(gotoMainRunable, 10);
        }
        switch (position) {
            case 1:
                // 刷新
                noticItem("是否刷新？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if(!isShowIng()){
                           onLoadingViewShow(Constants.Request_Code.LOAD_VIDEO);
                       }
                    }
                });
                break;
            case 0:
                //下一集
                noticItem("是否加载下一集？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (movieItemAdapter != null && movieItemAdapter.getmList().size() != 0) {
                            if (movieItemAdapter.getCurrentPosition() + 1 < movieItemAdapter.getmList().size())
                                OnItemClickListener(v, movieItemAdapter.getCurrentPosition() + 1);
                            else {
                                ToastUtil.showToastShort(PlayActivity.this, "没有下一集了");
                            }
                        }
                    }
                });
                break;
            case 2:
                // 上一集
                noticItem("是否加载上一集？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (movieItemAdapter != null && movieItemAdapter.getmList().size() != 0) {
                            if (movieItemAdapter.getCurrentPosition() > 0)
                                OnItemClickListener(v, movieItemAdapter.getCurrentPosition() - 1);
                            else {
                                ToastUtil.showToastShort(PlayActivity.this, "已经是第一集了");
                            }
                        }
                    }
                });
                break;
            case 3:
                //选集
                if (!isOpen) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    clickedView.setRotation(0);
                    PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 180);
                    ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(clickedView, pvhR);
                    animation.setDuration(100);
                    animation.start();
                } else {
                    mDrawerLayout.closeDrawers();
                    clickedView.setRotation(180);
                    PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                    ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(clickedView, pvhR);
                    animation.setDuration(100);
                    animation.start();
                }
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

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
            if (Build.VERSION.SDK_INT < 23) {
                index++;
                url = url.toLowerCase();
                if ((index % 7 == 0 || index % 11 == 0) && !url.equals("http://player.youku.com/h5player/img/xplayerv4.png")) {
                    Log.i("广告", index + "  url" + url);
                    isShowMenu = true;
                    return new WebResourceResponse(null, null, null);
                }
                isShowMenu = false;
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
            LoadCompelete();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
           LoadCompelete();
        }

    }

    @Override
    protected void onStartNetWork(int netCode) {
        super.onStartNetWork(netCode);
        switch (netCode){
            case Constants.Request_Code.LOAD_VIDEO:
                    new Thread(getRealUrl).start();
                break;
        }
    }

    Runnable freshbtnRunable = new Runnable() {
        @Override
        public void run() {
            if (isAlph) {
                refreshbtn.setAlpha(0f);
                animate(refreshbtn).setDuration(2000).alpha(1f);
                isAlph = false;
            } else {
                isAlph = true;
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
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAtLocation(PlayActivity.this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                    return;
                }
                mPopupWindow = DialogUtils.createPopupWindow(PlayActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.certain:
                               if(isShowIng()){
                                   onLoadingViewShow(Constants.Request_Code.LOAD_VIDEO);
                               }
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
        final List<String> datas = Arrays.asList("左侧拉出可以选集", "《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
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

        @SuppressLint("SourceLockedOrientationActivity")
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
            Log.i("进度", "进度" + newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }


    @Override
    public void onBackPressed() {
        if (isOpen) {
            mDrawerLayout.closeDrawers();
            return;
        }
        noticItem("退出播放", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.certain:
                        mPopupWindow.dismiss();
                        webview.destroy();
                        PlayActivity.super.onBackPressed();
                        break;
                    case R.id.cancel:
                        mPopupWindow.dismiss();
                        break;
                }
            }
        });
    }

    public abstract class ActionClickListenr implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (isOpen) {
                mDrawerLayout.closeDrawers();
            }
            myClick(v);
        }

        public abstract void myClick(View v);
    }

}
