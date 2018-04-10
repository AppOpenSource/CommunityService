package com.askviky.communityservice.net;

import android.os.AsyncTask;
import android.util.Log;

public class AnyTask extends AsyncTask<String, Integer, String> {

	private static final String TAG = "AnyTask";
	
	@Override
	protected void onPreExecute() { // onPreExecute方法用于在执行后台任务前做一些UI操作
		Log.i(TAG, "onPreExecute() called");
		// textView.setText("loading...");
	}

	// doInBackground方法内部执行后台任务,不可在此方法内修改UI 
	@Override
	protected String doInBackground(String... params) {
		Log.i(TAG, "doInBackground(Params... params) called");
		return null;
	}

	// onProgressUpdate方法用于更新进度信息
	@Override
	protected void onProgressUpdate(Integer... progresses) {
		Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
		//progressBar.setProgress(progresses[0]);
		//textView.setText("loading..." + progresses[0] + "%");
	}
	
	// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
	@Override
	protected void onPostExecute(String result) {
		Log.i(TAG, "onPostExecute(Result result) called"); 
		//textView.setText(result);
		
		//execute.setEnabled(true);
		//cancel.setEnabled(false);
	}
	
	// onCancelled方法用于在取消执行中的任务时更改UI
	@Override  
	protected void onCancelled() {
		Log.i(TAG, "onCancelled() called");
		//textView.setText("cancelled");
		//progressBar.setProgress(0);

		//execute.setEnabled(true);
		//cancel.setEnabled(false);  
	}
}
