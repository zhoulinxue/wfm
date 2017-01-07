package com.zx.wfm.service;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.zx.wfm.utils.Constants;

public class EventBroadCastRecevier extends BroadcastReceiver {
	SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("action", intent.getAction());
		if(preferences==null){
			preferences= PreferenceManager.getDefaultSharedPreferences(context);
			editor=preferences.edit();
		}
		// TODO Auto-generated method stub
		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isAvailable()) {
				String name = netInfo.getTypeName();
				if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					Log.i("网络", "已连接......wifi" + name);
					editor.putBoolean(Constants.MOBLE_TRAFFIC,false);
					editor.commit();
					// ///WiFi网络
				} else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					Log.i("网络", "已连接......流量" + name);
					// ///////3g网络
					editor.putBoolean(Constants.MOBLE_TRAFFIC,true);
					editor.commit();
				}
			} else {
				// //////网络断开
				Log.i("网络",
						"已断开......@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			}

		} else if (Intent.ACTION_BOOT_COMPLETED.equals(intent
				.getAction())) {
			Toast.makeText(context,"开机了",Toast.LENGTH_LONG).show();
			Log.i("网络", "开机了！！！！！！！！！！！！！！！");
		} else if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
			Toast.makeText(context,"解锁了",Toast.LENGTH_LONG).show();
			Log.i("网络", "解锁");
		}
	}


}
