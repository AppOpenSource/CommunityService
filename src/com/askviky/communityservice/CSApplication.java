package com.askviky.communityservice;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.util.Log;

/**
 * For developer startup JPush SDK
 * 
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class CSApplication extends Application {
	
	private static final String TAG = CSApplication.class.getSimpleName();
	
	@Override
	public void onCreate() {    	     
		Log.d(TAG, "onCreate");
		super.onCreate();
		
		JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
		JPushInterface.init(this);     		// 初始化 JPush
	}
}
