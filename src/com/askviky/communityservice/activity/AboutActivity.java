package com.askviky.communityservice.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;

public class AboutActivity extends AppBaseActivity implements OnClickListener {
	
	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_about);
		
		ImageButton btn = (ImageButton) findViewById(R.id.back_btn);
		btn.setOnClickListener(this);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onClick(View v) {
		this.finish();
	}
}
