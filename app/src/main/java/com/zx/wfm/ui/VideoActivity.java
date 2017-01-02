//package com.zx.wfm.ui;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.jivesoftware.smackx.jingle.JingleActionEnum;
//import org.jivesoftware.smackx.jingle.media.PayloadType;
//import org.json.JSONException;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.yunlian.wewesdk.sdkCore;
//import com.yunlian.wewesdk.sdkMedia;
//import com.yunlian.yudao.R;
//import com.yunlian.yudao.db.daoImpl.RecordDBHelper;
//import com.yunlian.yudao.db.daoImpl.TelegramDBHelper;
//import com.yunlian.yudao.db.daoImpl.UserDBHelper;
//import com.yunlian.yudao.entity.JingleData;
//import com.yunlian.yudao.entity.Recordbean;
//import com.yunlian.yudao.entity.ResultBean;
//import com.yunlian.yudao.entity.SongliBean;
//import com.yunlian.yudao.entity.Telegrambean;
//import com.yunlian.yudao.entity.Telegrambean;
//import com.yunlian.yudao.entity.TuyaBean;
//import com.yunlian.yudao.entity.TuyaBean2;
//import com.yunlian.yudao.entity.UserData;
//import com.yunlian.yudao.model.BaseApplicasion;
//import com.yunlian.yudao.model.UserModel;
//import com.yunlian.yudao.services.PlayerService;
//import com.yunlian.yudao.ui.adapter.SongliAdapter;
//import com.yunlian.yudao.ui.adapter.SongliAdapter1;
//import com.yunlian.yudao.ui.adapter.TuyaAdapter;
//import com.yunlian.yudao.ui.adapter.TuyaAdapter2;
//import com.yunlian.yudao.ui.camera.CameraAty;
//import com.yunlian.yudao.util.BitmapUtil;
//import com.yunlian.yudao.util.Constants;
//import com.yunlian.yudao.util.DialogUtil;
//import com.yunlian.yudao.util.FileUtils;
//import com.yunlian.yudao.util.LogUtil;
//import com.yunlian.yudao.util.PhoneUitls;
//import com.yunlian.yudao.util.PictureUtil;
//import com.yunlian.yudao.util.StringUtills;
//import com.yunlian.yudao.util.ToastUtils;
//import com.yunlian.yudao.util.Tools;
//import com.yunlian.yudao.util.imageCach.cach.ImageCacheManager;
//import com.yunlian.yudao.util.imageCach.cach.LoaderOptions;
//import com.yunlian.yudao.widget.HomeViewPager;
//import com.yunlian.yudao.widget.HorizontialListView;
//import com.yunlian.yudao.widget.NoScroolGridView;
//import com.yunlian.yudao.widget.OnResultCallback;
//import com.yunlian.yudao.widget.RoundImageView;
//import com.yunlian.yudao.widget.RoundImg;
//import com.yunlian.yudao.widget.TagViewPager;
//import com.yunlian.yudao.widget.TagViewPager.OnGetView;
//import com.yunlian.yudao.widget.doodle.Action;
//import com.yunlian.yudao.widget.doodle.Doodle;
//import com.yunlian.yudao.widget.doodle.DoodleData;
//import com.yunlian.yudao.widget.doodle.DoodlePoint;
//import com.yunlian.yudao.widget.paomadeng.TopPmd;
//import com.yunlian.yudao.widget.paomadeng.TopPmdView;
//import com.yunlian.yudao.xmpp.NetTaskForResult;
//import com.yunlian.yudao.xmpp.XMPPManager;
//import com.yunlian.yudao.xmpp.YDMessageManager;
//import com.yunlian.yudao.xmpp.Listener.InterfaceMethod;
//import com.yunlian.yudao.xmpp.jingle.JingleManager;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.KeyguardManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.PixelFormat;
//import android.graphics.PorterDuff.Mode;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.drawable.BitmapDrawable;
//import android.hardware.Camera;
//import android.hardware.Camera.CameraInfo;
//import android.media.AudioManager;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Message;
//import android.os.PowerManager;
//import android.os.SystemClock;
//import android.preference.PreferenceManager;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.text.Editable;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.TextWatcher;
//import android.text.style.ForegroundColorSpan;
//import android.util.Base64;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.View.OnKeyListener;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.Animation.AnimationListener;
//import android.view.animation.AnimationUtils;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Chronometer;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.PopupWindow.OnDismissListener;
//import android.widget.RelativeLayout.LayoutParams;
//import android.widget.SeekBar;
//import android.widget.SeekBar.OnSeekBarChangeListener;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class VideoActivity extends BaseActivity implements Doodle.GestrueAction {
//	private String TAG = VideoActivity.class.getSimpleName();
//	private RelativeLayout videoRootView;
//	private SurfaceView localVideoView;
//	private SurfaceView remoteVideoView;
//	private View presentView, titleDanmuView;
//	private ImageView tuya_xiangpi_img;
//	private Doodle mDoodle;
//	private View view_doodle, doodle_rootView;
//	// private PopupWindow mPop;
//	private TextView songli_yd;
//	private PopupWindow doodle_Pop;
//	RelativeLayout tuya_but1, tuya_but3;
//	private int tuya_choise = 0;
//	private ImageView changeCameroBtn;
//	private TagViewPager viewPager;
//	private Animation hideAction, showAction, titleshow, slid_out, slid_in,
//	headAction, slid_out_fromTop, slid_in_from_top;
//	private boolean isCallOut = false;
//	private boolean isLocalBig = false;
//	boolean menuShowed = true;
//	private View agreChanBtn, refuChanBtn;
//	private ImageView headImg, changeVideobtn, cancelImg;
//	private View callOutLayout, randomLayout, callInLayout, cendHeadLayout;
//	// title_changLayout;
//	private JingleData jingleData, changeJingleData;
//	private mMessageReceiver mMessageReceiver;
//	private boolean isagree = false;
//	private SharedPreferences preferences;
//	private View mainView, bottomView, bHungUpBtn, agreebtn, refusebtn,
//	callingLayout, titleHeadLayout;
//	private PopupWindow centerWindow;
//	private boolean isAttched = false;
//	private Chronometer chronometer;
//	private UserData bean, mybean;
//	private int camaraTag = CameraInfo.CAMERA_FACING_FRONT;
//	private TextView nickTv;
//	private TextView gift_much, costTv;
//	private ImageButton addfriendbtn;
//	private ImageButton guanzhuButton;
//	private long callTime;
//	private String howLong = "未接来电";
//	private Telegrambean telegrambeanq;
//	CountDownTimer countDownTimer;
//	private boolean isCharge = false;
//	// 1:随机 2：偶遇
//	private int chargeCallingType;
//	private boolean mute = false;
//	private ImageView jybtn, waifbtn, doolelayout, doodleImg;
//	private AudioManager audioManager;
//	PowerManager.WakeLock mWakeLock;
//	private int videoPayloadType, audiopayloadType;
//	List<SongliBean> present1 = new ArrayList<SongliBean>();
//	List<SongliBean> present2 = new ArrayList<SongliBean>();
//	List<SongliBean> present3 = new ArrayList<SongliBean>();
////	List<SongliBean> present4 = new ArrayList<SongliBean>();
//	SongliBean liwu;
//	public static MyHandler handler;
//	Editor editor;
//	public PopupWindow songliPop;
//	private RelativeLayout container, centerContainer;
//	private Paint paint, centerPaint;
//	private int sWidth, sHight;
//	private int current = 0;
//	private HashMap<Integer, Boolean> sendPosition = new HashMap<Integer, Boolean>();
//	private List<TopPmdView> pmdList = new ArrayList<TopPmdView>();
//	private boolean isLooping = false;
//	private boolean isTopOk = false;
//	private int danmuHeight;
//	private int totalCount = 4;
//	private int cenTerDanmuSize = 15;
//	private DoodleData doodleData;
//	private String noticeStr;
//	private RoundImageView titleHeadImg;
//	private int mySize = 5;
//	private String myColor;
//	private boolean isEraser = false;
//	private RoundImg strokImg;
//	private View titleAlphaImg, random_sure, random_cacel;
//	// 涂鸦板比列
//	private float doodleRate;
//	/**
//	 * 涂鸦
//	 */
//	ArrayList<View> pageViews = new ArrayList<View>();
//	HomeViewPager pager;
//	View view1, headbgLayout, callholdLayout, hbLayout;
//	View view2;
//	private SeekBar bar;
//	TuyaAdapter adapter_doodle;
//	TuyaAdapter2 adapter_doodle2;
//	List<TuyaBean> tuyas;
//	List<TuyaBean2> tuyas2;
//	String[] colors;
//	private float remoteWidth;
//	private float remoteHight;
//	private float realscal;
//	private String nickName;
//
//	private PopupWindow laheiPop;
//	private int oid;
//	private String costCall;
//	private Recordbean recordbean;
//	private String costText;
//	private TextView hbtitleTv;
//	private PopupWindow reportPop;
//	private TextView titleCostTv;
//	private View hbNoticeView;
//	private boolean isFullScreen = false;
//	private boolean isShowHb = false;
//
//	@Override
//	public void initWidget() {
//		// TODO Auto-generated method stub
//		// 保持屏幕常亮
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		mainView = getLayoutInflater().inflate(R.layout.video_calling_activity,
//				null);
//		setContentView(mainView);
//		sWidth = PhoneUitls.getScreenWidth(this);
//		sHight = PhoneUitls.getScreenHight(this);
//		BaseApplicasion.iscallIng = true;
//		handler = new MyHandler();
//		mybean = UserModel.getMyself(this);
//		callTime = Tools.getgenerateTime();
//		Intent intent = getIntent();
//		if (null != intent) {
//			BaseApplicasion.isVideo = intent.getBooleanExtra(Constants.ISVIDEO,
//					false);
//			isCallOut = intent.getBooleanExtra(Constants.CALL_OUT, false);
//			jingleData = (JingleData) intent
//					.getSerializableExtra(Constants.JINGLLDATA);
//			isCharge = true;
//			chargeCallingType = intent.getIntExtra("chargeCallingType", 2);
//			isShowHb = intent.getBooleanExtra("isShow", false);
//			bean = (UserData) intent.getSerializableExtra("user");
//			costText = intent.getStringExtra("expenses");
//			LogUtil.i(TAG, "打出=：" + isCallOut + "视频=："
//					+ BaseApplicasion.isVideo);
//			if (jingleData != null) {
//				getPayLoadType(jingleData);
//
//			}
//		}
//		// 设置提示语
//		noticeMe(isCallOut, BaseApplicasion.isVideo);
//		initView();
//		initHeadAnimation();
//	}
//
//	private void noticeMe(boolean isCallout, boolean isVideo) {
//		// TODO Auto-generated method stub
//		if (isCallout) {
//			if (isVideo) {
//				noticeStr = "  正在发起视频请求";
//			} else {
//				noticeStr = "正在呼叫 ";
//			}
//		} else {
//			if (isVideo) {
//				noticeStr = " 请求视频...";
//			} else {
//				noticeStr = " 请求通话...";
//			}
//		}
//	}
//
//	private void initdoodleView() {
//		// 涂鸦布局
//		initDoodleWindow();
//		doodle_rootView = findViewById(R.id.doodle_rootView);
//		doolelayout = (ImageView) findViewById(R.id.doodle_layout);
//		doolelayout.setBackgroundResource(R.drawable.huaban_1);
//		mDoodle = (Doodle) findViewById(R.id.doodle_surfaceview);
//		mDoodle.setBackGroundView(doolelayout);
//		mDoodle.setZOrderOnTop(true);
//		mDoodle.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//		mDoodle.setmGestrueAction(this);
//		// 设置涂鸦画笔
//		mDoodle.setPaint(getDefaultPaint());
//	}
//
//	private void getPayLoadType(JingleData jingleData) {
//		// TODO Auto-generated method stub
//		List<PayloadType> audio = jingleData.getAudioListType();
//		if (audio != null) {
//			for (int i = 0; i < audio.size(); i++) {
//				if (sdkCore.findCodec(audio.get(i).getId())) {
//					audiopayloadType = audio.get(i).getId();
//					LogUtil.i("audioType", audiopayloadType);
//
//					break;
//				}
//			}
//		}
//
//		List<PayloadType> video = jingleData.getVideoListType();
//		if (video != null) {
//			for (int i = 0; i < video.size(); i++) {
//				if (sdkCore.findCodec(video.get(i).getId())) {
//					videoPayloadType = video.get(i).getId();
//					LogUtil.i("audioType", videoPayloadType);
//					break;
//				}
//			}
//		}
//		LogUtil.i("audioType", audiopayloadType + "@@" + videoPayloadType);
//	}
//
//	/**
//	 * 解锁屏幕
//	 */
//	private void weakUp() {
//		// TODO Auto-generated method stub
//		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//
//		mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
//				| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
//		mWakeLock.acquire();
//
//		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
//		// 解锁
//		kl.disableKeyguard();
//		// 开始计时，
//		startMorentime();
//	}
//
//	private void getUserInfo(JingleData jingleData) {
//		// TODO Auto-generated method stub
//		UserModel.getuserBean(this, jingleData.getFrom().split("@")[0],
//				new OnResultCallback() {
//
//			@Override
//			public void onCallback(ResultBean bean) {
//				// TODO Auto-generated method stub
//				LogUtil.i("获取用户资料", bean.toString());
//				if (0 == bean.getErr()) {
//					UserData userFrom = (UserData) JSONObject
//							.parseArray(bean.getPara(), UserData.class)
//							.get(0);
//
//					ImageCacheManager.getInstance().displayRoundImage(
//							userFrom.getHead(), headImg,
//							LoaderOptions.getChatHeadOptions(0), 300);
//					ImageCacheManager.getInstance().displayRoundImage(
//							userFrom.getHead(), titleHeadImg,
//							LoaderOptions.getChatHeadOptions(0), 300);
//					// ImageCacheManager.getInstance().displayImage(
//					// userFrom.getHead(), titleHeadImg);
//					titleHeadImg.bringToFront();
//					nickName = userFrom.getNick();
//					nickTv.setText(userFrom.getNick() + noticeStr);
//				}
//			}
//		});
//
//	}
//
//	private void initBroadCastRecevier() {
//		mMessageReceiver = new mMessageReceiver();
//
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_ACCEPT_ACTION);
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_FAILED_ACTION);
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_SUCESS_ACTION);
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_TRANSPORT_ACTION);
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_CONTENT_ACCEPT_VIDEO_ACTION);
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_CONTENT_ACCEPT_AUDIO_ACTION);
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_SESION_TERMINATE);
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_CONTENT_ACCEPT_CHANGE_ACTION);
//		filter.addAction(Constants.DIALER_REQUEST_JINGGE_CONTENT_TRANSPORT_ACTION);
//		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//		filter.addAction(Constants.GET_PUBLIC_DANMU_MSG);
//		filter.addAction(Constants.GET_PRIVTAE_DANMU_MSG);
//		filter.addAction(Constants.DOODLE_CHANGE_BACKGROUND);
//		filter.addAction(Constants.DOODLE_PLAYING);
//		filter.addAction(Constants.DOODLE_DOWN);
//		filter.addAction(Constants.DOODLE_GO_FORWARD);
//		filter.addAction(Constants.DOODLE_CLEALER);
//		filter.addAction(Constants.DOODLE_RETURN);
//		filter.addAction("android.intent.action.HEADSET_PLUG");
//		registerReceiver(mMessageReceiver, filter);
//	}
//
//	private void initView() {
//		// TODO Auto-generated method stub
//		initTopPmdView();
//		initdoodleView();
//		hbNoticeView = findViewById(R.id.title_hb_notice_layout);
//		titleCostTv = (TextView) findViewById(R.id.hb_title_tv);
//		hbtitleTv = (TextView) findViewById(R.id.hb_title);
//		findViewById(R.id.title_right_img).setOnClickListener(this);
//		laheiPop = DialogUtil.creatLaheiPop(this, this);
//		costTv = (TextView) findViewById(R.id.video_cost_tv);
//
//		randomLayout = findViewById(R.id.video_random_avtivity_call_out_layout);
//		random_sure = findViewById(R.id.video_random_activity_sure_btn);
//		random_cacel = findViewById(R.id.media_random_activity_refuses_btn);
//		random_cacel.setOnClickListener(this);
//		random_sure.setOnClickListener(this);
//
//		hbLayout = findViewById(R.id.holding_hb_layout);
//		hbLayout.getBackground().setAlpha(125);
//		hbLayout.setOnClickListener(this);
//		headbgLayout = findViewById(R.id.head_bg_layout);
//		titleHeadLayout = findViewById(R.id.title_head_layout);
//		titleHeadLayout.setOnClickListener(this);
//		titleAlphaImg = findViewById(R.id.title_hight_alpha_img);
//		titleHeadImg = (RoundImageView) findViewById(R.id.audio_title_head_img);
//		cancelImg = (ImageView) findViewById(R.id.video_cancel_btn);
//		cancelImg.setOnClickListener(this);
//		callholdLayout = findViewById(R.id.call_hold_layout);
//		callholdLayout.getBackground().setAlpha(255);
//		callingLayout = findViewById(R.id.video_activity_calling_layout);
//		callingLayout.setBackgroundColor(Color.parseColor("#000000"));
//		titleDanmuView = findViewById(R.id.top_danmu_layout);
//		titleDanmuView.getBackground().setAlpha(100);
//		doodleImg = (ImageView) findViewById(R.id.doodle_img);
//		doodleImg.setOnClickListener(this);
//		cendHeadLayout = findViewById(R.id.center_head_layout);
//		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//		// 用mediaplayer播放音频文件 必须要。网友意见是AudioManager.MODE_IN_CALL 此处不适合
//		if (audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) < 10) {
//			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0);
//		}
//		jybtn = (ImageView) findViewById(R.id.video_activity_jybtn);
//		jybtn.setOnClickListener(this);
//		waifbtn = (ImageView) findViewById(R.id.chage_speak_img);
//		waifbtn.setOnClickListener(this);
//		//		remoteView = (SurfaceView) findViewById(R.id.remote);
//		//		localView = (SurfaceView) findViewById(R.id.local);
//		nickTv = (TextView) findViewById(R.id.video_nick_name);
//		changeCameroBtn = (ImageView) findViewById(R.id.video_activity_change_camero_btn);
//		changeCameroBtn.setOnClickListener(this);
//		addfriendbtn = (ImageButton) findViewById(R.id.add_friend);
//		addfriendbtn.setOnClickListener(this);
//		guanzhuButton = (ImageButton) findViewById(R.id.songli_btn);
//		guanzhuButton.setOnClickListener(this);
//		initBroadCastRecevier();
//		bHungUpBtn = findViewById(R.id.video_activity_center_hung_up_layout);
//		bHungUpBtn.setOnClickListener(this);
//		bottomView = getLayoutInflater().inflate(R.layout.media_popup_layout,
//				null);
//		centerWindow = DialogUtil.showBottomWindow(mainView, this, bottomView);
//		centerWindow.setOnDismissListener(new OnDismissListener() {
//
//			@Override
//			public void onDismiss() {
//				// TODO Auto-generated method stub
//				LogUtil.i("拒绝切换", "!!!!!!!!!!!!!!!!!");
//				// 拒绝切换语音
//				refuseChage();
//			}
//		});
//		chronometer = (Chronometer) findViewById(R.id.video_activity_chronometer);
//		chronometer.setText("连接中...");
//		chronometer.addTextChangedListener(timeTextSwticher);
//		agreChanBtn = bottomView
//				.findViewById(R.id.media_botton_layout_agree_btn);
//		refuChanBtn = bottomView
//				.findViewById(R.id.media_botton_layout_refuses_btn);
//		agreChanBtn.setOnClickListener(this);
//		refuChanBtn.setOnClickListener(this);
//		refusebtn = findViewById(R.id.media_activity_refuses_btn);
//		refusebtn.setOnClickListener(this);
//
//		preferences = PreferenceManager.getDefaultSharedPreferences(this);
//		editor = preferences.edit();
//		changeVideobtn = (ImageView) findViewById(R.id.video_activity_change_video_btn);
//		changeVideobtn.setOnClickListener(this);
//
//		callInLayout = findViewById(R.id.video_avtivity_call_in_layout);
//		callInLayout.bringToFront();
//		callOutLayout = findViewById(R.id.video_activity_call_out_layout);
//		callOutLayout.bringToFront();
//		headImg = (ImageView) findViewById(R.id.video_activity_headimg);
//		agreebtn = findViewById(R.id.video_activity_agree_btn);
//
//		agreebtn.setOnClickListener(this);
//		videoRootView = (RelativeLayout) findViewById(R.id.video_root);
//		ViewGroup.LayoutParams lp = videoRootView.getLayoutParams();
//		lp.width = sWidth + 30;
//		lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//		initVideoView();
//		//		remoteView = (SurfaceView) findViewById(R.id.remote);
//		//		localView = (SurfaceView) findViewById(R.id.local);
//		//		int localwidth = sWidth / 6;
//		//		int laocalhight = localwidth * 4 / 3;
//		//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//		//				localwidth, laocalhight);
//		//		lp.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.remote);
//		//		lp.addRule(RelativeLayout.ALIGN_RIGHT, R.id.remote);
//		//		android.view.ViewGroup.LayoutParams lp = localView.getLayoutParams();
//		//		lp.width = localwidth;
//		//		lp.height = laocalhight;
//		if (jingleData != null) {
//			// 唤醒被叫屏幕,并开始倒计时 被叫45s 无应答 则由被叫挂断电话
//			weakUp();
//			// ImageCacheManager.getInstance().displayheadImage(
//			// jingleData.getZjhead(), headImg,
//			// LoaderOptions.getHeadOptions(360));
//			ImageCacheManager.getInstance().displayRoundImage(
//					jingleData.getZjhead(), headImg,
//					LoaderOptions.getChatHeadOptions(0), 300);
//			ImageCacheManager.getInstance().displayRoundImage(
//					jingleData.getZjhead(), titleHeadImg,
//					LoaderOptions.getChatHeadOptions(0), 300);
//			titleHeadImg.bringToFront();
//			nickName = jingleData.getNickName();
//			nickTv.setText(nickName + noticeStr);
//			if (BaseApplicasion.jingMap.get(jingleData.getSid())) {
//				sendBroadcast(new Intent(
//						Constants.DIALER_REQUEST_JINGGE_SESION_TERMINATE));
//			}
//			oid = Integer.parseInt(jingleData.getFrom().split("@")[0]);
//		} else {
//			ImageCacheManager.getInstance().displayRoundImage(bean.getHead(),
//					headImg, LoaderOptions.getChatHeadOptions(0), 300);
//			ImageCacheManager.getInstance().displayRoundImage(bean.getHead(),
//					titleHeadImg, LoaderOptions.getChatHeadOptions(0), 300);
//
//			titleHeadImg.bringToFront();
//			nickName = bean.getNick();
//			nickTv.setText(noticeStr + bean.getNick());
//			oid = bean.getOid();
//		}
//		// 保存通话记录
//		saveMsgToLocal();
//		//		remoteView.setOnClickListener(new OnClickListener() {
//		//
//		//			@Override
//		//			public void onClick(View v) {
//		//				// TODO Auto-generated method stub
//		//				changeWindow();
//		//			}
//		//		});
//		reportPop = DialogUtil.creatReportPop(this, this);
//		reportPop.setOnDismissListener(new OnDismissListener() {
//
//			@Override
//			public void onDismiss() {
//				// TODO Auto-generated method stub
//				Tools.backgroundAlpha(VideoActivity.this, 1f);
//			}
//		});
//		initPopupWindow();
//		setLayoutVisibility();
//
//	}
//
//	private void initVideoView(){
//		localVideoView = new SurfaceView(this);
//		localVideoView.getHolder().setType(
//				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//		remoteVideoView = (SurfaceView) LayoutInflater.from(this).inflate(R.layout.view_remote_surface, null);
//	}
//
//	/**
//	 * 大播放区域LayoutParams
//	 */
//	private LayoutParams bigLp;
//	/**
//	 * 小
//	 * 播放区域LayoutParams
//	 */
//	private LayoutParams smallLp;
//
//	private void addVideoView(boolean isRemoteBig){
//		videoRootView.removeAllViews();
//		if(bigLp == null){
//			bigLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		}
//		if(smallLp == null){
//			int localwidth = sWidth / 6;
//			int localhight = localwidth * 4 / 3;
//			smallLp = new LayoutParams(localwidth,localhight);
//			smallLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//			smallLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		}
//		if(isRemoteBig){//远程大，本地小
//			videoRootView.addView(localVideoView, smallLp);
//			videoRootView.addView(remoteVideoView,bigLp);
//			localVideoView.bringToFront();
//			localVideoView.requestFocus();
//			localVideoView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					addVideoView(false);
//				}
//			});
//			remoteVideoView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					changeWindow();
//				}
//			});
//		}else{//远程小，本地大
//			videoRootView.addView(remoteVideoView, smallLp);
//			videoRootView.addView(localVideoView,bigLp);
//			remoteVideoView.bringToFront();
//			remoteVideoView.requestFocus();
//			localVideoView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					changeWindow();
//				}
//			});
//			remoteVideoView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					addVideoView(true);
//				}
//			});
//		}
//	}
//
//	void changeWindow() {
//		Tools.backgroundAlpha(this, 1.0f);
//		if (isFullScreen) {
//			isFullScreen = false;
//			titleHeadLayout.setVisibility(View.VISIBLE);
//			callingLayout.setVisibility(View.VISIBLE);
//			callingLayout.startAnimation(slid_in);
//			titleHeadLayout.startAnimation(slid_in_from_top);
//
//		} else {
//			isFullScreen = true;
//			titleHeadLayout.setVisibility(View.GONE);
//			callingLayout.setVisibility(View.GONE);
//			callingLayout.setAnimation(slid_out);
//			titleHeadLayout.startAnimation(slid_out_fromTop);
//		}
//
//	};
//
//	protected void charge() {
//		// TODO Auto-generated method stub
//		int i = 1;
//		if (BaseApplicasion.isVideo) {
//			i = 2;
//		}
//		if (!isCallOut) {
//			// 被叫不计费
//			return;
//		}
//
//		new NetTaskForResult(this, InterfaceMethod.get535(mybean.getUid(), oid,
//				callTime, chargeCallingType, i), new OnResultCallback() {
//
//			@Override
//			public void onCallback(ResultBean bean) {
//				// TODO Auto-generated method stub
//				if (bean.getErr() != 0) {
//					ToastUtils.showData(VideoActivity.this, bean);
//					sendTerminate(0);
//				}
//			}
//		}, false).execute();
//
//	}
//
//	void termineatToCharge(int i) {
//		LogUtil.i("那里调用", i);
//		new NetTaskForResult(this, InterfaceMethod.get540(mybean.getUid(),
//				callTime), new OnResultCallback() {
//
//			@Override
//			public void onCallback(ResultBean bean) {
//				// TODO Auto-generated method stub
//				if (bean.getErr() != 0) {
//					Map<String, String> map = (Map<String, String>) bean
//							.getResultParam(Map.class).get(0);
//					// ToastUtils.showDefaultToast(VideoActivity.this,
//					// map.get("data"));
//
//				}
//			}
//		}, false).execute();
//
//	}
//
//	/**
//	 * 根据状态 设置view 显示及隐藏
//	 */
//	private void setLayoutVisibility() {
//		// TODO Auto-generated method stub
//		sendFirstDoodle();
//		// 主叫 画面显示 控制；
//		if (isCallOut) {
//			setZJViews();
//		} else {
//			setBJViews();
//		}
//		if (audioManager.isSpeakerphoneOn()) {
//			waifbtn.setImageResource(R.drawable.waifang_on);
//		} else {
//			waifbtn.setImageResource(R.drawable.waifaneg_off);
//		}
//	}
//
//	/**
//	 * 设置被叫view显示和隐藏
//	 */
//	private void setBJViews() {
//		// TODO Auto-generated method stub
//		// 被叫 UI 显示没接受时底部按钮显示
//		if (!isagree) {
//			// 被叫显示打入，隐藏打出VIEW
//			callInLayout.setVisibility(View.VISIBLE);
//			callOutLayout.setVisibility(View.GONE);
//			hbLayout.setVisibility(View.GONE);
//			// 隐藏掉 surfaceview
//			//			localView.setVisibility(View.GONE);
//			//			remoteView.setVisibility(View.GONE);
//			videoRootView.setVisibility(View.GONE);
//			doodle_rootView.setVisibility(View.VISIBLE);
//			centerContainer.setVisibility(View.GONE);
//			titleDanmuView.setVisibility(View.GONE);
//			randomLayout.setVisibility(View.GONE);
//
//		} else {
//			hbNoticeView.setVisibility(View.GONE);
//			callholdLayout.getBackground().setAlpha(0);
//			// 接受 显示 底部 计时VIEW并置顶
//			callingLayout.setVisibility(View.VISIBLE);
//			callingLayout.bringToFront();
//			// 隐藏 头像及
//			callInLayout.setVisibility(View.GONE);
//			callOutLayout.setVisibility(View.GONE);
//			if (BaseApplicasion.isVideo) {
//				if (doodle_Pop.isShowing()) {
//					doodle_Pop.dismiss();
//				}
//				doodleImg.setVisibility(View.GONE);
//				changeVideobtn.setImageResource(R.drawable.yuyinmoshi);
//				changeVideobtn.setVisibility(View.GONE);
//				centerContainer.setVisibility(View.VISIBLE);
//				centerContainer.bringToFront();
//				doodle_rootView.setVisibility(View.GONE);
//				//				localView.setVisibility(View.VISIBLE);
//				//				remoteView.setVisibility(View.VISIBLE);
//				//				remoteView.startAnimation(showAction);
//				videoRootView.setVisibility(View.VISIBLE);
//				titleDanmuView.setVisibility(View.VISIBLE);
//				titleDanmuView.bringToFront();
//				if (!isAttched) {
//					isAttched = true;
//					addVideoView(true);
//					sdkCore.attachVideoWindow(remoteVideoView, localVideoView);
//					camaraTag = CameraInfo.CAMERA_FACING_BACK;
//					handler.sendEmptyMessage(Constants.CHANGE_CAMERA);
//				}
//				mDoodle.setVisibility(View.GONE);
//			} else {
//				changeVideobtn.setVisibility(View.VISIBLE);
//				changeVideobtn.setImageResource(R.drawable.shipin);
//				centerContainer.setVisibility(View.GONE);
//				//				localView.setVisibility(View.GONE);
//				//				remoteView.setVisibility(View.GONE);
//				videoRootView.removeAllViews();
//				videoRootView.setVisibility(View.GONE);
//				doodle_rootView.setVisibility(View.VISIBLE);
//				// changeCameroBtn.setVisibility(View.INVISIBLE);
//				mDoodle.setZOrderOnTop(true);
//				mDoodle.setVisibility(View.VISIBLE);
//			}
//		}
//	}
//
//	/**
//	 * 设置主叫view 显示和隐藏
//	 */
//	@SuppressLint("ResourceAsColor")
//	@SuppressWarnings("deprecation")
//	private void setZJViews() {
//		// TODO Auto-generated method stub
//		LogUtil.i("type!!!!!!!!!", chargeCallingType + "@" + isagree);
//		if (!isagree) {
//			callOutLayout.setVisibility(View.VISIBLE);
//			randomLayout.setVisibility(View.GONE);
//			callInLayout.setVisibility(View.GONE);
//			videoRootView.removeAllViews();
//			videoRootView.setVisibility(View.GONE);
//			//			remoteView.setVisibility(View.GONE);
//			//			localView.setVisibility(View.GONE);
//			doodle_rootView.setVisibility(View.VISIBLE);
//			centerContainer.setVisibility(View.GONE);
//			titleDanmuView.setVisibility(View.GONE);
//			hbLayout.setVisibility(View.VISIBLE);
//			costTv.setText(costText);
//			titleCostTv.setText(costText);
//			LogUtil.i("typeVideo", chargeCallingType);
//			if (chargeCallingType == 2) {
//				JingleManager.initVideo(this, bean, mybean,
//						BaseApplicasion.isVideo);
//				HandleMedia(getApplicationContext(),
//						Constants.PlayerMag.PLAY_MUSIC);
//				costTv.setTextColor(Color.parseColor("#f96d2f"));
//				hbtitleTv.setVisibility(View.VISIBLE);
//				hbLayout.getBackground().setAlpha(125);
//				callOutLayout.setVisibility(View.VISIBLE);
//			} else {
//
//				costTv.setTextColor(Color.parseColor("#ffffff"));
//				hbtitleTv.setVisibility(View.GONE);
//				nickTv.setText(bean.getNick());
//				hbLayout.getBackground().setAlpha(0);
//				callOutLayout.setVisibility(View.GONE);
//			}
//		} else {
//			hbLayout.setVisibility(View.GONE);
//			callholdLayout.getBackground().setAlpha(0);
//			callOutLayout.setVisibility(View.GONE);
//			callInLayout.setVisibility(View.GONE);
//			callingLayout.setVisibility(View.VISIBLE);
//			callingLayout.bringToFront();
//			if (BaseApplicasion.isVideo) {
//				if (doodle_Pop.isShowing()) {
//					doodle_Pop.dismiss();
//				}
//				doodleImg.setVisibility(View.GONE);
//				titleHeadLayout.setVisibility(View.INVISIBLE);
//				changeVideobtn.setImageResource(R.drawable.yuyinmoshi);
//				changeVideobtn.setVisibility(View.GONE);
//				//				localView.setVisibility(View.VISIBLE);
//				//				remoteView.setVisibility(View.VISIBLE);
//				//				remoteView.startAnimation(showAction);
//				videoRootView.setVisibility(View.VISIBLE);
//				doodle_rootView.setVisibility(View.GONE);
//				centerContainer.setVisibility(View.VISIBLE);
//				titleDanmuView.setVisibility(View.VISIBLE);
//				titleDanmuView.bringToFront();
//				centerContainer.bringToFront();
//				mDoodle.setVisibility(View.GONE);
//				if (!isAttched) {
//					isAttched = true;
//					addVideoView(true);
//					sdkCore.attachVideoWindow(remoteVideoView, localVideoView);
//				}
//
//				sdkCore.switchCamera(CameraInfo.CAMERA_FACING_FRONT);
//				camaraTag = CameraInfo.CAMERA_FACING_FRONT;
//			} else {
//				changeVideobtn.setVisibility(View.VISIBLE);
//				changeVideobtn.setImageResource(R.drawable.shipin);
//				//				remoteView.setVisibility(View.GONE);
//				//				localView.setVisibility(View.GONE);
//				videoRootView.removeAllViews();
//				videoRootView.setVisibility(View.GONE);
//				centerContainer.setVisibility(View.GONE);
//				doodle_rootView.setVisibility(View.VISIBLE);
//				mDoodle.setVisibility(View.VISIBLE);
//				mDoodle.setZOrderOnTop(true);
//			}
//		}
//	}
//
//	private void HandleMedia(Context context, String action) {
//		// TODO Auto-generated method stub
//		if (Constants.PlayerMag.PLAY_MUSIC.equals(action)) {
//			if (isCallOut) {
//				Tools.CloseSpeaker(getApplicationContext());
//			} else {
//				Tools.OpenSpeaker(getApplicationContext());
//			}
//		}
//		Tools.StartMusicService(context, action, isCallOut);
//	}
//
//	/**
//	 * 保存通话记录
//	 */
//	void saveMsgToLocal() {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//
//				if (bean != null) {
//					// 显示被叫头像
//					howLong = "未接通";
//
//					telegrambeanq = new Telegrambean(bean.getOid(), bean
//							.getJidWithNoTag(), mybean.getJidWithNoTag(),
//							isCallOut, Tools.formatDate(new Date(callTime)),
//							howLong, bean.getHead(), bean.getNick(), mybean
//							.getUid());
//				} else {
//
//					telegrambeanq = new Telegrambean(Integer
//							.parseInt(jingleData.getFrom().split("@")[0]),
//							mybean.getJidWithNoTag(), jingleData.getFrom()
//							.split("/")[0], isCallOut, Tools
//							.formatDate(new Date(callTime)), howLong,
//							jingleData.getZjhead(), jingleData.getNickName(),
//							mybean.getUid());
//
//				}
//				recordbean = new Recordbean(1, telegrambeanq.getOid(),
//						telegrambeanq.getMsgTime() + "", telegrambeanq.getTo(),
//						telegrambeanq.getFrom(), telegrambeanq.getHowLong());
//
//				TelegramDBHelper.getInstance().addMessage(telegrambeanq);
//				RecordDBHelper.getInstance().addMessage(recordbean);
//
//			}
//		}).start();
//
//	}
//
//	/**
//	 * 开始计时 如果超过45s无人接听就自动挂断
//	 */
//	private void startMorentime() {
//		// TODO Auto-generated method stub
//		if (countDownTimer == null) {
//			countDownTimer = new CountDownTimer(4500 * 10, 1000) {
//				@Override
//				public void onTick(long millisUntilFinished) {
//					// TODO Auto-generated method stub
//				}
//
//				@Override
//				public void onFinish() {
//					// TODO Auto-generated method stub
//					if (!isagree) {
//						sendTerminate(1);
//					}
//				}
//			};
//			countDownTimer.start();
//		}
//	}
//
//	@Override
//	public void widgetClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.tuya_forward:
//			String forId = mDoodle.goForward(mybean.getJidWithNoTag());
//			if (!StringUtills.isNULL(forId)) {
//				DoodleData doodleData = new DoodleData();
//				doodleData.setIqid(forId);
//				doodleData.setAction(Constants.DOODLE_GO_FORWARD);
//				sendDool(doodleData);
//			}
//
//			break;
//		case R.id.title_head_layout:
//
//		case R.id.holding_hb_layout:
//			sendTerminate(3);
//			UserModel.outGoingCall(this, bean.getOid());
//			break;
//		case R.id.media_random_activity_refuses_btn:
//			finish();
//			break;
//		case R.id.video_random_activity_sure_btn:
//			chargeCallingType = 2;
//			headbgLayout.startAnimation(headAction);
//			setLayoutVisibility();
//
//			break;
//
//		case R.id.jubao_1:
//			UserModel.report(this,reportPop, bean.getUid(), 7, "", "", "");
//			break;
//		case R.id.jubao_2:
//			UserModel.report(this, reportPop, bean.getUid(), 8, "", "", "");
//			break;
//		case R.id.jubao_3:
//			UserModel.report(this,reportPop,  bean.getUid(), 9, "", "", "");
//			break;
//		case R.id.jubao_4:
//			UserModel.report(this, reportPop, bean.getUid(), 10, "", "", "");
//			break;
//		case R.id.jubao_5:
//			UserModel.report(this,reportPop,  bean.getUid(), 11, "", "", "");
//			break;
//
//		case R.id.lahei_jubao:
//			// 举报
//			laheiPop.dismiss();
//			reportPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
//					| Gravity.CENTER_HORIZONTAL, 0, 0);
//			Tools.backgroundAlpha(VideoActivity.this, 0.5f);
//			break;
//
//		case R.id.lahei_cancel:
//			laheiPop.dismiss();
//			break;
//
//		case R.id.lahei_lahei:
//			UserModel.lahei(this, mybean.getUid(), oid);
//			break;
//
//		case R.id.title_right_img:
//			laheiPop.showAtLocation(mainView, Gravity.BOTTOM
//					| Gravity.CENTER_HORIZONTAL, 0, 0);
//
//			break;
//		case R.id.tuya_cleanall:
//			mDoodle.clean(mybean.getJidWithNoTag());
//
//			DoodleData cleandoodleData = new DoodleData();
//			cleandoodleData.setAction(Constants.DOODLE_CLEALER);
//			cleandoodleData.setIqid(FileUtils.generateFileName());
//			sendDool(cleandoodleData);
//			break;
//		case R.id.tuya_return:
//			String id = mDoodle.back("", mybean.getJidWithNoTag());
//			if (!StringUtills.isNULL(id)) {
//				DoodleData doodleData = new DoodleData();
//				doodleData.setIqid(id);
//				doodleData.setAction(Constants.DOODLE_RETURN);
//				sendDool(doodleData);
//			}
//			break;
//
//		case R.id.video_cancel_btn:
//			// 取消电话
//			sendTerminate(4);
//
//			break;
//			// 同意接听
//		case R.id.video_activity_agree_btn:
//			sdkCore.callCreate();
//			LogUtil.i("#########", "主叫");
//			isagree = true;
//			HandleMedia(this, Constants.PlayerMag.STOP_MUSIC);
//			Tools.CloseSpeaker(VideoActivity.this);
//			if (jingleData != null) {
//				JingleManager.jingleAccept(jingleData);
//				handler.sendEmptyMessage(Constants.START_CHAT);
//			}
//			showdoodlePop();
//			break;
//
//		case R.id.songli_btn:
//			// 送礼
//			songliPop.showAtLocation(VideoActivity.this.getWindow()
//					.getDecorView(),
//					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//
//			break;
//		case R.id.doodle_img:
//			//
//			doodleImg.setImageResource(R.drawable.tuya_pre);
//			showdoodlePop();
//
//			break;
//			// 免提
//		case R.id.chage_speak_img:
//			handler.sendEmptyMessage(Constants.PlayerMag.CHANGE_SPEARK);
//			break;
//			// 修改前后摄像头
//		case R.id.video_activity_change_camero_btn:
//			LogUtil.i("切换摄像头", "!!!!!!!!!!!!!!!");
//			if (BaseApplicasion.isVideo)
//				handler.sendEmptyMessage(2);
//			break;
//			// 切换为音频
//		case R.id.video_activity_change_video_btn:
//			if (isagree) {
//				if (!BaseApplicasion.isVideo)
//					changeToVideo();
//
//			}
//
//			break;
//			// 拒绝接听
//		case R.id.media_activity_refuses_btn:
//			sendTerminate(6);
//
//			break;
//			// 挂断(已经接听挂断)
//		case R.id.video_activity_center_hung_up_layout:
//			sendTerminate(5);
//
//			break;
//			// 同意切换为视频
//		case R.id.media_botton_layout_agree_btn:
//			agreeChage();
//			break;
//			// 拒绝切换为视频
//		case R.id.media_botton_layout_refuses_btn:
//			refuseChage();
//			break;
//		case R.id.add_friend:
//			// 添加好友
//			addFreind();
//			break;
//
//			// 静音
//		case R.id.video_activity_jybtn:
//			handler.sendEmptyMessage(Constants.PlayerMag.CHANGE_TO_JINGYIN);
//			break;
//		case R.id.tuya_but1:
//			pager.setCurrentItem(0, false);
//			tuya_choise = 0;
//			changeTuyaBut(tuya_choise);
//			break;
//		case R.id.tuya_but2:
//
//			gotoCamera();
//			break;
//
//		case R.id.tuya_but3:
//			pager.setCurrentItem(1, false);
//			tuya_choise = 1;
//			changeTuyaBut(tuya_choise);
//			break;
//		case R.id.tuya_but4:
//			Bitmap bitmap = mDoodle.getBitmap();
//			String path = BitmapUtil.saveBitmapToAlum(this, bitmap,"doodle");
//			ToastUtils.showDefaultToast(this, "图片已保存至:" + path);
//			doodle_Pop.dismiss();
//			break;
//
//		case R.id.tuya_xiangpi:
//			isEraser = true;
//			tuya_xiangpi_img.setImageResource(R.drawable.tuya_xiangpica_pre);
//			mDoodle.setPaint(getRemoteEraser(mySize + ""));
//			break;
//		}
//
//	}
//
//	private void gotoCamera() {
//		// TODO Auto-generated method stub
//		if (doodle_Pop.isShowing()) {
//
//			doodle_Pop.dismiss();
//		}
//		Intent intent = new Intent(this, CameraAty.class);
//		intent.putExtra("doodleRate", doodleRate);
//		startActivityForResult(intent, 2);
//	}
//
//	/**
//	 * 把自己屏幕宽度告诉
//	 */
//	private void sendFirstDoodle() {
//		// TODO Auto-generated method stub
//		doodleData = new DoodleData();
//		doodleData.width = sWidth;
//		doodleData.hight = sHight;
//		doodleData.setAction(Constants.DOODLE_PLAYING);
//		sendDool(doodleData);
//	}
//
//	private void showdoodlePop() {
//		// TODO Auto-generated method stub
//		doodle_Pop.showAtLocation(
//				VideoActivity.this.getWindow().getDecorView(), Gravity.BOTTOM
//				| Gravity.CENTER_HORIZONTAL, 0, 0);
//		if (doodle_Pop.isShowing()) {
//			bar.setProgress(mySize);
//		}
//	}
//
//	private void agreeChage() {
//		// TODO Auto-generated method stub
//		doSomeActions();
//		centerWindow.dismiss();
//		BaseApplicasion.isVideo = true;
//		isagree = true;
//		handler.sendEmptyMessage(Constants.CHANAGE_CHAT_TYPE_TO_VIDEO);
//	}
//
//	private void doSomeActions() {
//		// TODO Auto-generated method stub
//		cendHeadLayout.startAnimation(hideAction);
//		callholdLayout.getBackground().setAlpha(0);
//		Tools.StartMusicService(getApplicationContext(),
//				Constants.PlayerMag.STOP_MUSIC, false);
//
//		if (changeJingleData != null) {
//			JingleManager.jingleChangeMedia(new JingleData(changeJingleData
//					.getFrom(), preferences.getString(Constants.USER_ACCOUNT,
//							""), JingleActionEnum.CONTENT_ACCEPT, changeJingleData
//					.isAudioContent(), changeJingleData.isVidioContent(),
//					changeJingleData.getSid()));
//		}
//	}
//
//	/**
//	 * 拒绝切换为视频
//	 */
//	public void refuseChage() {
//		// TODO Auto-generated method stub
//		centerWindow.dismiss();
//		callholdLayout.getBackground().setAlpha(0);
//		doodle_rootView.setVisibility(View.VISIBLE);
//		mDoodle.setVisibility(View.VISIBLE);
//		Tools.StartMusicService(getApplicationContext(),
//				Constants.PlayerMag.STOP_MUSIC, false);
//		cendHeadLayout.startAnimation(hideAction);
//		JingleManager.reject(new JingleData(changeJingleData.getFrom(),
//				preferences.getString(Constants.USER_ACCOUNT, ""),
//				JingleActionEnum.CONTENT_REJECT, changeJingleData
//				.isAudioContent(), changeJingleData.isVidioContent(),
//				changeJingleData.getSid()));
//	}
//
//	/**
//	 * 添加好友
//	 */
//	private void addFreind() {
//		// TODO Auto-generated method stub
//		int uid = preferences.getInt(Constants.USER_UID, -1);
//		int oid = 0;
//		String to = "";
//		if (bean != null) {
//			oid = bean.getUid();
//			to = bean.getJidWithNoTag();
//		} else {
//			oid = Integer.parseInt(jingleData.getFrom().split("@")[0]);
//			to = jingleData.getFrom().split("/")[0];
//		}
//		UserData userData = UserDBHelper.getInstance().getUserDataByID(uid);
//		if (userData != null) {
//			UserModel.addFriend(this, userData, oid, to, 1, "",
//					new OnResultCallback() {
//
//				@Override
//				public void onCallback(ResultBean bean) {
//					// TODO Auto-generated method stub
//					@SuppressWarnings("unchecked")
//					Map<String, String> map = (Map<String, String>) bean
//					.getResultParam(Map.class).get(0);
//					ToastUtils.showDefaultToast(VideoActivity.this,
//							map.get("data"));
//				}
//			});
//		} else {
//			ToastUtils.showToast(this, "操作失败,请重新登录");
//		}
//	}
//
//	@SuppressLint("HandlerLeak")
//	public class MyHandler extends Handler {
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			switch (msg.what) {
//
//			case Constants.START_CHAT:
//				headbgLayout.clearAnimation();
//				cendHeadLayout.startAnimation(hideAction);
//				startTime();
//				setLayoutVisibility();
//				postDelayed(startChatRunnable, 2000);
//				break;
//			case Constants.CHANGE_CAMERA:// 2
//				ToastUtils.showToast(VideoActivity.this, "切换摄像头");
//				if (camaraTag == CameraInfo.CAMERA_FACING_BACK) {
//					CameraInfo cameraInfo = new CameraInfo();
//					for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
//						Camera.getCameraInfo(i, cameraInfo);
//						if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
//							sdkCore.switchCamera(CameraInfo.CAMERA_FACING_FRONT);
//							camaraTag = CameraInfo.CAMERA_FACING_FRONT;
//						}
//					}
//
//				} else {
//					sdkCore.switchCamera(CameraInfo.CAMERA_FACING_BACK);
//					camaraTag = CameraInfo.CAMERA_FACING_BACK;
//				}
//
//				break;
//			case Constants.CHANAGE_CHAT_TYPE_TO_VIDEO:// 3
//				LogUtil.i("切换", videoPayloadType);
//				BaseApplicasion.isVideo = true;
//				isagree = true;
//				// 切换时结算语音
//				termineatToCharge(0);
//				sdkCore.switchCamera(CameraInfo.CAMERA_FACING_FRONT);
//				camaraTag = CameraInfo.CAMERA_FACING_FRONT;
//				sdkCore.startMediaStreams(0, videoPayloadType);
//				callTime = Tools.getgenerateTime();
//				// 开始视频 计费
//				charge();
//				Tools.OpenSpeaker(VideoActivity.this);
//				setLayoutVisibility();
//				break;
//
//			case Constants.CHANAGE_CHAT_TYPE_TO_AUDIO:// 5
//				BaseApplicasion.isVideo = false;
//				isagree = true;
//				sdkCore.stopMediaStreams(sdkMedia.MEDIA_TYPE_ONLY_VIDEO);
//				Tools.CloseSpeaker(VideoActivity.this);
//				setLayoutVisibility();
//				break;
//			case Constants.PlayerMag.CHANGE_SPEARK:// 13
//
//				setAudioMode();
//
//				break;
//			case Constants.PlayerMag.CHANGE_TO_JINGYIN:// 14
//				mute = !mute;
//				if (mute) {
//					jybtn.setImageResource(R.drawable.maikefeng_off);
//				} else {
//					jybtn.setImageResource(R.drawable.maikefeng_on);
//				}
//				sdkCore.muteMic(mute);
//				break;
//			case Constants.PlayerMag.CHANGE_GIFT:
//				SongliBean bean = (SongliBean) msg.getData().getSerializable(
//						"gift");
//				// gift_much.setText("");
//				int m = bean.getNum() * bean.getMuch();
//				String n = bean.getName();
//				String g = bean.getUnit();
//				String a = "确认消费 " + m + "Y 豆送出" + n + bean.getNum() + g + "吗？";
//				int bstart = ("确认消费").length();
//				int bend = bstart + (m + "Y 豆送出").length();
//				SpannableStringBuilder style = new SpannableStringBuilder(a);
//				style.setSpan(new ForegroundColorSpan(Color.RED), bstart, bend,
//						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//				gift_much.setText(style);
//				break;
//
//			case Constants.PMD_LOOPING_TAG:// 6
//				for (int i = 0; i < pmdList.size(); i++) {
//					TopPmdView danmu = (TopPmdView) pmdList.get(i);
//					if (!isTopOk) {
//						danmu.setPosition(0);
//						LayoutParams lp = new LayoutParams(
//								LayoutParams.WRAP_CONTENT,
//								LayoutParams.MATCH_PARENT);
//						lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//						// lp.topMargin = danmuHeight;
//						danmu.setGravity(Gravity.CENTER);
//						if (danmu.getParent() == null) {
//							container.addView(danmu, lp);
//							danmu.send();
//							sendPosition.put(0, true);
//							current = i;
//						}
//						break;
//					}
//				}
//
//				break;
//			case Constants.PMD_NOW_TAG:// 7
//
//				int next = current + 1;
//				if (pmdList.size() > next) {
//					TopPmdView danmu = (TopPmdView) pmdList.get(current + 1);
//					danmu.setPosition(0);
//					LayoutParams lp = new LayoutParams(
//							LayoutParams.WRAP_CONTENT,
//							LayoutParams.MATCH_PARENT);
//					lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//					// lp.topMargin = danmuHeight;
//					danmu.setGravity(Gravity.CENTER);
//					container.addView(danmu, lp);
//					danmu.send();
//					isTopOk = true;
//
//				}
//				current++;
//
//				break;
//
//			default:
//				break;
//			}
//
//		}
//	}
//
//	Runnable startChatRunnable = new Runnable() {
//		public void run() {
//			if (!BaseApplicasion.isVideo) {
//				if (audiopayloadType == 0) {
//					sendTerminate(7);
//
//				} else {
//					sdkCore.startMediaStreams(audiopayloadType, 0);
//					BaseApplicasion.isStartMedia = true;
//				}
//
//			} else {
//				if (videoPayloadType == 0 || audiopayloadType == 0) {
//					sendTerminate(8);
//				} else {
//					Tools.OpenSpeaker(VideoActivity.this);
//					waifbtn.setImageResource(R.drawable.waifang_on);
//
//					sdkCore.startMediaStreams(audiopayloadType,
//							videoPayloadType);
//					BaseApplicasion.isStartMedia = true;
//					int i = sdkCore.setVideoScale(0.90f, 1);
//					LogUtil.i("修面", i + "");
//				}
//			}
//		}
//	};
//
//	/**
//	 * 切换为语音
//	 */
//	private void changeToAudio() {
//		// TODO Auto-generated method stub
//		ToastUtils.showToast(VideoActivity.this, "切换语音");
//		changToAudio();
//
//	}
//
//	/**
//	 * 设置免提模式
//	 */
//	protected void setAudioMode() {
//		// TODO Auto-generated method stub
//		if (audioManager.isSpeakerphoneOn()) {
//			waifbtn.setImageResource(R.drawable.waifang_on);
//			Tools.CloseSpeaker(this);
//		} else {
//			waifbtn.setImageResource(R.drawable.waifaneg_off);
//			Tools.OpenSpeaker(this);
//		}
//		LogUtil.i("改变情景模式", audioManager.isSpeakerphoneOn());
//	}
//
//	/**
//	 * 挂断
//	 */
//	private void terminate() {
//		// TODO Auto-generated method stub
//		HandleMedia(this, Constants.PlayerMag.STOP_MUSIC);
//		chronometer.stop();
//		audiopayloadType = 0;
//		videoPayloadType = 0;
//		BaseApplicasion.jingMap.clear();
//		if (isagree) {
//			telegrambeanq.setHowLong(chronometer.getText().toString());
//			recordbean.setNewmsg(telegrambeanq.getHowLong());
//		}
//		chronometer.setText("已断开");
//		LogUtil.i("修改数据", telegrambeanq.toString());
//		TelegramDBHelper.getInstance().updateMsgTime(telegrambeanq);
//		RecordDBHelper.getInstance().addMessage(recordbean);
//		YDMessageManager.refreshUI(telegrambeanq.getTo());
//		handler.postDelayed(terminateRunnable, 1000);
//	}
//
//	void sendTerminate(int i) {
//		LogUtil.i("主动挂断", i + "");
//		if (isCharge) {
//			termineatToCharge(2);
//		}
//		if (jingleData != null) {
//			JingleManager.jingleTerminate(jingleData);
//		} else {
//			JingleManager.jingleTerminate(new JingleData(
//					bean.getJidWithNoTag(), StringUtills.stringToBase64(mybean
//							.getHead())));
//		}
//		terminate();
//	}
//
//	/**
//	 * 切换为视频
//	 */
//	private void changeToVideo() {
//		// TODO Auto-generated method stub
//		if (jingleData == null) {
//			JingleManager.jingleChangeMedia(getChangeMediaJingledata(true,
//					true, JingleActionEnum.CONTENT_ADD));
//		} else {
//			JingleManager.jingleChangeMedia(new JingleData(
//					jingleData.getFrom(), preferences.getString(
//							Constants.USER_ACCOUNT, ""),
//					JingleActionEnum.CONTENT_ADD, true, true, jingleData
//					.getSid()));
//		}
//	}
//
//	/**
//	 * sdk 暂停
//	 */
//	void OnPauseMedia() {
//		if (BaseApplicasion.isVideo) {
//			sdkCore.pauseMediaStreams(sdkMedia.MEDIA_TYPE_AUDIO_VIDEO);
//		} else {
//			sdkCore.pauseMediaStreams(sdkMedia.MEDIA_TYPE_ONLY_AUDIO);
//		}
//	}
//
//	/**
//	 * 继续通话
//	 */
//	void OnReStartMedia() {
//		if (BaseApplicasion.isVideo) {
//			sdkCore.resumeMediaStreams(sdkMedia.MEDIA_TYPE_AUDIO_VIDEO);
//		} else {
//			sdkCore.resumeMediaStreams(sdkMedia.MEDIA_TYPE_ONLY_AUDIO);
//		}
//	}
//
//	/**
//	 * 切换为音频
//	 */
//	private void changToAudio() {
//		// TODO Auto-generated method stub
//		if (jingleData == null) {
//			JingleManager.jingleChangeToAudio(getChangeMediaJingledata(true,
//					false, JingleActionEnum.CONTENT_REMOVE));
//		} else {
//			JingleManager.jingleChangeToAudio(new JingleData(jingleData
//					.getFrom(), preferences.getString(Constants.USER_ACCOUNT,
//							""), JingleActionEnum.CONTENT_REMOVE, true, false,
//					jingleData.getSid()));
//		}
//		handler.sendEmptyMessage(Constants.CHANAGE_CHAT_TYPE_TO_AUDIO);
//	}
//
//	/**
//	 * 组装切换所需要的jingle
//	 *
//	 * @param isAudio
//	 * @param isVideo
//	 * @param action
//	 * @return
//	 */
//
//	JingleData getChangeMediaJingledata(boolean isAudio, boolean isVideo,
//			JingleActionEnum action) {
//		JingleData jinglecData = null;
//
//		jinglecData = new JingleData(bean.getJidWithNoTag(), action, isAudio,
//				isVideo, mybean.getSid(), mybean.getCell(), mybean.getHead(),
//				mybean.getNick());
//
//		return jinglecData;
//	}
//
//	/**
//	 * 接受音视频操作的广播接收器
//	 *
//	 * @author Administrator
//	 *
//	 */
//	private class mMessageReceiver extends BroadcastReceiver {
//
//		@SuppressLint("NewApi")
//		@Override
//		public void onReceive(Context context, Intent intent) {
//
//			if (intent.getAction().equals(
//					Constants.DIALER_REQUEST_JINGGE_ACCEPT_ACTION)) {
//				LogUtil.i("#########", "被叫");
//
//				// 关闭铃音
//				HandleMedia(VideoActivity.this, Constants.PlayerMag.STOP_MUSIC);
//				// 接听来电
//				isagree = true;
//				handler.sendEmptyMessage(Constants.START_CHAT);
//				showdoodlePop();
//			} else if (intent
//					.getAction()
//					.equals(Constants.DIALER_REQUEST_JINGGE_CONTENT_ACCEPT_AUDIO_ACTION)) {
//				// 视频切成音频
//				handler.sendEmptyMessage(Constants.CHANAGE_CHAT_TYPE_TO_AUDIO);
//			} else if (intent
//					.getAction()
//					.equals(Constants.DIALER_REQUEST_JINGGE_CONTENT_ACCEPT_VIDEO_ACTION)) {
//				// 音频切换为视频 弹出 选择框
//				changeJingleData = (JingleData) intent
//						.getSerializableExtra(Constants.JINGLLDATA);
//				LogUtil.i("changeJingleData", changeJingleData.toString());
//				if (isagree && !centerWindow.isShowing()) {
//					Tools.StartMusicService(getApplicationContext(),
//							Constants.PlayerMag.PLAY_MUSIC, false);
//					noticeMe(false, true);
//					nickTv.setText(nickName + noticeStr);
//					centerWindow.showAtLocation(mainView, Gravity.BOTTOM, 0,
//							200);
//					titleHeadLayout.setVisibility(View.INVISIBLE);
//					cendHeadLayout.setVisibility(View.VISIBLE);
//					cendHeadLayout.startAnimation(showAction);
//					cendHeadLayout.bringToFront();
//					callholdLayout.getBackground().setAlpha(255);
//					if (doodle_Pop.isShowing()) {
//						doodle_Pop.dismiss();
//					}
//				}
//
//			} else if (intent
//					.getAction()
//					.equals(Constants.DIALER_REQUEST_JINGGE_CONTENT_ACCEPT_CHANGE_ACTION)) {
//				// 同意切换媒体
//				// if (BaseApplicasion.isVideo) {
//				// handler.sendEmptyMessage(Constants.CHANAGE_CHAT_TYPE_TO_AUDIO);
//				// } else {
//
//				handler.sendEmptyMessage(Constants.CHANAGE_CHAT_TYPE_TO_VIDEO);
//				// }
//
//			} else if (intent.getAction().equals(
//					Constants.DIALER_REQUEST_JINGGE_SESION_TERMINATE)) {
//				// 对方主动挂断
//				LogUtil.i("挂断", "界面接受");
//				chronometer.stop();
//				HandleMedia(VideoActivity.this, Constants.PlayerMag.STOP_MUSIC);
//				if (!isCallOut) {
//					// 主叫挂断 的提示（提示被叫）
//					ToastUtils.showDefaultToast(VideoActivity.this, "通话已结束...");
//				} else if (isCallOut && !isagree) {
//					// 被叫挂断 的提示 切正在打电话时（提示主叫）
//					ToastUtils.showDefaultToast(VideoActivity.this, "已挂断...");
//				}
//				if (isCharge) {
//					termineatToCharge(3);
//				}
//				terminate();
//			} else if (intent.getAction().equals(
//					ConnectivityManager.CONNECTIVITY_ACTION)) {
//				ConnectivityManager connectivityManager = (ConnectivityManager) context
//						.getSystemService(Context.CONNECTIVITY_SERVICE);
//				NetworkInfo netInfo = connectivityManager
//						.getActiveNetworkInfo();
//				if (netInfo == null) {
//					ToastUtils.showToast(VideoActivity.this, "无网络链接");
//
//				}
//			} else if (Constants.DIALER_REQUEST_JINGGE_CONTENT_TRANSPORT_ACTION
//					.equals((intent.getAction()))) {
//				JingleData jingleData = (JingleData) intent
//						.getSerializableExtra(Constants.JINGLLDATA);
//				getPayLoadType(jingleData);
//			} else if (Constants.GET_PUBLIC_DANMU_MSG
//					.equals(intent.getAction())) {
//				setPmd(intent.getStringExtra("data"));
//
//			} else if (Constants.GET_PRIVTAE_DANMU_MSG.equals(intent
//					.getAction())) {
//
//				if (BaseApplicasion.isVideo) {
//					centerContainer.bringToFront();
//					setCenterDanmu(intent.getStringExtra("data"));
//				}
//			} else if (Constants.DOODLE_CHANGE_BACKGROUND.equals(intent
//					.getAction())) {
//
//				DoodleData doodleData = (DoodleData) intent
//						.getSerializableExtra("doodledata");
//				byte[] bt = FileUtils.decodeByteFromBase64(doodleData
//						.getBackground());
//				Bitmap bmp = BitmapFactory.decodeByteArray(bt, 0, bt.length);
//
//				doolelayout.setImageBitmap(bmp);
//
//			} else if (Constants.DOODLE_PLAYING.equals(intent.getAction())) {
//
//				DoodleData doodleData = (DoodleData) intent
//						.getSerializableExtra("doodledata");
//
//				remoteHight = doodleData.hight;
//				remoteWidth = doodleData.width;
//				float scanlx = sWidth / doodleData.width;
//				float scanly = sHight / doodleData.hight;
//				LogUtil.i(TAG, scanlx + "@" + doodleData.width + "#" + sWidth);
//
//				realscal = Math.min(scanlx, scanly);
//
//				List<DoodlePoint> list = doodleData.getPointList();
//				if (list == null || list.size() == 0) {
//					LogUtil.i(TAG, "接受到数据为空!!!!!!!!!!!!!!!!!");
//					return;
//				}
//				if (doodle_Pop.isShowing()) {
//					doodle_Pop.dismiss();
//				}
//
//				if (!doodleData.isEraser()) {
//					mDoodle.setPaint(getRemotePaint(doodleData.getPaintColor(),
//							doodleData.getPaintSize()));
//				} else {
//					mDoodle.setPaint(getRemoteEraser(doodleData.getPaintSize()));
//				}
//				mDoodle.setCurAction(list.get(0).getX(), list.get(0).getY(),
//						scanlx, scanly, doodleData.getIqid(),
//						doodleData.getFrom());
//				for (int i = 1; i < list.size(); i++) {
//					LogUtil.i("所有点", list.get(i).getX() + "@"
//							+ list.get(i).getY());
//					mDoodle.actionDraw(list.get(i), scanlx, scanly);
//				}
//			} else if (Constants.DOODLE_GO_FORWARD.equals(intent.getAction())) {
//				DoodleData doodleData = (DoodleData) intent
//						.getSerializableExtra("doodledata");
//				mDoodle.goForward(doodleData.getFrom());
//			} else if (Constants.DOODLE_RETURN.equals(intent.getAction())) {
//
//				DoodleData doodleData = (DoodleData) intent
//						.getSerializableExtra("doodledata");
//
//				mDoodle.back(doodleData.getIqid(), "");
//			} else if (Constants.DOODLE_CLEALER.equals(intent.getAction())) {
//				DoodleData doodleData = (DoodleData) intent
//						.getSerializableExtra("doodledata");
//				mDoodle.clean(doodleData.getFrom());
//			}
//
//			if (intent.hasExtra("state")) {
//				if (0 == intent.getIntExtra("state", 0)) {
//					setAudioMode();
//					waifbtn.setImageResource(R.drawable.waifaneg_off);
//					Tools.OpenSpeaker(VideoActivity.this);
//				} else if (1 == intent.getIntExtra("state", 0)) {
//					waifbtn.setImageResource(R.drawable.waifang_on);
//					Tools.CloseSpeaker(VideoActivity.this);
//					Toast.makeText(context, "耳机已插入", Toast.LENGTH_SHORT).show();
//				}
//			}
//
//		}
//	}
//
//	// 开始通话记时
//	void startTime() {
//		chronometer.setBase(SystemClock.elapsedRealtime());
//		chronometer.start();
//	}
//
//	// 结束通话 释放对象
//	void endCall() {
//		if (isagree) {
//			if (BaseApplicasion.isVideo) {
//				sdkCore.stopMediaStreams(sdkMedia.MEDIA_TYPE_AUDIO_VIDEO);
//			} else {
//				sdkCore.stopMediaStreams(sdkMedia.MEDIA_TYPE_ONLY_AUDIO);
//			}
//			sdkCore.callEnd();
//		}
//		BaseApplicasion.iscallIng = false;
//	}
//
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		//		if (mDoodle != null) {
//		//			mDoodle.setZOrderOnTop(false);
//		//			mDoodle.getHolder().setFormat(PixelFormat.OPAQUE);
//		//			mDoodle=null;
//		//		}
//		endCall();
//		if (!isCallOut) {
//			countDownTimer.cancel();
//			mWakeLock.release();
//		}
//		audioManager.setMode(AudioManager.MODE_NORMAL);
//		unregisterReceiver(mMessageReceiver);
//		handler.removeCallbacks(terminateRunnable);
//		stopService(new Intent(VideoActivity.this, PlayerService.class));
//	}
//
//	Runnable terminateRunnable = new Runnable() {
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//
//			if (!VideoActivity.this.isFinishing()) {
//				VideoActivity.this.finish();
//			}
//		}
//	};
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		int vioceType = 0;
//		if (!isagree) {
//			vioceType = AudioManager.STREAM_MUSIC;
//		} else {
//			vioceType = AudioManager.STREAM_VOICE_CALL;
//		}
//		switch (keyCode) {
//		case KeyEvent.KEYCODE_VOLUME_UP:
//			audioManager.adjustStreamVolume(vioceType,
//					AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND
//					| AudioManager.FLAG_SHOW_UI);
//			return true;
//		case KeyEvent.KEYCODE_VOLUME_DOWN:
//			audioManager.adjustStreamVolume(vioceType,
//					AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND
//					| AudioManager.FLAG_SHOW_UI);
//			return true;
//		case KeyEvent.KEYCODE_BACK:
//			if (isagree) {
//				ToastUtils.showDefaultToast(this, "正在通话中...");
//			}
//			break;
//		default:
//			break;
//		}
//
//		return false;
//	}
//
//	private void initPopupWindow() {
//		innitGiftData();
//		presentView = this.getLayoutInflater().inflate(
//				R.layout.popuwindow_songli, null);
//		viewPager = (TagViewPager) presentView.findViewById(R.id.myviewpager);
//		final List<View> presentList = new ArrayList<View>();
//		View a = VideoActivity.this.getLayoutInflater().inflate(
//				R.layout.songli_item, null);
//		NoScroolGridView gridView1 = (NoScroolGridView) a
//				.findViewById(R.id.grid);
//		View b = VideoActivity.this.getLayoutInflater().inflate(
//				R.layout.songli_item, null);
//		NoScroolGridView gridView2 = (NoScroolGridView) b
//				.findViewById(R.id.grid);
//		View c = VideoActivity.this.getLayoutInflater().inflate(
//				R.layout.songli_item, null);
//		NoScroolGridView gridView3 = (NoScroolGridView) c
//				.findViewById(R.id.grid);
////		View d = VideoActivity.this.getLayoutInflater().inflate(
////				R.layout.songli_item, null);
////		NoScroolGridView gridView4 = (NoScroolGridView) d
////				.findViewById(R.id.grid);
//		final SongliAdapter padapter1 = new SongliAdapter(VideoActivity.this,
//				present1);
//		final SongliAdapter padapter2 = new SongliAdapter(VideoActivity.this,
//				present2);
//		final SongliAdapter padapter3 = new SongliAdapter(VideoActivity.this,
//				present3);
////		final SongliAdapter padapter4 = new SongliAdapter(VideoActivity.this,
////				present4);
//		gridView1.setAdapter(padapter1);
//		gridView1.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				present1.get(arg2).setSelect(true);
//				for (int i = 0; i < present1.size(); i++) {
//					if (i != arg2) {
//						present1.get(i).setSelect(false);
//					}
//				}
//				for (int j = 0; j < present2.size(); j++) {
//					present2.get(j).setSelect(false);
//				}
//				for (int j = 0; j < present3.size(); j++) {
//					present3.get(j).setSelect(false);
//				}
////				for (int j = 0; j < present4.size(); j++) {
////					present4.get(j).setSelect(false);
////				}
//				padapter1.refresh(present1);
//				padapter2.refresh(present2);
//				padapter3.refresh(present3);
//		//		padapter4.refresh(present4);
//				liwu = present1.get(arg2);
//				gift_much.setText(StringUtills.getPresentMuch(liwu));
//			}
//		});
//		gridView2.setAdapter(padapter2);
//		gridView2.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				present2.get(arg2).setSelect(true);
//				for (int i = 0; i < present2.size(); i++) {
//					if (i != arg2) {
//						present2.get(i).setSelect(false);
//					}
//				}
//				for (int j = 0; j < present1.size(); j++) {
//					present1.get(j).setSelect(false);
//				}
//				for (int j = 0; j < present3.size(); j++) {
//					present3.get(j).setSelect(false);
//				}
////				for (int j = 0; j < present4.size(); j++) {
////					present4.get(j).setSelect(false);
////				}
//				padapter1.refresh(present1);
//				padapter2.refresh(present2);
//				padapter3.refresh(present3);
//			//	padapter4.refresh(present4);
//				liwu = present2.get(arg2);
//				gift_much.setText(StringUtills.getPresentMuch(liwu));
//			}
//		});
//		gridView3.setAdapter(padapter3);
//		gridView3.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				present3.get(arg2).setSelect(true);
//				for (int i = 0; i < present3.size(); i++) {
//					if (i != arg2) {
//						present3.get(i).setSelect(false);
//					}
//				}
//				for (int j = 0; j < present1.size(); j++) {
//					present1.get(j).setSelect(false);
//				}
//				for (int j = 0; j < present2.size(); j++) {
//					present2.get(j).setSelect(false);
//				}
////				for (int j = 0; j < present4.size(); j++) {
////					present4.get(j).setSelect(false);
////				}
//				padapter1.refresh(present1);
//				padapter2.refresh(present2);
//				padapter3.refresh(present3);
//				//padapter4.refresh(present4);
//				liwu = present3.get(arg2);
//				gift_much.setText(StringUtills.getPresentMuch(liwu));
//			}
//		});
////		gridView4.setAdapter(padapter4);
////		gridView4.setOnItemClickListener(new OnItemClickListener() {
////			@Override
////			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
////					long arg3) {
////				// TODO Auto-generated method stub
////				present4.get(arg2).setSelect(true);
////				for (int i = 0; i < present4.size(); i++) {
////					if (i != arg2) {
////						present4.get(i).setSelect(false);
////					}
////				}
////				for (int j = 0; j < present1.size(); j++) {
////					present1.get(j).setSelect(false);
////				}
////				for (int j = 0; j < present2.size(); j++) {
////					present2.get(j).setSelect(false);
////				}
////				for (int j = 0; j < present3.size(); j++) {
////					present3.get(j).setSelect(false);
////				}
////				padapter1.refresh(present1);
////				padapter2.refresh(present2);
////				padapter3.refresh(present3);
////				padapter4.refresh(present4);
////				liwu = present4.get(arg2);
////				gift_much.setText(StringUtills.getPresentMuch(liwu));
////			}
////		});
//		// ImageView iv=new ImageView(VideoActivity.this);
//		// Imageloader_homePager.displayImage(list.get(position), iv,
//		// new Handler(), null);
//		presentList.add(a);
//		presentList.add(b);
//		presentList.add(c);
//	//	presentList.add(d);
//		gift_much = (TextView) presentView.findViewById(R.id.songli_much);
//		songli_yd = (TextView) presentView.findViewById(R.id.songli_yd);
//		viewPager.init(R.drawable.songli_hong, R.drawable.songli_hui, 20, 5, 2,
//				20);
//		// viewPager.setAutoNext(false, 500);
//		viewPager.setOnGetView(new OnGetView() {
//			@Override
//			public View getView(ViewGroup container, int position) {
//				// TODO Auto-generated method stub
//
//				container.addView(presentList.get(position));
//
//				return presentList.get(position);
//			}
//		});
//		viewPager.setAdapter(presentList.size());
//
//		songliPop = new PopupWindow(presentView, LayoutParams.MATCH_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		presentView.findViewById(R.id.songli_topup).setOnClickListener(
//				new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(VideoActivity.this,
//								TopUpActivity.class);
//						VideoActivity.this.startActivity(intent);
//					}
//				});
//		presentView.findViewById(R.id.songli_yes).setOnClickListener(
//				new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						if (liwu != null) {
//							int a = 0;
//							for (int i = 0; i < present1.size(); i++) {
//								if (present1.get(i).isSelect()) {
//									a = present1.get(i).getGid();
//								}
//							}
//							for (int j = 0; j < present2.size(); j++) {
//								if (present2.get(j).isSelect()) {
//									a = present2.get(j).getGid();
//								}
//							}
//							for (int m = 0; m < present3.size(); m++) {
//								if (present3.get(m).isSelect()) {
//									a = present3.get(m).getGid();
//								}
//							}
////							for (int n = 0; n < present4.size(); n++) {
////								if (present4.get(n).isSelect()) {
////									a = present4.get(n).getGid();
////								}
////							}
//							if (a == liwu.getGid()) {
//								if (bean != null) {
//									sendgift(liwu.getGid() + "", liwu.getNum(),
//											bean.getUid() + "");
//								} else {
//									sendgift(liwu.getGid() + "", liwu.getNum(),
//											oid + "");
//								}
//							}
//						}
//
//					}
//				});
//		// presentView.findViewById(R.id.songli_no).setOnClickListener(
//		// new OnClickListener() {
//		//
//		// @Override
//		// public void onClick(View arg0) {
//		// // TODO Auto-generated method stub
//		// for (int i = 0; i < present1.size(); i++) {
//		// present1.get(i).setSelect(false);
//		// }
//		// for (int j = 0; j < present2.size(); j++) {
//		// present2.get(j).setSelect(false);
//		// }
//		// for (int j = 0; j < present3.size(); j++) {
//		// present2.get(j).setSelect(false);
//		// }
//		// for (int j = 0; j < present4.size(); j++) {
//		// present2.get(j).setSelect(false);
//		// }
//		// liwu = null;
//		// padapter1.refresh(present1);
//		// padapter2.refresh(present2);
//		// padapter2.refresh(present3);
//		// padapter2.refresh(present4);
//		// gift_much.setText("");
//		// }
//		// });
//
//		songliPop.setOutsideTouchable(true);
//		songliPop.setFocusable(true);
//		songliPop.setBackgroundDrawable(getResources().getDrawable(
//				R.color.black3));
//		songliPop.setOnDismissListener(new OnDismissListener() {
//
//			@Override
//			public void onDismiss() {
//				// TODO Auto-generated method stub
//				Tools.backgroundAlpha(VideoActivity.this, 1f);
//				liwu = null;
//				for (int i = 0; i < present1.size(); i++) {
//					present1.get(i).setSelect(false);
//				}
//				for (int j = 0; j < present2.size(); j++) {
//					present2.get(j).setSelect(false);
//				}
//				for (int j = 0; j < present3.size(); j++) {
//					present3.get(j).setSelect(false);
//				}
////				for (int j = 0; j < present4.size(); j++) {
////					present4.get(j).setSelect(false);
////				}
//				padapter1.refresh(present1);
//				padapter2.refresh(present2);
//				padapter3.refresh(present3);
//	//			padapter4.refresh(present4);
//				gift_much.setText("");
//			}
//		});
//		presentView.setOnKeyListener(new OnKeyListener() {// 手机键盘上的返回键
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				switch (keyCode) {
//				case KeyEvent.KEYCODE_BACK:
//					if (songliPop != null && songliPop.isShowing()) {
//						songliPop.dismiss();
//					}
//					break;
//				}
//				return true;
//			}
//		});
//	}
//
//	private void initDoodleWindow() {
//
//		view_doodle = this.getLayoutInflater().inflate(R.layout.tuya_pop, null);
//		doodle_Pop = new PopupWindow(view_doodle, LayoutParams.MATCH_PARENT,
//				LayoutParams.WRAP_CONTENT);
//
//		pager = (HomeViewPager) view_doodle.findViewById(R.id.tuya_pager);
//		view1 = this.getLayoutInflater().inflate(R.layout.tuya_pager_tiem1,
//				null);
//		view2 = this.getLayoutInflater().inflate(R.layout.tuya_pager_tiem2,
//				null);
//		view1.findViewById(R.id.tuya_return).setOnClickListener(this);
//		view1.findViewById(R.id.tuya_forward).setOnClickListener(this);
//		view1.findViewById(R.id.tuya_cleanall).setOnClickListener(this);
//		strokImg = (RoundImg) view1.findViewById(R.id.stroke_width_img);
//		tuya_xiangpi_img = (ImageView) view1
//				.findViewById(R.id.tuya_xiangpi_img);
//		tuya_but1 = (RelativeLayout) view_doodle.findViewById(R.id.tuya_but1);
//		tuya_but3 = (RelativeLayout) view_doodle.findViewById(R.id.tuya_but3);
//		tuya_but1.setOnClickListener(this);
//		tuya_but3.setOnClickListener(this);
//		view1.findViewById(R.id.tuya_xiangpi).setOnClickListener(this);
//		view_doodle.findViewById(R.id.tuya_but2).setOnClickListener(this);
//		view_doodle.findViewById(R.id.tuya_but3).setOnClickListener(this);
//		view_doodle.findViewById(R.id.tuya_but4).setOnClickListener(this);
//		bar = (SeekBar) view1.findViewById(R.id.tuya_seekBar);
//		bar.setProgress(mySize);
//		HorizontialListView list_doodle = (HorizontialListView) view1
//				.findViewById(R.id.tuya_list);
//		HorizontialListView list_doodle2 = (HorizontialListView) view2
//				.findViewById(R.id.tuya_list2);
//		colors = this.getResources().getStringArray(R.array.tuya_color);
//		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//
//			@Override
//			public void onStopTrackingTouch(SeekBar arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onStartTrackingTouch(SeekBar arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
//				// TODO Auto-generated method stub
//				if (arg1 < 5) {
//					arg1 = 5;
//				}
//				mySize = arg1;
//				if (strokImg != null) {
//					strokImg.setChange(myColor,
//							Tools.px2sp(getApplicationContext(), mySize)
//							* realscal);
//				}
//
//			}
//		});
//		tuyas = new ArrayList<TuyaBean>();
//		tuyas2 = new ArrayList<TuyaBean2>();
//
//		tuyas2.add(new TuyaBean2(R.drawable.huaban_1, true));
//		tuyas2.add(new TuyaBean2(R.drawable.huaban_2, false));
//		tuyas2.add(new TuyaBean2(R.drawable.huaban_3, false));
//		adapter_doodle = new TuyaAdapter(VideoActivity.this, tuyas);
//		adapter_doodle2 = new TuyaAdapter2(VideoActivity.this, tuyas2);
//		list_doodle.setAdapter(adapter_doodle);
//		list_doodle2.setAdapter(adapter_doodle2);
//		list_doodle.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				for (int i = 0; i < tuyas.size(); i++) {
//					if (i == arg2) {
//						isEraser = false;
//						tuya_xiangpi_img
//						.setImageResource(R.drawable.tuya_xiangpica);
//						tuyas.get(i).setChecked(true);
//						myColor = tuyas.get(i).getColor();
//					} else {
//						tuyas.get(i).setChecked(false);
//					}
//					adapter_doodle.notifyDataSetChanged();
//				}
//				if (strokImg != null) {
//					strokImg.setChange(myColor,
//							Tools.px2sp(getApplicationContext(), mySize));
//				}
//			}
//		});
//		list_doodle2.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				for (int i = 0; i < tuyas2.size(); i++) {
//					if (i == arg2) {
//						mDoodle.setPaint(getDefaultPaint());
//						tuyas2.get(i).setChecked(true);
//						doolelayout.setBackgroundResource(tuyas2.get(i)
//								.getBack());
//						final int a = i;
//						new Thread(new Runnable() {
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								Bitmap bitmap = PictureUtil.getBitmapFrom(
//										BitmapFactory.decodeResource(
//												getResources(), tuyas2.get(a)
//												.getBack()), 40);
//								doodleSendbackGround(bitmap);
//							}
//						}).start();
//
//					} else {
//						tuyas2.get(i).setChecked(false);
//					}
//					adapter_doodle2.notifyDataSetChanged();
//				}
//			}
//		});
//		pageViews.add(view1);
//		pageViews.add(view2);
//		pager.setAdapter(new GuidePageAdapter());
//		pager.setOnPageChangeListener(new GuidePageChangeListener());
//		doodle_Pop.setOutsideTouchable(true);
//		doodle_Pop.setFocusable(true);
//		doodle_Pop.setBackgroundDrawable(getResources().getDrawable(
//				R.drawable.lkcolor));
//		doodle_Pop.setOnDismissListener(new OnDismissListener() {
//			@Override
//			public void onDismiss() {
//				// TODO Auto-generated method stub
//				doodleImg.setImageResource(R.drawable.tuya);
//			}
//		});
//		if (StringUtills.isNULL(myColor)) {
//			if (isCallOut) {
//				myColor = colors[3];
//			} else {
//				myColor = colors[4];
//			}
//			for (int i = 0; i < colors.length; i++) {
//				if (myColor.equals(colors[i])) {
//					tuyas.add(new TuyaBean(colors[i], true));
//				} else {
//					tuyas.add(new TuyaBean(colors[i], false));
//				}
//			}
//			adapter_doodle.notifyDataSetChanged();
//		}
//		strokImg.setChange(myColor, mySize);
//	}
//
//	protected void doodleSendbackGround(final Bitmap bitmap) {
//		// TODO Auto-generated method stub
//
//		doodleData = new DoodleData(FileUtils.decodeBitmapToBase64(bitmap),
//				Constants.DOODLE_CHANGE_BACKGROUND);
//		sendDool(doodleData);
//
//	}
//
//	class GuidePageAdapter extends PagerAdapter {
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return pageViews.size();
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			return arg0 == arg1;
//		}
//
//		@Override
//		public void destroyItem(View container, int position, Object object) {
//			// TODO Auto-generated method stub
//			((ViewPager) container).removeView(pageViews.get(position));
//		}
//
//		@Override
//		public Object instantiateItem(View arg0, int arg1) {
//
//			// TODO Auto-generated method stub
//			((ViewPager) arg0).addView(pageViews.get(arg1));
//			return pageViews.get(arg1);
//
//		}
//	}
//
//	// page监听
//	class GuidePageChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageScrollStateChanged(int arg0) {
//			// TODO Auto-generated method stub
//		}
//
//		@Override
//		public void onPageScrolled(int position, float arg1,
//				int positionOffsetPixels) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onPageSelected(int arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//	}
//
//	private void changeTuyaBut(int tuya_choise) {
//		switch (tuya_choise) {
//		case 0:
//			tuya_but1.setBackgroundResource(R.color.tuya);
//			tuya_but3.setBackgroundResource(R.color.black);
//			break;
//		case 1:
//			tuya_but1.setBackgroundResource(R.color.black);
//			tuya_but3.setBackgroundResource(R.color.tuya);
//			break;
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * 弹幕
//	 */
//	private void initTopPmdView() {
//		// TODO Auto-generated method stub
//		TextView textView = new TextView(this);
//		textView.setTextSize(15);
//		textView.setPadding(10, 3, 10, 3);
//		paint = textView.getPaint();
//		TextView centertV = new TextView(this);
//		centertV.setTextSize(18);
//		centertV.setPadding(10, 3, 10, 3);
//		centerPaint = centertV.getPaint();
//
//		container = (RelativeLayout) findViewById(R.id.danmuContainer);
//		container.setOnClickListener(this);
//		ViewGroup.LayoutParams lp = container.getLayoutParams();
//		lp.width = Tools.sp2px(this, 15) * 100;
//		container.setLayoutParams(lp);
//
//		centerContainer = (RelativeLayout) findViewById(R.id.danmu_center_Container);
//		// 设置单行宽度
//		danmuHeight = (int) (Tools.sp2px(this, 18) * 1.5);
//		ViewGroup.LayoutParams lpcenter = centerContainer.getLayoutParams();
//		lpcenter.width = Tools.sp2px(this, 18) * 100;
//		centerContainer.setLayoutParams(lpcenter);
//
//		for (int i = 0; i < totalCount; i++) {
//			sendPosition.put(i, false);
//		}
//
//	}
//
//	private void setPmd(String text) {
//
//		final TopPmd danmu = new TopPmd(this, sWidth,
//				(int) -paint.measureText(text));
//		danmu.setTextSize(cenTerDanmuSize);
//		danmu.setPadding(10, 5, 10, 5);
//		danmu.setText(text);
//		danmu.setTextColor(Color.parseColor("#fffa7a"));
//		danmu.setOnAnimationEndListener(new TopPmd.OnAnimationEndListener() {
//			@Override
//			public void clearPosition() {
//				isTopOk = false;
//				handler.sendEmptyMessage(Constants.PMD_NOW_TAG);
//			}
//
//			@Override
//			public void animationEnd() {
//				container.removeView(danmu);
//				if (pmdList.size() != 0) {
//					pmdList.remove(0);
//					if (current > 0) {
//						current--;
//					}
//				}
//			}
//
//		});
//
//		if (isLooping && !sendPosition.get(0)) {
//			LayoutParams lp = new LayoutParams(
//					LayoutParams.WRAP_CONTENT,
//					LayoutParams.MATCH_PARENT);
//			lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//			danmu.setGravity(Gravity.CENTER);
//			if (danmu.getParent() == null) {
//				container.addView(danmu, lp);
//				danmu.send();
//				isTopOk = true;
//			}
//
//		} else {
//			pmdList.add(danmu);
//		}
//		if (!isLooping)
//			new Thread(addRunable).start();
//
//	}
//
//	Runnable addRunable = new Runnable() {
//
//		public void run() {
//			while (pmdList.size() != 0) {
//				isLooping = true;
//				handler.sendEmptyMessage(Constants.PMD_LOOPING_TAG);
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (pmdList.size() == 0) {
//				isLooping = false;
//			}
//		}
//	};
//
//	public void sendgift(String gid, final int num, String to) {
//		new NetTaskForResult(this, InterfaceMethod.set55(mybean.getUid() + "",
//				to.split("@")[0] + "", to, gid, num + "",
//				preferences.getString(Constants.USER_HEAD, ""),
//				preferences.getString(Constants.USER_NICK, "")),
//				new OnResultCallback() {
//
//			@Override
//			public void onCallback(ResultBean bean) {
//				// TODO Auto-generated method stub
//				Log.i("resultbean", bean.toString());
//				if (bean.getErr() == 0) {
//					ToastUtils.showToast(VideoActivity.this, "赠送成功");
//					List<String> result = (List<String>) bean
//							.getResultParam(String.class);
//					try {
//						org.json.JSONObject jsonObject = new org.json.JSONObject(
//								result.get(0));
//						LogUtil.i("result", jsonObject.toString());
//						String outgifts = jsonObject
//								.getString("outgifts");
//						String fame = jsonObject.getString("fame");
//						int coin = jsonObject.getInt("coin");
//						editor = preferences.edit();
//						editor.putString(Constants.USER_FAME, fame);
//						editor.putString(Constants.USER_OUT_GIFTS,
//								outgifts);
//						editor.putInt(Constants.USER_COIN, coin);
//						editor.commit();
//						songliPop.dismiss();
//					} catch (JSONException e) {
//						// TODO: handle exception
//					}
//				} else {
////					Map<String, String> map = (Map<String, String>) bean
////							.getResultParam(Map.class).get(0);
////					ToastUtils.showToast(VideoActivity.this,
////							map.get("data"));
//					ToastUtils.showData(VideoActivity.this, bean);
//				}
//			}
//		}).execute();
//	}
//
//	private void setCenterDanmu(String text) {
//
//		final TopPmd danmu = new TopPmd(this, sWidth,
//				(int) -centerPaint.measureText(text));
//		danmu.setTextSize(cenTerDanmuSize);
//		danmu.setBackgroundResource(R.drawable.danmu_text);
//		danmu.setText(text);
//		danmu.setTextColor(Color.parseColor("#4fffffff"));
//		danmu.setPadding(10, 3, 10, 3);
//		danmu.setOnAnimationEndListener(new TopPmd.OnAnimationEndListener() {
//			@Override
//			public void clearPosition() {
//				sendPosition.put(danmu.getPosition(), false);
//			}
//
//			@Override
//			public void animationEnd() {
//
//				centerContainer.removeView(danmu);
//			}
//
//		});
//
//		for (int i = 0; i < totalCount; i++) {
//			if (sendPosition.get(i) == false) {
//				danmu.setPosition(i);
//				LayoutParams lp = new LayoutParams(
//						LayoutParams.WRAP_CONTENT, danmuHeight);
//				lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//				lp.topMargin = i * danmuHeight;
//				danmu.setGravity(Gravity.CENTER);
//				centerContainer.addView(danmu, lp);
//				danmu.send();
//				sendPosition.put(i, true);
//				break;
//			}
//
//		}
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		LogUtil.i("手势", "!!!" + menuShowed);
//		if (menuShowed && isagree && !BaseApplicasion.isVideo) {
//			if (!isEraser) {
//				mDoodle.setPaint(getDefaultPaint());
//			} else {
//				mDoodle.setPaint(getRemoteEraser(mySize + ""));
//			}
//			return mDoodle.onTouchEvent(event);
//		}
//		if (songliPop != null && songliPop.isShowing()) {
//			songliPop.dismiss();
//		}
//		return super.onTouchEvent(event);
//	}
//
//	@Override
//	public void onActionDown(DoodlePoint point, Action action) {
//		// TODO Auto-generated method stub
//		doodleData = new DoodleData();
//		doodleData.width = sWidth;
//		doodleData.hight = sHight;
//		doodleData.setEraser(isEraser);
//		doodleData.setPaintColor(myColor);
//		if (realscal < 1) {
//			doodleData.setPaintSize((int) (mySize / realscal) + "");
//		} else {
//			doodleData.setPaintSize(mySize + "");
//		}
//		doodleData.setPointList(new ArrayList<DoodlePoint>());
//		doodleData.setIqid(FileUtils.generateFileName());
//		doodleData.setAction(Constants.DOODLE_PLAYING);
//		doodleData.setFrom(mybean.getJidWithNoTag());
//		doodleData.getPointList().add(point);
//		action.setID(doodleData.getIqid());
//		action.setFrom(doodleData.getFrom());
//
//	}
//
//	@Override
//	public void onActionMove(DoodlePoint point) {
//		// TODO Auto-generated method stub
//		if (doodleData != null) {
//			List list = doodleData.getPointList();
//			if (list == null) {
//				doodleData.setPointList(new ArrayList<DoodlePoint>());
//			}
//			doodleData.getPointList().add(point);
//		}
//	}
//
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//
//	}
//
//	@Override
//	protected void onRestart() {
//		// TODO Auto-generated method stub
//		super.onRestart();
//	}
//
//	@Override
//	public void onActionUp() {
//		// TODO Auto-generated method stub
//		sendDool(doodleData);
//	}
//
//	private void sendDool(DoodleData doodleData) {
//		// TODO Auto-generated method stub
//		String str = JSON.toJSONString(doodleData).toString();
//		LogUtil.i("点参数", str);
//		String to = "";
//		String from = mybean.getJidWithNoTag();
//		if (jingleData == null) {
//			to = bean.getJidWithNoTag();
//		} else {
//			to = jingleData.getFrom();
//		}
//		com.yunlian.yudao.xmpp.doodle.Doodle dool = new com.yunlian.yudao.xmpp.doodle.Doodle(
//				str, to, from);
//		dool.setPacketID(doodleData.getIqid());
//		XMPPManager.getInstance().getConnection().sendPacket(dool);
//	}
//
//	// 定時 發起收費 請求
//	TextWatcher timeTextSwticher = new TextWatcher() {
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before,
//				int count) {
//			// TODO Auto-generated method stub
//			LogUtil.i("ssssssss", s.toString());
//			long time = 0;
//			long minute = 0;
//			if (s != null && s.toString().contains(":")) {
//				String str[] = s.toString().split(":");
//				time = Long.valueOf(str[1]);
//				minute = Long.valueOf(str[0]) * 60;
//				LogUtil.i("分钟", minute);
//			}
//			if (time == 0 || !isCallOut) {
//				return;
//			}
//			if (((minute == 0) && time == 10) || (minute + time) % 60 == 0) {
//				LogUtil.i("一分钟一次", "########################" + time
//						+ "!!!!!!!" + (time % 60 == 0) + isCharge);
//				if (isCharge) {
//					charge();
//				}
//			}
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count,
//				int after) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			// TODO Auto-generated method stub
//
//		}
//	};
//
//	/**
//	 * 默认画笔
//	 *
//	 * @return
//	 */
//	private Paint getDefaultPaint() {
//		Paint paint = getPaintBasic();
//		paint.setAntiAlias(true);
//		paint.setDither(true);
//		paint.setColor(Color.parseColor(myColor));
//		return paint;
//	}
//
//	/**
//	 * 获取 对方的paint
//	 *
//	 * @param color
//	 * @param size
//	 * @return
//	 */
//	private Paint getRemotePaint(String color, String size) {
//		Paint paint = new Paint();
//		paint.setStrokeWidth(Float.valueOf(size));
//		paint.setStyle(Paint.Style.STROKE);
//		paint.setStrokeJoin(Paint.Join.ROUND);
//		paint.setStrokeCap(Paint.Cap.ROUND);
//		paint.setAntiAlias(true);
//		paint.setDither(true);
//		paint.setColor(Color.parseColor(color));
//		return paint;
//	};
//
//	/**
//	 * 橡皮擦
//	 *
//	 * @return
//	 */
//	private Paint getRemoteEraser(String size) {
//		Paint paint = new Paint();
//		paint.setStrokeWidth(Float.valueOf(size));
//		paint.setStyle(Paint.Style.STROKE);
//		paint.setStrokeJoin(Paint.Join.ROUND);
//		paint.setStrokeCap(Paint.Cap.ROUND);
//		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
//		paint.setColor(Color.TRANSPARENT);
//		paint.setAlpha(0);
//		paint.setAntiAlias(false);
//		return paint;
//	}
//
//	private Paint getPaintBasic() {
//
//		Paint paint = new Paint();
//		if (realscal < 1) {
//			paint.setStrokeWidth(mySize);
//		} else {
//			paint.setStrokeWidth(mySize * realscal);
//		}
//
//		paint.setStyle(Paint.Style.STROKE);
//		paint.setStrokeJoin(Paint.Join.ROUND);
//		paint.setStrokeCap(Paint.Cap.ROUND);
//		return paint;
//	}
//
//	void initHeadAnimation() {
//		slid_out = AnimationUtils.loadAnimation(this, R.anim.slide_out);
//		slid_in = AnimationUtils.loadAnimation(this, R.anim.slide_in);
//		slid_in_from_top = AnimationUtils.loadAnimation(this,
//				R.anim.slide_in_from_top);
//		slid_out_fromTop = AnimationUtils.loadAnimation(this,
//				R.anim.slide_out_to_top);
//
//		slid_in_from_top.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		showAction = AnimationUtils.loadAnimation(getApplicationContext(),
//				R.anim.popup_enter);
//		hideAction = AnimationUtils.loadAnimation(getApplicationContext(),
//				R.anim.videohead_anim);
//
//		titleshow = AnimationUtils.loadAnimation(getApplicationContext(),
//				R.anim.title_show);
//
//		headAction = AnimationUtils.loadAnimation(getApplicationContext(),
//				R.anim.head_anim);
//		headAction.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				// TODO Auto-generated method stub
//				headbgLayout.startAnimation(headAction);
//
//			}
//		});
//		cendHeadLayout.startAnimation(showAction);
//		showAction.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				// TODO Auto-generated method stub
//				if (chargeCallingType != 1) {
//					headbgLayout.startAnimation(headAction);
//				}
//			}
//		});
//		hideAction.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				// TODO Auto-generated method stub
//				cendHeadLayout.setVisibility(View.GONE);
//				titleHeadLayout.setVisibility(View.VISIBLE);
//				hbNoticeView.setVisibility(View.GONE);
//				handler.postDelayed(showTitlelayout, 200);
//			}
//		});
//
//	};
//
//	Runnable showTitlelayout = new Runnable() {
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			titleHeadLayout.startAnimation(showAction);
//		}
//	};
//
//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		// TODO Auto-generated method stub
//		super.onWindowFocusChanged(hasFocus);
//		//		RelativeLayout.LayoutParams remotlp = new RelativeLayout.LayoutParams(
//		//				sWidth + 30, LayoutParams.MATCH_PARENT);
//		//		remotlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//		//		remoteView.setLayoutParams(remotlp);
//		doodleRate = (float) doolelayout.getWidth()
//				/ (float) doolelayout.getHeight();
//		LogUtil.i("比列", doodleRate + "");
//
//	}
//
//	@SuppressLint("NewApi")
//	private void processExtraData(Intent intent) {
//		LogUtil.i("image", "!!!!!!!!!");
//		if (intent == null) {
//			return;
//		}
//		String filepath = intent.getStringExtra("image");
//		Bitmap bitmap = BitmapFactory.decodeFile(filepath);
//		LogUtil.i("image", bitmap == null);
//		if (bitmap != null) {
//			doolelayout.setBackground(new BitmapDrawable(bitmap));
//			doodleSendbackGround(bitmap);
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		switch (resultCode) {
//		case 2:
//			if (data != null && data.hasExtra("file")) {
//				Intent intent = new Intent(this, CameraShow.class);
//				String filePath = data.getStringExtra("file").toString();
//				intent.putExtra("doodleRate", doodleRate);
//				intent.putExtra("file", filePath);
//				startActivityForResult(intent, 3);
//			}
//			break;
//		case 3:
//			processExtraData(data);
//			break;
//		case 4:
//			gotoCamera();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	public void innitGiftData() {
//		present1.add(new SongliBean(false, 1, false, Constants.GIFT_1,
//				Constants.GIFT_N_1, Constants.GIFT_U_1));
//		present1.add(new SongliBean(false, 2, false, Constants.GIFT_2,
//				Constants.GIFT_N_2, Constants.GIFT_U_1));
//		present1.add(new SongliBean(false, 3, false, Constants.GIFT_3,
//				Constants.GIFT_N_3, Constants.GIFT_U_1));
//		present1.add(new SongliBean(false, 4, false, Constants.GIFT_4,
//				Constants.GIFT_N_4, Constants.GIFT_U_1));
//		present1.add(new SongliBean(false, 5, false, Constants.GIFT_5,
//				Constants.GIFT_N_5, Constants.GIFT_U_1));
//		present1.add(new SongliBean(false, 6, false, Constants.GIFT_6,
//				Constants.GIFT_N_6, Constants.GIFT_U_1));
//		present1.add(new SongliBean(false, 7, false, Constants.GIFT_7,
//				Constants.GIFT_N_7, Constants.GIFT_U_1));
//		present1.add(new SongliBean(false, 8, false, Constants.GIFT_8,
//				Constants.GIFT_N_8, Constants.GIFT_U_1));
//		present2.add(new SongliBean(false, 9, false, Constants.GIFT_9,
//				Constants.GIFT_N_9, Constants.GIFT_U_1));
//		present2.add(new SongliBean(false, 10, false, Constants.GIFT_10,
//				Constants.GIFT_N_10, Constants.GIFT_U_1));
//		present2.add(new SongliBean(false, 11, false, Constants.GIFT_11,
//				Constants.GIFT_N_11, Constants.GIFT_U_1));
//		present2.add(new SongliBean(false, 12, false, Constants.GIFT_12,
//				Constants.GIFT_N_12, Constants.GIFT_U_1));
//		present2.add(new SongliBean(false, 13, false, Constants.GIFT_13,
//				Constants.GIFT_N_13, Constants.GIFT_U_1));
//		present2.add(new SongliBean(false, 14, false, Constants.GIFT_14,
//				Constants.GIFT_N_14, Constants.GIFT_U_1));
//		present2.add(new SongliBean(false, 15, false, Constants.GIFT_15,
//				Constants.GIFT_N_15, Constants.GIFT_U_1));
//		present2.add(new SongliBean(false, 16, false, Constants.GIFT_16,
//				Constants.GIFT_N_16, Constants.GIFT_U_1));
//		present3.add(new SongliBean(false, 17, false, Constants.GIFT_17,
//				Constants.GIFT_N_17, Constants.GIFT_U_1));
//		present3.add(new SongliBean(false, 18, false, Constants.GIFT_18,
//				Constants.GIFT_N_18, Constants.GIFT_U_1));
//		present3.add(new SongliBean(false, 19, false, Constants.GIFT_19,
//				Constants.GIFT_N_19, Constants.GIFT_U_1));
//		present3.add(new SongliBean(false, 20, false, Constants.GIFT_20,
//				Constants.GIFT_N_20, Constants.GIFT_U_1));
//		present3.add(new SongliBean(false, 21, false, Constants.GIFT_21,
//				Constants.GIFT_N_21, Constants.GIFT_U_1));
//		present3.add(new SongliBean(false, 22, false, Constants.GIFT_22,
//				Constants.GIFT_N_22, Constants.GIFT_U_1));
//		present3.add(new SongliBean(false, 23, false, Constants.GIFT_23,
//				Constants.GIFT_N_23, Constants.GIFT_U_1));
//		present3.add(new SongliBean(false, 24, false, Constants.GIFT_24,
//				Constants.GIFT_N_24, Constants.GIFT_U_1));
//	}
//}
