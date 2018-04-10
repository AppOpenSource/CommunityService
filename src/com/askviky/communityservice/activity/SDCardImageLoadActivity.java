package com.askviky.communityservice.activity;

import java.io.File;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.common.util.ImageUtil;
import com.askviky.communityservice.R;

public class SDCardImageLoadActivity extends AppBaseActivity {
	
	private LinearLayout mLinearLayout;

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_image_load);
		mLinearLayout = (LinearLayout) findViewById(R.id.linear_layout);

		ImageView imageView = new ImageView(this);//创建一个imageView对象  
		String filePath = ImageUtil.getSDCardRoot()+File.separator+"xxx.jpg";
		imageView.setImageBitmap(ImageUtil.getImage(filePath));
		mLinearLayout.addView(imageView); 
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
}
