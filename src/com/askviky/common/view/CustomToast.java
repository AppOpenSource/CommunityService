package com.askviky.common.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.askviky.common.util.ScreenUtil;
import com.askviky.communityservice.R;

public enum CustomToast {
	builder;
	private TextView mTextView;
	private Toast mCustomToast = null;

	public void init(Context context) {
		Toast toast = new Toast(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast_layout, null);
		mTextView = (TextView) layout.findViewById(R.id.text);
		mTextView.setTextSize(14);
		toast.setView(layout);
		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
				ScreenUtil.getScreenHeight(context) / 8);
		toast.setDuration(Toast.LENGTH_SHORT);
		mCustomToast = toast;
	}

	public Toast display(CharSequence text) {
		if (mTextView != null) mTextView.setText(text);
		if (mCustomToast != null) mCustomToast.show();
		return mCustomToast;
	}

	public Toast display(int Resid) {
		if (mTextView != null) mTextView.setText(Resid);
		if (mCustomToast != null) mCustomToast.show();
		return mCustomToast;
	}

	public void displayOnUiThread(Activity act, final CharSequence text) {
		if (act != null) {
			act.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					display(text);
				}
			});
		}
	}

	public void displayOnUiThread(Activity act, final int Resid) {
		if (act != null) {
			act.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					display(Resid);
				}
			});
		}
	}
}
