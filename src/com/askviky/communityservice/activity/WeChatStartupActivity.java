package com.askviky.communityservice.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.common.view.ScrollLayout;
import com.askviky.communityservice.R;
import com.askviky.communityservice.listener.OnViewChangeListener;

public class WeChatStartupActivity extends AppBaseActivity implements OnViewChangeListener {

	private ScrollLayout mScrollLayout;
	private ImageView[] mImgs;
	private int mCount;
	private int mCurrentItem;
	private Button mStartBtn;
	private RelativeLayout mMainRLayout;
	private LinearLayout mPointLLayout;
	private LinearLayout mLeftLayout;
	private LinearLayout mRightLayout;
	private LinearLayout mAnimLayout;

	private View.OnClickListener mOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startBtn:
				mScrollLayout.setVisibility(View.GONE);
				mPointLLayout.setVisibility(View.GONE);
				mAnimLayout.setVisibility(View.VISIBLE);
				mMainRLayout.setBackgroundResource(R.drawable.whatsnew_bg);

				Animation leftOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left);
				Animation rightOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right);

				mLeftLayout.setAnimation(leftOutAnimation);
				mRightLayout.setAnimation(rightOutAnimation);

				leftOutAnimation.setAnimationListener(new AnimationListener() {
					@SuppressLint("ResourceAsColor") @Override
					public void onAnimationStart(Animation animation) {
						mMainRLayout.setBackgroundColor(R.color.bg_color);
					}
					@Override
					public void onAnimationRepeat(Animation animation) {

					}
					@Override
					public void onAnimationEnd(Animation animation) {
						mLeftLayout.setVisibility(View.GONE);
						mRightLayout.setVisibility(View.GONE);
						Intent intent = new Intent(WeChatStartupActivity.this, WeChatActivity.class);
						WeChatStartupActivity.this.startActivity(intent);
						WeChatStartupActivity.this.finish();
						overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit);
					}
				});
				break;
			}
		}
	};

	@Override
	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		if(position < 0 || position > mCount -1 || mCurrentItem == position) {
			return;
		}
		mImgs[mCurrentItem].setEnabled(true);
		mImgs[position].setEnabled(false);
		mCurrentItem = position;
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_wechat_startup);
		mScrollLayout  = (ScrollLayout) findViewById(R.id.ScrollLayout);
		mPointLLayout = (LinearLayout) findViewById(R.id.llayout);
		mMainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
		mStartBtn = (Button) findViewById(R.id.startBtn);
		mStartBtn.setOnClickListener(mOnClick);
		mAnimLayout = (LinearLayout) findViewById(R.id.animLayout);
		mLeftLayout  = (LinearLayout) findViewById(R.id.leftLayout);
		mRightLayout  = (LinearLayout) findViewById(R.id.rightLayout);
		mCount = mScrollLayout.getChildCount();
		mImgs = new ImageView[mCount];
		for(int i = 0; i< mCount;i++) {
			mImgs[i] = (ImageView) mPointLLayout.getChildAt(i);
			mImgs[i].setEnabled(true);
			mImgs[i].setTag(i);
		}
		mCurrentItem = 0;
		mImgs[mCurrentItem].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(this);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
}
