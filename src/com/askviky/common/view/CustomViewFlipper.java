package com.askviky.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.askviky.communityservice.R;

public class CustomViewFlipper extends ViewFlipper {
	
	private static final String TAG = CustomViewFlipper.class.getSimpleName();
	private int mStartX = 0, mEndX = 0;
	private CustomViewFlipper mViewFlipper;
	private Context mContext;
    private ChangeIndexListner mChangeIndexListner;

	public CustomViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		mViewFlipper=this;
		mContext=context;
	}
	
	public interface ChangeIndexListner{
		public void changeIndex(int move);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_DOWN) {
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			return true;
		}
		return true;
	}
	
	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i(TAG, "---------onTouchEvent==" + event.getX());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "---------startX==" + mStartX);
			mStartX = (int) event.getX();
			break;

		case MotionEvent.ACTION_UP:
			mEndX = (int) event.getX();
			Log.i(TAG, "---------endX+=" + mEndX);

			if (mEndX - mStartX > 50) {
				Log.i(TAG, "------------------right");
				mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.push_left_in));
				mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.push_left_out));

				// 显示上一屏的View
				mViewFlipper.showPrevious();
				mChangeIndexListner.changeIndex(-1);
				
			} else if (mStartX - mEndX > 50) {
				Log.i(TAG, "------------------left");
				mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.push_right_in));
				mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.push_right_out));
				
				// 显示下一屏的View
				mViewFlipper.showNext();
				mChangeIndexListner.changeIndex(1);
			}
		}
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onKeyDown keyCode: "+keyCode);
		Log.d(TAG, "onKeyDown event: "+event);
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onKeyUp keyCode: "+keyCode);
		Log.d(TAG, "onKeyUp event: "+event);
		return super.onKeyUp(keyCode, event);
	}

	public ChangeIndexListner getChangeIndexListner() {
		return mChangeIndexListner;
	}

	public void setChangeIndexListner(ChangeIndexListner changeIndexListner) {
		this.mChangeIndexListner = changeIndexListner;
	}

}
