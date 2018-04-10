package com.askviky.communityservice.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.Toast;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;
import com.askviky.communityservice.adapter.GalleryAdapter;

public class GalleryActivity extends AppBaseActivity {

	private GalleryAdapter mImgAdapter = null;		// 声明图片资源对象
	private Gallery mGallery = null;

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_gallery);

		mGallery = (Gallery) findViewById(R.id.gallery);
		mImgAdapter = new GalleryAdapter(this);
		mGallery.setAdapter(mImgAdapter); 					// 设置图片资源
		mGallery.setGravity(Gravity.CENTER_HORIZONTAL);		// 设置水平居中显示
		mGallery.setSelection(mImgAdapter.mImgs.length * 100); // 设置起始图片显示位置（可以用来制作gallery循环显示效果）

		mGallery.setOnItemClickListener(mClickListener); // 设置点击图片的监听事件（需要用手点击才触发，滑动时不触发）
		mGallery.setOnItemSelectedListener(mSelectedListener); // 设置选中图片的监听事件（当图片滑到屏幕正中，则视为自动选中）
		mGallery.setUnselectedAlpha(0.3f);	// 设置未选中图片的透明度
		mGallery.setSpacing(40); // 设置图片之间的间距
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	// 点击图片的监听事件
	AdapterView.OnItemClickListener mClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Toast.makeText(GalleryActivity.this, "点击图片 " + (position + 1), 100).show();
		}
	};

	// 选中图片的监听事件
	AdapterView.OnItemSelectedListener mSelectedListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			Toast.makeText(GalleryActivity.this, "选中图片 " + (position + 1), 20).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

}
