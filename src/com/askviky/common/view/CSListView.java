package com.askviky.common.view;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.askviky.communityservice.R;

public class CSListView extends ListView implements OnScrollListener {

	private static final String TAG = "CSListView";
	private static final int RELEASE_To_REFRESH = 0;
	private static final int PULL_To_REFRESH = 1;
	private static final int REFRESHING = 2;
	private static final int DONE = 3;
	private static final int LOADING = 4;

	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;

	private LayoutInflater mInflater;
	private LinearLayout mHeadView;
	private TextView mTVTips;
	private TextView mTVLastUpdated;
	private ImageView mIVArrow;
	private ProgressBar mProgressBar;
	private RotateAnimation mAnimation;
	private RotateAnimation mReverseAnimation;

	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean mIsRecored;
	private boolean mIsBack;
	private boolean mIsRefreshable;
	private OnRefreshListener mRefreshListener;
	
	private int mHeadContentWidth;
	private int mHeadContentHeight;
	private int mState;
	private int mStartY;
	private int mFirstItemIndex;

	public CSListView(Context context) {
		super(context);
		init(context);
	}

	public CSListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setCacheColorHint(context.getResources().getColor(R.color.transparent));
		
		mInflater = LayoutInflater.from(context);
		mHeadView = (LinearLayout) mInflater.inflate(R.layout.listview_head, null);

		mIVArrow = (ImageView) mHeadView.findViewById(R.id.head_arrowImageView);
		mIVArrow.setMinimumWidth(70);
		mIVArrow.setMinimumHeight(50);
		mProgressBar = (ProgressBar) mHeadView.findViewById(R.id.head_progressBar);
		mTVTips = (TextView) mHeadView.findViewById(R.id.head_tipsTextView);
		mTVLastUpdated = (TextView) mHeadView.findViewById(R.id.head_lastUpdatedTextView);

		measureView(mHeadView);
		mHeadContentHeight = mHeadView.getMeasuredHeight();
		mHeadContentWidth = mHeadView.getMeasuredWidth();

		mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
		mHeadView.invalidate();

		Log.v(TAG, "width: " + mHeadContentWidth + " height: "+ mHeadContentHeight);

		addHeaderView(mHeadView, null, false);
		setOnScrollListener(this);

		mAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mAnimation.setInterpolator(new LinearInterpolator());
		mAnimation.setDuration(250);
		mAnimation.setFillAfter(true);

		mReverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseAnimation.setInterpolator(new LinearInterpolator());
		mReverseAnimation.setDuration(200);
		mReverseAnimation.setFillAfter(true);

		mState = DONE;
		mIsRefreshable = false;
	}

	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2, int arg3) {
		mFirstItemIndex = firstVisiableItem;
	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (mIsRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (mFirstItemIndex == 0 && !mIsRecored) {
					mIsRecored = true;
					mStartY = (int) event.getY();
					Log.v(TAG, "在down时候记录当前位置");
				}
				break;
			case MotionEvent.ACTION_UP:
				if (mState != REFRESHING && mState != LOADING) {
					if (mState == DONE) {
						// 什么都不做
					}
					if (mState == PULL_To_REFRESH) {
						mState = DONE;
						changeHeaderViewByState();
						Log.v(TAG, "由下拉刷新状态，到done状态");
					}
					if (mState == RELEASE_To_REFRESH) {
						mState = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
						Log.v(TAG, "由松开刷新状态，到done状态");
					}
				}
				mIsRecored = false;
				mIsBack = false;
				break;
			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();
				if (!mIsRecored && mFirstItemIndex == 0) {
					Log.v(TAG, "在move时候记录下位置");
					mIsRecored = true;
					mStartY = tempY;
				}
				if (mState != REFRESHING && mIsRecored && mState != LOADING) {
					// 保证在设置padding的过程中，当前的位置一直是在head
					// 否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
					if (mState == RELEASE_To_REFRESH) {// 可以松手去刷新了
						setSelection(0);
						// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
						if (((tempY - mStartY) / RATIO < mHeadContentHeight)
								&& (tempY - mStartY) > 0) {
							mState = PULL_To_REFRESH;
							changeHeaderViewByState();
						} else if (tempY - mStartY <= 0) { // 一下子推到顶了
							mState = DONE;
							changeHeaderViewByState();
						} else {
							// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
							// 不用进行特别的操作，只用更新paddingTop的值就行了
						}
					}
					// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态״̬
					if (mState == PULL_To_REFRESH) {
						setSelection(0);
						// 下拉到可以进入RELEASE_TO_REFRESH的状态״̬
						if ((tempY - mStartY) / RATIO >= mHeadContentHeight) {
							mState = RELEASE_To_REFRESH;
							mIsBack = true;
							changeHeaderViewByState();
							Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
						} else if (tempY - mStartY <= 0) { // 上推到顶了
							mState = DONE;
							changeHeaderViewByState();
							Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
						}
					}
					// done状态下
					if (mState == DONE) {
						if (tempY - mStartY > 0) {
							mState = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					// 更新headView的size
					if (mState == PULL_To_REFRESH) {
						mHeadView.setPadding(0, -1 * mHeadContentHeight
								+ (tempY - mStartY) / RATIO, 0, 0);
					}

					// 更新headView的paddingTop
					if (mState == RELEASE_To_REFRESH) {
						mHeadView.setPadding(0, (tempY - mStartY) / RATIO
								- mHeadContentHeight, 0, 0);
					}
				}
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (mState) {
		case RELEASE_To_REFRESH:
			mIVArrow.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			mTVTips.setVisibility(View.VISIBLE);
			mTVLastUpdated.setVisibility(View.VISIBLE);

			mIVArrow.clearAnimation();
			mIVArrow.startAnimation(mAnimation);
			mTVTips.setText("松开刷新");
			Log.v(TAG, "当前状态，松开刷新");
			break;
		case PULL_To_REFRESH:
			mProgressBar.setVisibility(View.GONE);
			mTVTips.setVisibility(View.VISIBLE);
			mTVLastUpdated.setVisibility(View.VISIBLE);
			mIVArrow.clearAnimation();
			mIVArrow.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (mIsBack) {
				mIsBack = false;
				mIVArrow.clearAnimation();
				mIVArrow.startAnimation(mReverseAnimation);
				mTVTips.setText("下拉刷新");
			} else {
				mTVTips.setText("下拉刷新");
			}
			Log.v(TAG, "当前状态，下拉刷新");
			break;
		case REFRESHING:
			mHeadView.setPadding(0, 0, 0, 0);
			mProgressBar.setVisibility(View.VISIBLE);
			mIVArrow.clearAnimation();
			mIVArrow.setVisibility(View.GONE);
			mTVTips.setText("正在刷新...");
			mTVLastUpdated.setVisibility(View.VISIBLE);
			Log.v(TAG, "当前状态,正在刷新...");
			break;
		case DONE:
			mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
			mProgressBar.setVisibility(View.GONE);
			mIVArrow.clearAnimation();
			mIVArrow.setImageResource(R.drawable.arrow);
			mTVTips.setText("下拉刷新");
			mTVLastUpdated.setVisibility(View.VISIBLE);
			Log.v(TAG, "当前状态，done");
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.mRefreshListener = refreshListener;
		mIsRefreshable = true;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void onRefreshComplete() {
		mState = DONE;
		mTVLastUpdated.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	private void onRefresh() {
		if (mRefreshListener != null) {
			mRefreshListener.onRefresh();
		}
	}

	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {
		mTVLastUpdated.setText("最近更新:" + new Date().toLocaleString());
		super.setAdapter(adapter);
	}
}
