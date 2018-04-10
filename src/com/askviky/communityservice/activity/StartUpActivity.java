package com.askviky.communityservice.activity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;
import com.askviky.communityservice.service.LoginService;

// 启动页面
public class StartUpActivity extends AppBaseActivity implements TagAliasCallback {

	private static final String TAG = "StartupPage";
	private LoginService mLoginManager;

	@Override
	public void gotResult(int code, String alias, Set<String> tags) {
		String logs;
		switch (code) {
		case 0:
			logs = "Set tag and alias success, alias = " + alias + "; tags = " + tags;
			break;
		default:
			logs = "Failed with errorCode = " + code + " " + alias + "; tags = " + tags;
		}
		Log.d(TAG, "logs: "+logs);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_startup);

		mLoginManager = new LoginService(this);
		if (mLoginManager.getUserId() != -1) {
			Set<String> tagSet = new LinkedHashSet<String>();
			tagSet.add("1"+mLoginManager.getUserId());
			//registerMessageReceiver();  // used for receive msg
			//调用JPush API设置Tag
			JPushInterface.setAliasAndTags(getApplicationContext(), null, tagSet, this);
		}
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Intent intent = new Intent(StartUpActivity.this, ChatActivity.class);
				startActivity(intent);
				finish();
			}
		};
		timer.schedule(task, 1000*1);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
}
