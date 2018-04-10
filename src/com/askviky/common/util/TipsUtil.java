package com.askviky.common.util;

import com.askviky.common.callback.AirCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class TipsUtil {

	private static AlertDialog mAlertDialog;
	private static Dialog mProDialog;

	/**
	 * 弹出只有确定按钮的dialog
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题文字
	 * @param content
	 *            提示信息
	 * @param okLabel
	 *            提示按钮文字
	 */
	public static void alertDialogTips(Context context, String title,
			String content, String okLabel, final AirCallback call) {
		mAlertDialog = new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(content)
		.setPositiveButton(okLabel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialoginterface, int i) {
				if (null != call) {
					call.callback();
				}
			}
		}).show();
	}

	/**
	 * 显示带两个按钮的dialog
	 * 
	 * @param context
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param okLabel
	 *            左侧按钮内容
	 * @param okCall
	 *            点击按钮的回调函数
	 * @param cancelLabel
	 *            右侧按钮内容
	 * @param cancelCall
	 *            点击按钮的回调函数
	 */
	public static void alertDialogTwoBtn(Context context, String title,
			String content, String okLabel, final AirCallback okCall,
			String cancelLabel, final AirCallback cancelCall) {
		mAlertDialog = new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(content)
		.setNegativeButton(cancelLabel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if (null != cancelCall) {
					cancelCall.callback();
				}
			}
		})
		.setPositiveButton(okLabel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialoginterface, int i) {
				if (null != okCall) {
					okCall.callback();
				}
			}
		}).show();
	}

	/**
	 * 关闭AlertDialog
	 */
	public static void closeAlertDialog() {
		if (mAlertDialog != null) {
			mAlertDialog.dismiss();
		}
	}

	/**
	 * 弹出ProgressDialog
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示信息
	 * @param title
	 *            可以为空
	 */
	public static void showProgress(Activity ac, Context context, String msg) {
		/*if (mProDialog != null && mProDialog.isShowing())
			return;
		mProDialog = new Dialog(context, R.style.dialog);
		mProDialog.setContentView(R.layout.progress_dialog);
		TextView content = (TextView) mProDialog
				.findViewById(R.id.dialog_progress_content);
		content.setText(msg);
		ImageView view = (ImageView) mProDialog
				.findViewById(R.id.dialog_progress_icon);
		view.setBackgroundResource(R.anim.loading);
		AnimationDrawable animationDrawable = (AnimationDrawable) view
				.getBackground();
		animationDrawable.start();
		mProDialog.show();
		WindowManager windowManager = ac.getWindowManager();
		Window window = mProDialog.getWindow();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = mProDialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);*/
	}

	public static void hideProgressDialog() {
		if (null != mProDialog) {
			mProDialog.dismiss();
		}
	}

	/**
	 * 关闭ProgressDialog
	 */
	public static void closeProgressDialog() {
		if (mProDialog != null) {
			mProDialog.dismiss();
		}
	}

	public static void alertToastTips(Context context, String tips) {
		Toast.makeText(context, tips, Toast.LENGTH_LONG).show();
	}
}
