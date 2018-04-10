package com.askviky.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.askviky.communityservice.R;

public class UpdateDownLoadDialog extends Dialog {

	private Context mContext;
	private String mCacelText;
	private ClickListenerInterface mClickListener;
	private ProgressBar mProgress;

	public interface ClickListenerInterface {
		public void doCancel();
	}

	public UpdateDownLoadDialog(Context context, String cacelButtonText) {
		super(context, R.style.dialog);
		this.mContext = context;
		this.mCacelText = cacelButtonText;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	public ProgressBar getProgressView() {
		return mProgress;
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.update_download_dialog_layout,
				null);
		setContentView(view);

		mProgress = (ProgressBar) view.findViewById(R.id.update_progress);

		TextView tvCancel = (TextView) view
				.findViewById(R.id.filter_dialog_cancel_btn);

		tvCancel.setText(mCacelText);

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

}