package com.askviky.communityservice.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;

public class SlideOutActivity extends AppBaseActivity {

	private ImageView mCover;
	private ListView mList;
	private Animation mStartAnimation;
	private Animation mStopAnimation;
	private static Bitmap mCoverBitmap = null;
	private static final int DURATION_MS = 400;

	// 2个步骤
	// 1. activity-->other activity
	// 2. anim
	// 先切换到另一个activity
	// 再获得之前activity屏幕的快照将它作为一个cover覆盖在下一个屏幕的上面，然后通过动画移动这个cover，让人感觉好像是前一个屏幕的移动。
	public static void prepare(Activity activity, int id) {
		if (mCoverBitmap != null) {
			mCoverBitmap.recycle();
		}
		// 用指定大小生成一张透明的32位位图，并用它构建一张canvas画布
		mCoverBitmap = Bitmap.createBitmap(
				activity.findViewById(id).getWidth(), activity.findViewById(id)
				.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mCoverBitmap);
		// 将指定的view包括其子view渲染到这种画布上，在这就是上一个activity布局的一个快照，现在这个bitmap上就是上一个activity的快照
		activity.findViewById(id).draw(canvas);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void initAnim() {
		// 采用了绝对布局，绝对布局是view的左上角从(0,0)开始
		@SuppressWarnings("deprecation")
		final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0);
		findViewById(R.id.slideout_placeholder).setLayoutParams(lp);

		// 屏幕的宽度
		int displayWidth = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getWidth();
		// 右边的位移量，60dp转换成px
		int sWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 60, getResources()
				.getDisplayMetrics());
		// 将快照向右移动的偏移量
		final int shift = displayWidth - sWidth;
		// 向右移动的位移动画向右移动shift距离，y方向不变
		mStartAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE,
				0, TranslateAnimation.ABSOLUTE, shift,
				TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 0);
		// 回退时的位移动画
		mStopAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, -shift,
				TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 0);
		// 持续时间
		mStartAnimation.setDuration(DURATION_MS);
		// 动画完成时停留在结束位置
		mStartAnimation.setFillAfter(true);
		mStartAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// 动画结束时回调
				// 将imageview固定在位移后的位置
				mCover.setAnimation(null);
				@SuppressWarnings("deprecation")
				final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
						shift, 0);
				mCover.setLayoutParams(lp);
			}
		});

		mStopAnimation.setDuration(DURATION_MS);
		mStopAnimation.setFillAfter(true);
		mStopAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				finish();
				overridePendingTransition(0, 0);
			}
		});
	}

	public void open() {
		mCover.startAnimation(mStartAnimation);
	}

	public void close() {
		mCover.startAnimation(mStopAnimation);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 摁返回键时也要触发动画
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			close();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// 绝对布局最上层覆盖了一个imageview
		setContentView(R.layout.activity_slide);
		initAnim();
		mCover = (ImageView) findViewById(R.id.slidedout_cover);
		mCover.setImageBitmap(mCoverBitmap);
		mCover.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				close();
			}
		});
		mList = (ListView) findViewById(R.id.list);
		mList.setAdapter(new ArrayAdapter<String>(SlideOutActivity.this,
				android.R.layout.simple_list_item_1, new String[] { " First",
				" Second", " Third", " Fourth", " Fifth", " Sixth" }));
		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				close();
			}
		});
		open();
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
}
