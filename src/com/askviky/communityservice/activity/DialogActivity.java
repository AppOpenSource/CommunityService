package com.askviky.communityservice.activity;

import android.app.Dialog;
import android.os.Bundle;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.common.view.CustomDialog;

public class DialogActivity extends AppBaseActivity {

	private Dialog mDialog;
	
	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void loadData() {
		/* mDialog = new ProgressDialog(getActivity());
		mDialog.setTitle("等待");
		mDialog.setMessage("正在加载");*/
		
		mDialog = CustomDialog.getLoadingDialog(this, "正在加载 ...");
		mDialog.show();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDialog.dismiss();
	}
	
}
