package com.zx.wfm.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import com.zx.wfm.bean.TrafficInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 获取手机安装的应用程序的的引擎工具类
 * @author liuyazhuang
 *
 */
public class TrafficManagerUtils {
	
	private PackageManager pm;
	public TrafficManagerUtils(Context context) {
		super();
		pm = context.getPackageManager();
	}

	/**
	 * 查询能够启动的应用程序
	 * @return
	 */
	public List<TrafficInfo> getLauncherTrafficInfos(){
		List<TrafficInfo> trafficInfos = new ArrayList<TrafficInfo>();
		//查询能够启动的应用程序  
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		//ResolveInfo  就类似于一个IntentFilter
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		for(ResolveInfo info:resolveInfos){
			ApplicationInfo appInfo = info.activityInfo.applicationInfo;
			Drawable appicon = appInfo.loadIcon(pm);
			String appname = appInfo.loadLabel(pm).toString();
			
			String packageName = appInfo.packageName;
			int uid = appInfo.uid;
			
			trafficInfos.add(new TrafficInfo(appicon, appname, packageName, uid));
		}
		return trafficInfos;
	}
	
	/**
	 * 获取拥有internet权限的应用列表
	 * @return
	 */
	public List<TrafficInfo> getInternetTrafficInfos(){
		List<TrafficInfo> trafficInfos = new ArrayList<TrafficInfo>();
		//获取手机中安装的并且具有权限的应用
		List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
		for(PackageInfo info : installedPackages){
			//获取权限数组
			String[] permissions = info.requestedPermissions;
			if(permissions != null && permissions.length > 0){
				for(String permission : permissions){
					if(permission.equals(Manifest.permission.INTERNET)){
						ApplicationInfo applicationInfo = info.applicationInfo;
						Drawable icon = applicationInfo.loadIcon(pm);
						String appname = applicationInfo.loadLabel(pm).toString();
						String packagename = applicationInfo.packageName;
						int uid = applicationInfo.uid;
						TrafficInfo trafficInfo = new TrafficInfo(icon, appname, packagename, uid);
						trafficInfos.add(trafficInfo);
					}
				}
			}
		}
		return trafficInfos;
	}
}
