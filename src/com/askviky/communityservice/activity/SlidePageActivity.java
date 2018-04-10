package com.askviky.communityservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;

public class SlidePageActivity extends AppBaseActivity {

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_slide_out);

		Button btn = (Button) findViewById(R.id.sample_button);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SlideOutActivity.prepare(SlidePageActivity.this, R.id.inner_content);
				startActivity(new Intent(SlidePageActivity.this, SlideOutActivity.class));
				overridePendingTransition(0, 0);
			}
		});
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
}