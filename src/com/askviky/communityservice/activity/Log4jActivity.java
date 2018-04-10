package com.askviky.communityservice.activity;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class Log4jActivity extends AppBaseActivity implements OnClickListener {
	
	private static final String TAG = Log4jActivity.class.getSimpleName();
	private Logger mLogger;
	
	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about);

		ImageButton btn = (ImageButton) findViewById(R.id.back_btn);
		btn.setOnClickListener(this);
		
		configLog();
		
		mLogger.debug("Test android log to SDCard file using log4j");
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	public void configLog() {
		final LogConfigurator logConfigurator = new LogConfigurator();

		logConfigurator.setFileName(Environment.getExternalStorageDirectory() + File.separator + "Cookbook_Log4j.log");
		// Set the root log level
		logConfigurator.setRootLevel(Level.DEBUG);
		// Set log level of a specific logger
		logConfigurator.setLevel(TAG, Level.ERROR);
		logConfigurator.configure();

		mLogger = (Logger) Logger.getInstance(Log4jActivity.class);
	}

	@Override
	public void onClick(View v) {
		this.finish();
	}
	
}
