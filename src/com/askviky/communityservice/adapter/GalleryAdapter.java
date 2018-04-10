package com.askviky.communityservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.askviky.communityservice.R;

public class GalleryAdapter extends BaseAdapter {
	
	private Context mContext;

	//  图片数组源
	public Integer[] mImgs = { R.drawable.wechat_startup_01, R.drawable.wechat_startup_02,
			R.drawable.wechat_startup_03, R.drawable.wechat_startup_04, R.drawable.wechat_startup_05,
			R.drawable.wechat_startup_06, R.drawable.wechat_startup_07};

	public GalleryAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	// 获取图片位置
	@Override
	public Object getItem(int position) {
		return mImgs[position];
	}

	// 获取图片ID
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageview = new ImageView(mContext);

		imageview.setImageResource(mImgs[position % mImgs.length]);
		imageview.setLayoutParams(new Gallery.LayoutParams(200, 94));		// 设置布局 图片120×120显示
		imageview.setScaleType(ImageView.ScaleType.CENTER);				// 设置显示比例类型
		imageview.setBackgroundColor(Color.alpha(1));
		return imageview;
	}
}
