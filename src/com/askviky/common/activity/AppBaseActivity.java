package com.askviky.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class AppBaseActivity extends FragmentActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initVariables();
		initViews(savedInstanceState);
		loadData();
	}
	
	protected abstract void initVariables();
	protected abstract void initViews(Bundle savedInstanceState);
	protected abstract void loadData();
	
	public void jumpTo(String activityName, Intent intent) {
		Class<?> cls = null;
		try {
			cls = Class.forName(activityName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (cls == null) return;
		intent.setClass(this, cls);
		this.startActivity(intent);
	}
}
