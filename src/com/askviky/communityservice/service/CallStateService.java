package com.askviky.communityservice.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallStateService extends Service {

	public static final String INTENT_ACTION_INCOMING_CALL = "com.communityservice.INCOMING_CALL";

	private TelephonyManager mCallManager;
	private IncomingCallListener mIncomingCallListener;

	// -----------------------------------------------------------------------
	// Debug Function
	// -----------------------------------------------------------------------

	private static final String LOG_TAG = "### CallStateService ###";
	private boolean DEBUG_MODE = true;
	private void log(String msg) {
		if (DEBUG_MODE) Log.d(LOG_TAG, msg);
	}

	// -----------------------------------------------------------------------
	// Private Function
	// -----------------------------------------------------------------------

	private void initCallManager() {
		if (mCallManager == null) {
			log("Add Incoming Call Manager");
			mCallManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			mIncomingCallListener = new IncomingCallListener();
			mCallManager.listen(mIncomingCallListener, PhoneStateListener.LISTEN_CALL_STATE);		
		}
	}

	private void removeCallManager() {
		if (mCallManager != null) {
			log("Remove Call Manager");
			mCallManager.listen(this.mIncomingCallListener, PhoneStateListener.LISTEN_NONE);
			mCallManager = null;
		}
	}

	private class IncomingCallListener extends PhoneStateListener {		
		@Override
		public void onCallStateChanged(int callstate, String incomingNumber) {			
			if (callstate == TelephonyManager.CALL_STATE_RINGING) {				
				log("Incoming CALL!!!");
				sendBroadcast(new Intent(INTENT_ACTION_INCOMING_CALL));
			}
		}
	}

	// -----------------------------------------------------------------------
	// Interface Function
	// -----------------------------------------------------------------------

	@Override
	public void onCreate() {
		initCallManager();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		removeCallManager();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
