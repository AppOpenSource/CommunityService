package com.askviky.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askviky.communityservice.R;

public class CustomDialog extends Dialog {

	private Context mContext;
	private String mTitle;
	private String mConfirmText;
	private String mCacelText;
	private ClickListenerInterface mClickListener;
	private TextView mTvTitle;

	public interface ClickListenerInterface {
		public void doConfirm();
		public void doCancel();
	}

	public CustomDialog(Context context, String title,
			String confirmButtonText, String cacelButtonText) {
		super(context, R.style.dialog);
		this.mContext = context;
		this.mTitle = title;
		this.mConfirmText = confirmButtonText;
		this.mCacelText = cacelButtonText;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	public void setMsgViewGravity(int gravity) {
		mTvTitle.setGravity(gravity);
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.change_filter_dialog_layout, null);
		setContentView(view);
		mTvTitle = (TextView) view.findViewById(R.id.filter_dialog_msg_tv);
		TextView tvConfirm = (TextView) view.findViewById(R.id.filter_dialog_buy_btn);
		TextView tvCancel = (TextView) view.findViewById(R.id.filter_dialog_cancel_btn);

		mTvTitle.setText(mTitle);
		tvConfirm.setText(mConfirmText);
		tvCancel.setText(mCacelText);

		tvConfirm.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				mClickListener.doConfirm();

			}
		});
		tvCancel.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				mClickListener.doCancel();

			}
		});

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.height = (int) (d.heightPixels * 0.2);
		lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
		dialogWindow.setAttributes(lp);
	}

	public void setClicklistener(ClickListenerInterface clickListenerInterface) {
		this.mClickListener = clickListenerInterface;
	}
	
	public static Dialog getLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_loading, null);

		LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
		ImageView image = (ImageView) view.findViewById(R.id.img);
		TextView tips = (TextView) view.findViewById(R.id.tipTextView);

		Animation anima = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		image.startAnimation(anima);
		tips.setText(msg);

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		loadingDialog.setCancelable(false); // 不可以用"返回键"取消
		loadingDialog.setContentView(layout, new LinearLayout.
				LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT));
		return loadingDialog;
	}

}
